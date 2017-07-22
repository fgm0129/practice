package com.practice.hack.groovy;


import com.practice.base.SpringBootstrapBase;

/**
 * Created by fgm on 2017/7/22.
 */
public class GroovyBootstrap extends SpringBootstrapBase {




    public static void main(String[] args) throws Exception {
        String sourceCode="package com.practice.hack.groovy;\n" +
                "\n" +
                "import org.slf4j.Logger;\n" +
                "import org.slf4j.LoggerFactory;\n" +
                "import org.springframework.beans.factory.annotation.Autowired;\n" +
                "\n" +
                "/**\n" +
                " * Created by fgm on 2017/7/22.\n" +
                " *\n" +
                " * the demo of sourceCode  提供源码的类,不直接执行\n" +
                " */\n" +
                "public class HelloJobHandler extends IJobHandler {\n" +
                "    private static  Logger log= LoggerFactory.getLogger(HelloJobHandler.class);\n" +
                "    @Autowired\n" +
                "    private BusinessExecutor businessExecutor;\n" +
                "    public String execute(String... params) throws Exception {\n" +
                "        log.info(\"before execute helloJobBean\");\n" +
                "        businessExecutor.execute(params);\n" +
                "        log.info(\"after execute helloJobBean\");\n" +
                "        return SUCCESS;\n" +
                "    }\n" +
                "}";
        GroovyFactory groovyFactory=GroovyFactory.getInstance();
        IJobHandler jobHandler=groovyFactory.loadJobHandler(sourceCode);
        jobHandler.execute("Hello World,Hello Groovy!");

    }

}
