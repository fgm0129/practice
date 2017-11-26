package com.practice.hack.changelog;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.StaticLoggerBinder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by fgm on 2017/8/20.
 */
@Component
public class ChangeLogLevelProcessUnit {

    private static final Logger LOG = LoggerFactory.getLogger(ChangeLogLevelProcessUnit.class);
    private LogFrameworkType logFrameworkType = LogFrameworkType.UNKNOWN;
    private static ConcurrentMap<String, Object> loggerMap = new ConcurrentHashMap();

    /**
     * 创建ChangeLogLevelProcessUnit实例的同时, 确定使用的日志框架, 并获取配置的所有Logger.
     */
    public ChangeLogLevelProcessUnit() {
        String type = StaticLoggerBinder.getSingleton().getLoggerFactoryClassStr();
        if ("org.slf4j.impl.Log4jLoggerFactory".equals(type)) {
            logFrameworkType = LogFrameworkType.LOG4J;
//            Enumeration enumeration = org.apache.log4j.LogManager.getCurrentLoggers();
//            while (enumeration.hasMoreElements()) {
//                org.apache.log4j.Logger logger = (org.apache.log4j.Logger) enumeration.nextElement();
//                if (logger.getLevel() != null) {
//                    loggerMap.put(logger.getName(), logger);
//                }
//            }
//            org.apache.log4j.Logger rootLogger = org.apache.log4j.LogManager.getRootLogger();
//            loggerMap.put(rootLogger.getName(), rootLogger);
        } else if ("ch.qos.logback.classic.util.ContextSelectorStaticBinder".equals(type)) {
            logFrameworkType = LogFrameworkType.LOGBACK;
//            ch.qos.logback.classic.LoggerContext loggerContext = (ch.qos.logback.classic.LoggerContext) LoggerFactory.getILoggerFactory();
//            for (ch.qos.logback.classic.Logger logger : loggerContext.getLoggerList()) {
//                if (logger.getLevel() != null) {
//                    loggerMap.put(logger.getName(), logger);
//                }
//            }
//            ch.qos.logback.classic.Logger rootLogger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
//            loggerMap.put(rootLogger.getName(), rootLogger);
        } else if ("org.apache.logging.slf4j.Log4jLoggerFactory".equals(type)) {
            logFrameworkType = LogFrameworkType.LOG4J2;
            org.apache.logging.log4j.core.LoggerContext loggerContext = (org.apache.logging.log4j.core.LoggerContext) org.apache.logging.log4j.LogManager.getContext(false);
            Map<String, LoggerConfig> map = loggerContext.getConfiguration().getLoggers();
            for (org.apache.logging.log4j.core.config.LoggerConfig loggerConfig : map.values()) {
                String key = loggerConfig.getName();
                if (StringUtils.isBlank(key)) {
                    key = "root";
                }
                loggerMap.put(key, loggerConfig);
            }
        } else {
            LOG.error("Log框架无法识别: type={}", type);
        }
        LOG.info("LoggerMap={}", loggerMap.keySet());
    }





    /**
     * 远程方法调用
     *
     * @param data
     * @return
     */
    public String invoke(String data) throws Exception {
        LOG.info("invoke: data={}", data);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(data);
            JsonNode opTypejson = jsonNode.get("opType");
            String opType = opTypejson.textValue();
            if (StringUtils.isBlank(opType)) {
                throw new RuntimeException("操作类型不能为空");
            } else if ("get".equals(opType.toLowerCase())) {
                return getLoggerList();
            } else if ("set".equals(opType.toLowerCase())) {
                JsonNode loggerListJson = jsonNode.get("loggerList");
                return setLogLevel(loggerListJson);
            } else {
                throw new RuntimeException(String.format("未知指令 : %s", opType));
            }
        } catch (IOException e) {
            LOG.error("参数反序列化失败, 请检查JSON参数 {}", data, e);
            throw new Exception(String.format("参数反序列化失败, 请检查JSON参数"));
        }

    }

    /**
     * 获取Logger列表
     *
     * @return
     */
    private String getLoggerList() {

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.createObjectNode();

        ArrayNode arrayNode = objectMapper.createArrayNode();

        for (ConcurrentMap.Entry<String, Object> entry : loggerMap.entrySet()) {
            ObjectNode loggerJSON = objectMapper.createObjectNode();
            loggerJSON.put("loggerName", entry.getKey());

            if (logFrameworkType == LogFrameworkType.LOG4J) {
//                org.apache.log4j.Logger targetLogger = (org.apache.log4j.Logger) entry.getValue();
//                loggerJSON.put("logLevel", targetLogger.getLevel().toString());
            } else if (logFrameworkType == LogFrameworkType.LOGBACK) {
//                ch.qos.logback.classic.Logger targetLogger = (ch.qos.logback.classic.Logger) entry.getValue();
//                loggerJSON.put("logLevel", targetLogger.getLevel().toString());
            } else if (logFrameworkType == LogFrameworkType.LOG4J2) {
                org.apache.logging.log4j.core.config.LoggerConfig targetLogger = (org.apache.logging.log4j.core.config.LoggerConfig) entry.getValue();
                loggerJSON.put("logLevel", targetLogger.getLevel().toString());
            } else {
                loggerJSON.put("logLevel", "Logger的类型未知,无法处理!");
            }
            arrayNode.add(loggerJSON);
        }
        objectNode.set("loggerList", arrayNode);
        LOG.info("getLoggerList: result={}", objectNode.toString());
        return objectNode.toString();
    }

    /**
     * 修改日志级别
     *
     * @param data
     * @return
     */
    private String setLogLevel(JsonNode data) {
        LOG.info("setLogLevel: data={}", data);
        List<LoggerBean> loggerList = parseJsonData(data);
        if (loggerList == null || loggerList.isEmpty()) {
            return "";
        }
        for (LoggerBean loggerbean : loggerList) {
            Object logger = loggerMap.get(loggerbean.getName());
            if (logger == null) {
                throw new RuntimeException("需要修改日志级别的Logger不存在");
            }
            if (logFrameworkType == LogFrameworkType.LOG4J) {
//                org.apache.log4j.Logger targetLogger = (org.apache.log4j.Logger) logger;
//                org.apache.log4j.Level targetLevel = org.apache.log4j.Level.toLevel(loggerbean.getLevel());
//                targetLogger.setLevel(targetLevel);
            } else if (logFrameworkType == LogFrameworkType.LOGBACK) {
//                ch.qos.logback.classic.Logger targetLogger = (ch.qos.logback.classic.Logger) logger;
//                ch.qos.logback.classic.Level targetLevel = ch.qos.logback.classic.Level.toLevel(loggerbean.getLevel());
//                targetLogger.setLevel(targetLevel);
            } else if (logFrameworkType == LogFrameworkType.LOG4J2) {
                org.apache.logging.log4j.core.config.LoggerConfig loggerConfig = (org.apache.logging.log4j.core.config.LoggerConfig) logger;
                org.apache.logging.log4j.Level targetLevel = org.apache.logging.log4j.Level.toLevel(loggerbean.getLevel());
                loggerConfig.setLevel(targetLevel);
                org.apache.logging.log4j.core.LoggerContext ctx = (org.apache.logging.log4j.core.LoggerContext) org.apache.logging.log4j.LogManager.getContext(false);
                ctx.updateLoggers(); // This causes all Loggers to refetch information from their LoggerConfig.
            } else {
                throw new RuntimeException("Logger的类型未知,无法处理!");
            }
        }
        return "success";
    }

    private List<LoggerBean> parseJsonData(JsonNode data) {
        List<LoggerBean> loggerList = new ArrayList();
        if (data == null) {
            return loggerList;
        }

        JsonNodeType nodeType = data.getNodeType();
        if (nodeType.equals(JsonNodeType.ARRAY)) {
            Iterator<JsonNode> iterator = data.iterator();
            while (iterator.hasNext()) {
                JsonNode next = iterator.next();
                String name = next.get("loggerName").textValue();
                String level = next.get("logLevel").textValue();
                if (StringUtils.isBlank(name) || StringUtils.isBlank(level)) {
                    continue;
                }
                loggerList.add(new LoggerBean(name, level));
            }
        }
        return loggerList;
    }


}
