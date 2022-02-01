package com.creditas.performancetest.infrastructure.controller

import com.creditas.performancetest.infrastructure.entity.LineEntity
import com.creditas.performancetest.infrastructure.jpa.LineRepository
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller
import org.springframework.util.StopWatch
import org.springframework.web.bind.annotation.RequestBody

@Controller
class WebSocketController(private val lineRepository: LineRepository) {

    companion object {
        private const val total = 500
    }

    @MessageMapping("/message/batch")
    @SendTo("/topic/message")
    fun socketBatchTest(@RequestBody list: ListRequest): ListResponse {

        println("message received")
        println(list)
        val stopWatch = StopWatch()
        stopWatch.start()
        list
            .lines
            .chunked(total)
            .forEach {
                lineRepository.saveAll(it.map { line -> LineEntity(name = line.getOrDefault("ID", "blank")) })
            }

        stopWatch.stop()
        println(stopWatch.prettyPrint())
        return ListResponse(lines = list.lines)
    }

    data class ListRequest(val lines: List<Map<String, String>>)
    data class ListResponse(val lines: List<Map<String, String>>)
}
