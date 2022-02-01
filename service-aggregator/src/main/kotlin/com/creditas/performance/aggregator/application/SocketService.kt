package com.creditas.performance.aggregator.application

import com.creditas.performance.aggregator.utils.ReadFile
import org.apache.http.conn.ssl.NoopHostnameVerifier
import org.apache.tomcat.websocket.Constants.SSL_CONTEXT_PROPERTY
import org.springframework.messaging.converter.MappingJackson2MessageConverter
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
import org.springframework.stereotype.Service
import org.springframework.web.socket.client.standard.StandardWebSocketClient
import org.springframework.web.socket.messaging.WebSocketStompClient
import java.security.SecureRandom
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager

@Service
class SocketService(private val readFile: ReadFile) {

    fun getBatchResponse(total: Int, reps: Int) {

        val trustAllCerts = arrayOf<TrustManager>(MyTrustManager())

        HttpsURLConnection.setDefaultHostnameVerifier(NoopHostnameVerifier())
        val sc = SSLContext.getInstance("SSL")
        sc.init(null, trustAllCerts, SecureRandom())
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.socketFactory)

        val userProperties = mapOf<String, Any>(SSL_CONTEXT_PROPERTY to sc)

        val taskScheduler = ThreadPoolTaskScheduler()
        taskScheduler.afterPropertiesSet()
        val standardWebSocketClient = StandardWebSocketClient()
        standardWebSocketClient.setUserProperties(userProperties)
        val webSocketStompClient = WebSocketStompClient(standardWebSocketClient)
        val heartbeat = longArrayOf(20000, 10000)
        webSocketStompClient.taskScheduler = taskScheduler
        webSocketStompClient.defaultHeartbeat = heartbeat
        webSocketStompClient.messageConverter = MappingJackson2MessageConverter()
        webSocketStompClient.connect(
            "ws://localhost:8000/ws",
            MyStompSessionHandler(total, reps, "/app/message/batch")
        )
    }
}
