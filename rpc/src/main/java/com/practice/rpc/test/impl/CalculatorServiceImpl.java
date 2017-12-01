package com.practice.rpc.test.impl;

import com.practice.api.CalculatorService;
import com.practice.rpc.aspect.ServiceProvider;
import com.practice.rpc.core.BizException;

/**
 * Created by fgm on 2017/12/1.
 */
@ServiceProvider
public class CalculatorServiceImpl implements CalculatorService {
    @Override
    public double add(double a1, double a2) {
        return a1+a2;
    }

    @Override
    public double subtract(double a1, double a2) {
        return a1-a2;
    }

    @Override
    public double multiply(double a1, double a2) {
        return a1*a2;
    }

    @Override
    public double divide(double a1, double a2) {
        if(a2!=0){
            return a1/a2;
        }
        throw new BizException("cannot divide by zero");
    }
}
