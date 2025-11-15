package ru.otus.otuskotlin.smartoffice.biz.stubs

import ru.otus.otuskotlin.smartoffice.cor.ICorChainDsl
import ru.otus.otuskotlin.smartoffice.cor.worker
import ru.otus.otuskotlin.smartoffice.common.OfficeContext
import ru.otus.otuskotlin.smartoffice.common.OfficeCorSettings
import ru.otus.otuskotlin.smartoffice.common.models.OfficeBookingId
import ru.otus.otuskotlin.smartoffice.common.models.OfficeState
import ru.otus.otuskotlin.smartoffice.common.stubs.OfficeStubs
import ru.otus.otuskotlin.smartoffice.logging.common.LogLevel
import ru.otus.otuskotlin.smartoffice.stubs.OfficeBookingStub

fun ICorChainDsl<OfficeContext>.stubReadSuccess(title: String, corSettings: OfficeCorSettings) = worker {
    this.title = title
    this.description = """
        Кейс успеха для чтения бронирования
    """.trimIndent()
    on { stubCase == OfficeStubs.SUCCESS && state == OfficeState.RUNNING }
    val logger = corSettings.loggerProvider.logger("stubReadSuccess")
    handle {
        logger.doWithLogging(id = this.requestId.asString(), LogLevel.DEBUG) {
            state = OfficeState.FINISHING
            val stub = OfficeBookingStub.prepareResult {
                bookingRequest.id.takeIf { it != OfficeBookingId.NONE }?.also { this.id = it }
            }
            bookingResponse = stub
        }
    }
}
