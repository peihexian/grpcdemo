package com.shinow.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@SpringBootApplication
public class ServerApplication {
	private static final Logger log = Logger.getLogger(ServerApplication.class.getName());

	private Server server;

	public static void main(String[] args) throws IOException, InterruptedException {
		final ServerApplication server = new ServerApplication();
		server.start();
		server.blockUntilShutdown();
	}

	private void start() throws IOException {
		int port = 41005;
		//1.forPort 指定监听客户端请求的端口
		//2.创建我们的服务端实现类的实例GreeterImpl并将传递给构建器的addService方法
		//3.调用build （）并 start（）在构建器上为我们的服务创建和启动RPC服务器
		server = ServerBuilder.forPort(port)
				.addService(new SayHelloImpl())
				.build()
				.start();
		log.info("Server stated , listener on port:" + port);
		//JVM关闭时调用的钩子
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public synchronized void start() {
				System.err.println("*** shutting down gRPC server since JVM is shutting down");
				ServerApplication.this.stop();
				System.err.println("*** server shut down");
			}
		});
	}

	private void stop() {
		if (null != server) {
			server.shutdown();
		}
	}

	/**
	 * Await termination on the main thread since the grpc library uses daemon threads.
	 *
	 * @throws InterruptedException
	 */
	private void blockUntilShutdown() throws InterruptedException {
		if (null != server) {
			server.awaitTermination();
		}
	}
}
