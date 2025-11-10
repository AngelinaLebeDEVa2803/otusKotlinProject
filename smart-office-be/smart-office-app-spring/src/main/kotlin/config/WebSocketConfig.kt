package ru.otus.otuskotlin.smartoffice.app.spring.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.HandlerMapping
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping
import org.springframework.web.reactive.socket.WebSocketHandler
import ru.otus.otuskotlin.smartoffice.app.spring.controllers.BookingControllerV1Ws


@Suppress("unused")
@Configuration
class WebSocketConfig(
    private val adControllerV1: BookingControllerV1Ws,
) {
    @Bean
    fun handlerMapping(): HandlerMapping {
        val handlerMap: Map<String, WebSocketHandler> = mapOf(
            "/v1/ws" to adControllerV1,
        )
        return SimpleUrlHandlerMapping(handlerMap, 1)
    }
}
