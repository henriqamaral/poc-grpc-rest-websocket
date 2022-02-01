package com.creditas.performance.aggregator.infrastructure.controller

import com.creditas.performance.aggregator.application.SocketService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("socket")
class SocketApiController(private val socketService: SocketService) {

    @GetMapping("/batch/{total}/reps/{reps}")
    fun restBatch(@PathVariable total: Int, @PathVariable reps: Int) {
        socketService.getBatchResponse(total, reps)
    }
}
