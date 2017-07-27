package com.practice.hack.groovy;


import com.practice.base.SpringBootstrapBase;

/**
 * Created by fgm on 2017/7/22.
 * 启动类:利用 GroovyClassLoader 动态实例化源码对应类,并将spring的bean注册到源码实例中,做到业务动态执行
 *
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
        //源码实例化成bean
        IJobHandler jobHandler=groovyFactory.loadJobHandler(sourceCode);
        //执行调用
        jobHandler.execute("Hello World,Hello Groovy!");

    }

}
