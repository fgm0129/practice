package com.practice.thread.queue;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by fgm on 2017/7/22.
 */
@Data
public class TriggerParam implements Serializable{
    private static final long serialVersionUID = 42L;
    private int jobId;

    private String executorHandler;
    private String executorParams;
    private String executorBlockStrategy;

    private String glueType;
    private String glueSource;
    private long glueUpdateTime;

    private int logId;
    private long logDateTime;

}
