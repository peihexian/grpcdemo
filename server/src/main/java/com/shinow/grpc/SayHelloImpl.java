package com.shinow.grpc;

import com.google.protobuf.ByteString;
import com.test.rpc.Service;

import java.io.UnsupportedEncodingException;

/**
 * Created by Administrator on 2019/4/12.
 */
public class SayHelloImpl extends com.test.rpc.SayHelloServiceGrpc.SayHelloServiceImplBase {

    public void sayHello(com.test.rpc.Service.SayHelloRequest request,
                         io.grpc.stub.StreamObserver<com.test.rpc.Service.SayHelloResponse> responseObserver) {
        // 获取数据信息
        try {
            String name =request.getName().toString("utf-8");
            // 计算数据
            Service.SayHelloResponse response = Service.SayHelloResponse.newBuilder()
                    .setResult(ByteString.copyFrom("hello from java:"+name,"utf-8"))
                    .build();
            responseObserver.onNext(response);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // 返回数据，完成此次请求
        responseObserver.onCompleted();
    }

}
