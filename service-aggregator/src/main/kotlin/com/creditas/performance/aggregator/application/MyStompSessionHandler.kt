package com.creditas.performance.aggregator.application

import org.springframework.messaging.simp.stomp.StompCommand
import org.springframework.messaging.simp.stomp.StompHeaders
import org.springframework.messaging.simp.stomp.StompSession
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter
import org.springframework.util.StopWatch
import java.lang.reflect.Type

class MyStompSessionHandler(
    private val size: Int,
    private val destination: String = "/app/message",
) :
    StompSessionHandlerAdapter() {

    private var stopWatch: StopWatch? = null
    private var currentPos = 0
    private var totalCalls = 0

    override fun afterConnected(session: StompSession, connectedHeaders: StompHeaders) {
        session.subscribe("/topic/message", this)

        val lines = readFileUsingGetResource("test_stress_ID_caterina_500.csv")
        val headers = lines[0].split(";")
        val map = lines
            .map { line ->
                line.split(";").mapIndexed { index, s ->
                    headers[index] to s
                }
                    .associate { pair -> pair.first to pair.second }
            }

        stopWatch = StopWatch()
        val names = (0..size).map { n -> "name$n" }
        stopWatch?.start()
        totalCalls = lines.size / 20
        map
            .chunked(20)
            .forEach {
                session.send(destination, buildMessage(it))
            }
        currentPos = 0
    }

    override fun getPayloadType(headers: StompHeaders): Type {
        return ListResponse::class.java
    }

    override fun handleFrame(headers: StompHeaders, payload: Any?) {
        println("finished?")
    }

    override fun handleException(
        session: StompSession,
        command: StompCommand?,
        headers: StompHeaders,
        payload: ByteArray,
        exception: Throwable,
    ) {
        currentPos++
        if (currentPos == totalCalls) {
            println("finished")
            stopWatch?.stop()
            println(stopWatch?.prettyPrint())
        }
    }

    override fun handleTransportError(session: StompSession, exception: Throwable) {
        println("error error: ${exception.message}")
    }

    private fun buildMessage(names: List<Map<String, String>>): ListRequest {
        return ListRequest(names)
    }

    private fun readFileUsingGetResource(fileName: String) =
        this::class.java.classLoader.getResourceAsStream(fileName).bufferedReader().readLines()

    data class ListRequest(val lines: List<Map<String, String>>)
    data class ListResponse(val lines: List<Map<String, String>>)
}
