package com.creditas.performance.aggregator.config

import org.apache.http.conn.ssl.NoopHostnameVerifier
import org.apache.http.impl.client.HttpClients
import org.apache.http.ssl.SSLContextBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.web.client.RestTemplate
import java.security.cert.X509Certificate

@Configuration
class RestConfig {

    @Bean
    fun restTemplate(): RestTemplate {
        val sslContext = SSLContextBuilder()
            .loadTrustMaterial(null) { _: Array<X509Certificate>, _: String -> true }.build()
        val httpClient = HttpClients.custom().setSSLContext(sslContext)
            .setSSLHostnameVerifier(NoopHostnameVerifier())
            .build()
        val requestFactory = HttpComponentsClientHttpRequestFactory()
        requestFactory.httpClient = httpClient
        return RestTemplate(requestFactory)
    }
}
