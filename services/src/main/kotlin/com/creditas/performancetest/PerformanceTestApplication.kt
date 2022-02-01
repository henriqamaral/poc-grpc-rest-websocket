package com.creditas.performancetest

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PerformanceTestApplication

fun main(args: Array<String>) {
    runApplication<PerformanceTestApplication>(*args)
}
