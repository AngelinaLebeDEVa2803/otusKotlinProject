package ru.otus.otuskotlin.smartoffice.biz.stubs

import kotlinx.datetime.Instant
import ru.otus.otuskotlin.smartoffice.cor.ICorChainDsl
import ru.otus.otuskotlin.smartoffice.cor.worker
import ru.otus.otuskotlin.smartoffice.common.OfficeContext
import ru.otus.otuskotlin.smartoffice.common.OfficeCorSettings
import ru.otus.otuskotlin.smartoffice.common.models.*
import ru.otus.otuskotlin.smartoffice.common.NONE
import ru.otus.otuskotlin.smartoffice.common.stubs.OfficeStubs
import ru.otus.otuskotlin.smartoffice.logging.common.LogLevel
import ru.otus.otuskotlin.smartoffice.stubs.OfficeBookingStub

fun ICorChainDsl<OfficeContext>.stubCreateSuccess(title: String, corSettings: OfficeCorSettings) = worker {
    this.title = title
    this.description = """
        Кейс успеха для создания бронирования
    """.trimIndent()
    on { stubCase == OfficeStubs.SUCCESS && state == OfficeState.RUNNING }
    val logger = corSettings.loggerProvider.logger("stubCreateSuccess")
    handle {
        logger.doWithLogging(id = this.requestId.asString(), LogLevel.DEBUG) {
            state = OfficeState.FINISHING
            val stub = OfficeBookingStub.prepareResult {
                bookingRequest.userId.takeIf { it != OfficeUserId.NONE }?.also { this.userId = it }
                bookingRequest.floorId.takeIf { it != OfficeFloorId.NONE }?.also { this.floorId = it }
                bookingRequest.roomId.takeIf { it != OfficeRoomId.NONE }?.also { this.roomId = it }
                bookingRequest.workspaceId.takeIf { it != OfficeWorkspaceId.NONE }?.also { this.workspaceId = it }

                bookingRequest.startTime.takeIf { it != Instant.NONE }?.also { this.startTime = it }
                bookingRequest.endTime.takeIf { it != Instant.NONE }?.also { this.endTime = it }

                bookingRequest.status.takeIf { it != OfficeBookingStatus.NONE }?.also { this.status = it }
            }
            bookingResponse = stub
        }
    }
}
