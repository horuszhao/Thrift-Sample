package org.example.thriftsample.client;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.example.thriftsample.api.Calculator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

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
}
