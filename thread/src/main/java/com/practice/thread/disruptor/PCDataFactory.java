package com.practice.thread.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * Created by fgm on 2017/7/9.
 */
public class PCDataFactory implements EventFactory<PCData>{
    public PCData newInstance() {
        return new PCData();
    }
}
