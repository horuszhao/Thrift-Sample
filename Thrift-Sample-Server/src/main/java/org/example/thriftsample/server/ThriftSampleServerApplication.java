package org.example.thriftsample.server;

import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
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
//        startServer();
        startAsyncServer();
    }

    private static void startServer(){
        try {
            CalculatorHandler handler = new CalculatorHandler();
            Calculator.Processor<CalculatorHandler> processor = new Calculator.Processor<>(handler);
            TServerTransport serverTransport = new TServerSocket(9090);
            TServer server = new TSimpleServer(new TServer.Args(serverTransport)
                    .processor(processor));

            System.out.println("Starting the server...");
            server.serve();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private static void startAsyncServer(){

        try {
            // 创建异步处理器
            Calculator.AsyncProcessor<CalculatorAsyncHandler> processor = new Calculator.AsyncProcessor<>(new CalculatorAsyncHandler());

            // 非阻塞传输
            TNonblockingServerTransport serverTransport = new TNonblockingServerSocket(9090);

            // 使用 TThreadedSelectorServer 实现异步服务
            TServer server = new TThreadedSelectorServer(new TThreadedSelectorServer.Args(serverTransport).processor(processor));

            System.out.println("Starting the async server...");
            server.serve();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
