package com.practice.api.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by fgm on 2017/12/1.
 */
public class GreetDto implements Serializable {
    private String greetSentence;
    private String name;
    private Date date;

    public String getGreetSentence() {
        return greetSentence;
    }

    public void setGreetSentence(String greetSentence) {
        this.greetSentence = greetSentence;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
