package org.example.thriftsample.client;

import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.async.AsyncMethodFutureAdapter;
import org.apache.thrift.async.TAsyncClientManager;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.TNonblockingSocket;
import org.apache.thrift.transport.TNonblockingTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.example.thriftsample.api.Calculator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@RequestMapping("/thrift")
@RestController
public class ThriftController {

    @GetMapping("add")
    public Integer add(@PathParam("a") int a, @PathParam("b") int b) {
        try {
            TTransport transport = new TSocket("127.0.0.1", 9090);
            transport.open();

            TProtocol protocol = new TBinaryProtocol(transport);
            Calculator.Client client = new Calculator.Client(protocol);

            int result = client.add(a, b);

            System.out.println("Result: " + a + " + " + b + " = " + result);

            transport.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @GetMapping("addAsync")
    public Integer addAsync(@PathParam("a") int a, @PathParam("b") int b) {
        try {
            // 异步客户端管理器
            TAsyncClientManager clientManager = new TAsyncClientManager();
            // 非阻塞的传输层
            TNonblockingTransport transport = new TNonblockingSocket("127.0.0.1", 9090);
            // 使用二进制协议
            TProtocolFactory protocolFactory = new TBinaryProtocol.Factory();

            // 创建异步客户端
            Calculator.AsyncClient asyncClient = new Calculator.AsyncClient(protocolFactory, clientManager, transport);

            // 调用 add 方法并提供回调函数
            System.out.println("Calling add method asynchronously...");
            asyncClient.add(a, b, new AsyncMethodCallback<Integer>() {
                @Override
                public void onComplete(Integer response) {
                    // 当异步调用成功时，调用此方法
                    System.out.println("Result: " + response);
                }

                @Override
                public void onError(Exception exception) {
                    // 处理调用中的错误
                    System.out.println("Error during async call: " + exception.getMessage());
                }
            });

            // 防止主线程退出，等待异步回调完成
            Thread.sleep(3000);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
}
