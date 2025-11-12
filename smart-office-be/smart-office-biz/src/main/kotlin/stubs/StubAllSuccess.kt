package ru.otus.otuskotlin.smartoffice.biz.stubs

import ru.otus.otuskotlin.smartoffice.cor.ICorChainDsl
import ru.otus.otuskotlin.smartoffice.cor.worker
import ru.otus.otuskotlin.smartoffice.common.OfficeContext
import ru.otus.otuskotlin.smartoffice.common.OfficeCorSettings
import ru.otus.otuskotlin.smartoffice.common.models.*
import ru.otus.otuskotlin.smartoffice.common.stubs.OfficeStubs
import ru.otus.otuskotlin.smartoffice.logging.common.LogLevel
import ru.otus.otuskotlin.smartoffice.stubs.OfficeBookingStub

fun ICorChainDsl<OfficeContext>.stubAllSuccess(title: String, corSettings: OfficeCorSettings) = worker {
    this.title = title
    this.description = """
        Кейс успеха для отображения всех бронирований пользователя
    """.trimIndent()
    on { stubCase == OfficeStubs.SUCCESS && state == OfficeState.RUNNING }
    val logger = corSettings.loggerProvider.logger("stubAllSuccess")
    handle {
        logger.doWithLogging(id = this.requestId.asString(), LogLevel.DEBUG) {
            state = OfficeState.FINISHING
            bookingsResponse.addAll(OfficeBookingStub.prepareBookingsList(bookingFilterRequest.userId,
                bookingFilterRequest.startTime,
                bookingFilterRequest.endTime,
                bookingFilterRequest.status))
        }
    }
}
