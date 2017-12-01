package com.practice.rpc.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

/**
 * Created by fgm on 2017/12/1.
 */
public class PropertiesUtil {

    private static final String classpath;

    static{
        classpath=PropertiesUtil.class.getClassLoader().getResource("").getPath();
    }

    public static Properties load(String fileName){
        Properties properties=new Properties();
        try {
            List<String> list=Files.readAllLines(Paths.get(classpath+fileName), StandardCharsets.UTF_8);
            for(String row:list){
                String[] rows=row.split("=");
                properties.put(rows[0],rows[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return properties;

    }


}
