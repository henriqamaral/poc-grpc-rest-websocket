package com.creditas.performancetest.infrastructure.controller

import com.creditas.performancetest.infrastructure.entity.LineEntity
import com.creditas.performancetest.infrastructure.jpa.LineRepository
import org.springframework.util.StopWatch
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("rest")
class PostControllerTest(private val lineRepository: LineRepository) {

    companion object {
        private const val total = 500
    }

    @CrossOrigin(origins = ["*"])
    @PostMapping("/batch")
    fun postBatch(@RequestBody list: ListRequest): ListResponse {
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
