package com.creditas.performance.aggregator.utils

import org.springframework.stereotype.Component

@Component
class ReadFile {

    fun readFileUsingGetResource(fileName: String) = this::class.java.getResource(fileName).readText(Charsets.UTF_8)
}
