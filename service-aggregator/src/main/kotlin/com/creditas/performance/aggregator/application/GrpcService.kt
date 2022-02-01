package com.creditas.performance.aggregator.application

import creditas.fileimporter.HelloBatchServiceGrpc
import creditas.fileimporter.HelloRequest
import io.grpc.ManagedChannelBuilder
import org.springframework.stereotype.Service
import org.springframework.util.StopWatch

@Service
class GrpcService {

    companion object {
        private val channel =
            ManagedChannelBuilder.forAddress("localhost", 6565)
                .usePlaintext()
                .build()
        private val helloBatchService = HelloBatchServiceGrpc.newBlockingStub(channel)
    }

    fun getBatchResponse(total: Int, reps: Int) {

        val stopWatch = StopWatch()
        val lines = readFileUsingGetResource("test_stress_ID_caterina_500.csv")
        val names = (0..total).map { n -> "name$n" }
        stopWatch.start()

        names
            .chunked(reps)
            .forEach {
                helloBatchService
                    .helloBatch(HelloRequest.newBuilder().addAllNames(it).build())
            }

        stopWatch.stop()
        println(stopWatch.prettyPrint())
    }

    private fun readFileUsingGetResource(fileName: String) =
        this::class.java.classLoader.getResourceAsStream(fileName).bufferedReader().readLines()
}
