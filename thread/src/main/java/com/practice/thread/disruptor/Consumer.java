package com.practice.thread.disruptor;

import com.lmax.disruptor.WorkHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by fgm on 2017/7/9.
 */
@Slf4j
public class Consumer implements WorkHandler<PCData> {
    public void onEvent(PCData pcData) throws Exception {
        log.info("{} Event:--{}--",Thread.currentThread().getId(),pcData.getValue()*pcData.getValue());
    }
}
