package com.creditas.performance.aggregator.application

import org.springframework.http.HttpEntity
import org.springframework.stereotype.Service
import org.springframework.util.StopWatch
import org.springframework.web.client.RestTemplate

@Service
class RestService(private val restTemplate: RestTemplate) {

    fun getBatchResponse(total: Int, reps: Int) {
        val stopWatch = StopWatch()
        val names = (0..total).map { n -> "name$n" }
        stopWatch.start()

        names
            .chunked(reps)
            .forEach {
                val request = ListRequest(it)
                val httpEntity = HttpEntity<ListRequest>(request)
                restTemplate
                    .postForEntity(
                        "http://localhost:8000/rest/batch",
                        httpEntity,
                        String::class.java
                    )
            }

        stopWatch.stop()
        println(stopWatch.prettyPrint())
    }

    data class ListRequest(val names: List<String>)
}
