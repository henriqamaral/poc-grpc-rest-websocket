package com.creditas.performance.aggregator.infrastructure.controller

import com.creditas.performance.aggregator.application.RestService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("rest")
class RestApiController(val restService: RestService) {

    @GetMapping("/{total}")
    fun rest(@PathVariable total: Int) {
        restService.getResponse(total)
    }

    @GetMapping("/batch/{total}/reps/{reps}")
    fun restBatch(@PathVariable total: Int, @PathVariable reps: Int) {
        restService.getBatchResponse(total, reps)
    }
}
