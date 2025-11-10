package ru.otus.otuskotlin.smartoffice.app.spring.controllers

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactor.asFlux
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Component
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.publisher.Mono
import ru.otus.otuskotlin.smartoffice.app.spring.base.OfficeAppSettings
import ru.otus.otuskotlin.smartoffice.app.spring.base.SpringWsSessionV1
import ru.otus.otuskotlin.smartoffice.api.v1.apiV1Mapper
import ru.otus.otuskotlin.smartoffice.api.v1.models.IRequest
import ru.otus.otuskotlin.smartoffice.app.common.controllerHelper
import ru.otus.otuskotlin.smartoffice.common.OfficeContext
import ru.otus.otuskotlin.smartoffice.common.models.OfficeCommand
import ru.otus.otuskotlin.smartoffice.mappers.v1.fromTransport
import ru.otus.otuskotlin.smartoffice.mappers.v1.toTransportBooking

@Component
class BookingControllerV1Ws(private val appSettings: OfficeAppSettings) : WebSocketHandler {
    private val sessions = appSettings.corSettings.wsSessions

    override fun handle(session: WebSocketSession): Mono<Void> {
//        // Обслуживаем INIT логику
//
//        // Получаем поток входящих сообщений
//        val input = session.receive()
//        // Формируем поток исходящих сообщений
//        val output1 = input
//            .map {session.textMessage("Echo $it")}
//            .doOnComplete {
//                // Можно выполнить логику FINISH
//            }
//            .onErrorComplete {
//                // Обработка ошибок
//                true
//            }
//        return@runBlocking session.send(output1)


        val officeSess = SpringWsSessionV1(session)
        sessions.add(officeSess)
        val messageObj = flow {
            emit(process("ws-v1-init") {
                command = OfficeCommand.INIT
                wsSession = officeSess
            })
        }

        val messages = session.receive().asFlow()
            .map { message ->
                process("ws-v1-handle") {
                    wsSession = officeSess
                    val request = apiV1Mapper.readValue(message.payloadAsText, IRequest::class.java)
                    fromTransport(request)
                }
            }

        val output = merge(messageObj, messages)
            .onCompletion {
                process("ws-v1-finish") {
                    wsSession = officeSess
                    command = OfficeCommand.FINISH
                }
                sessions.remove(officeSess)
            }
            .map { session.textMessage(apiV1Mapper.writeValueAsString(it)) }
            .asFlux()
        return session.send(output)
    }

    private suspend fun process(logId: String, function: OfficeContext.() -> Unit) = appSettings.controllerHelper(
        getRequest = function,
        toResponse = OfficeContext::toTransportBooking,
        clazz = this@BookingControllerV1Ws::class,
        logId = logId,
    )
}
