package com.creditas.performancetest.infrastructure.rpc

import com.creditas.performancetest.HelloBatchServiceGrpc
import com.creditas.performancetest.HelloRequest
import com.creditas.performancetest.HelloResponse
import com.creditas.performancetest.infrastructure.entity.LineEntity
import com.creditas.performancetest.infrastructure.jpa.LineRepository
import io.grpc.stub.StreamObserver
import org.lognet.springboot.grpc.GRpcService
import org.springframework.util.StopWatch

@GRpcService
class HelloBatchRpcService(private val lineRepository: LineRepository) :
    HelloBatchServiceGrpc.HelloBatchServiceImplBase() {

    override fun helloBatch(request: HelloRequest, responseObserver: StreamObserver<HelloResponse>) {

        val stopWatch = StopWatch()

        stopWatch.start()
        lineRepository.saveAll(
            request
                .namesList
                .map {
                    LineEntity(name = it)
                }
        )
        stopWatch.stop()
        println(println(stopWatch.prettyPrint()))

        val response = HelloResponse
            .newBuilder()
            .setStatus("OK")
            .build()
        responseObserver.onNext(response)
        responseObserver.onCompleted()
    }
}
