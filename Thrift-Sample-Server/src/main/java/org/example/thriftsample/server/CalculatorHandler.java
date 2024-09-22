package org.example.thriftsample.server;

import org.example.thriftsample.api.Calculator;

public class CalculatorHandler implements Calculator.Iface{
    @Override
    public int add(int num1, int num2) throws org.apache.thrift.TException {
        System.out.println("Received add(" + num1 + ", " + num2 + ")");
        return num1 + num2;
    }
}
