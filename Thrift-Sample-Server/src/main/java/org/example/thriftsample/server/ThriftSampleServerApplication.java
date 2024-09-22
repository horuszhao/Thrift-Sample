package org.example.thriftsample.server;

import org.example.thriftsample.api.Calculator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

@SpringBootApplication
public class ThriftSampleServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThriftSampleServerApplication.class, args);

        try {
            CalculatorHandler handler = new CalculatorHandler();
            Calculator.Processor<CalculatorHandler> processor = new Calculator.Processor<>(handler);

            TServerTransport serverTransport = new TServerSocket(9090);
            TServer server = new TSimpleServer(new TServer.Args(serverTransport).processor(processor));

            System.out.println("Starting the server...");
            server.serve();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
