package com.shinow.grpc;

import com.google.protobuf.ByteString;
import com.test.rpc.SayHelloServiceGrpc;
import com.test.rpc.Service;
import io.grpc.netty.shaded.io.grpc.netty.NegotiationType;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

@SpringBootApplication
public class ClientApplication {

	private static final String host="172.0.16.111";
	private static final int port=41005;

	public static void main(String[] args) {
		SpringApplication.run(ClientApplication.class, args);

		io.grpc.Channel channel = NettyChannelBuilder.forAddress(host, port)
				.negotiationType(NegotiationType.PLAINTEXT)
				.build();

		Service.SayHelloRequest req=Service.SayHelloRequest.newBuilder().setName(ByteString.copyFrom("测试", Charset.forName("utf-8"))).build();

		Service.SayHelloResponse result= SayHelloServiceGrpc.newBlockingStub(channel).sayHello(req);
		try {
			System.out.println(result.getResult().toString("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	}

}
