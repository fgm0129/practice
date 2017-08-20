package com.practice.hack.changelog;

/**
 * Created by fgm on 2017/8/20.
 */
public class LoggerBean {
    String name;
    String level;
    public LoggerBean(String name, String level) {
        this.name = name;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

}
