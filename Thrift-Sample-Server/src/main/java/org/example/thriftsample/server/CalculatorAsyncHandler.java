package org.example.thriftsample.server;

import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.example.thriftsample.api.Calculator;

public class CalculatorAsyncHandler implements Calculator.AsyncIface{

    @Override
    public void add(int num1, int num2, AsyncMethodCallback<Integer> resultHandler) throws TException {
        if(num1==0 || num2==0){
            resultHandler.onError(new Exception("Data Error"));
        }else {
            resultHandler.onComplete(num1 + num2);
        }
    }
}
