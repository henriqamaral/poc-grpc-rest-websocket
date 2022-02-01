package com.creditas.performance.aggregator.infrastructure.controller

import com.creditas.performance.aggregator.application.GrpcService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("grpc")
class GrpcApiController(private val grpcService: GrpcService) {

    @GetMapping("/{total}")
    fun grpc(@PathVariable total: Int) {
        grpcService.getResponse(total)
    }

    @GetMapping("/batch/{total}/reps/{reps}")
    fun rpcBatch(@PathVariable total: Int, @PathVariable reps: Int) {
        grpcService.getBatchResponse(total, reps)
    }
}