package ru.otus.otuskotlin.smartoffice.biz

import ru.otus.otuskotlin.smartoffice.common.OfficeContext
import ru.otus.otuskotlin.smartoffice.common.OfficeCorSettings
import ru.otus.otuskotlin.smartoffice.common.models.OfficeState
import ru.otus.otuskotlin.smartoffice.stubs.OfficeBookingStub

@Suppress("unused", "RedundantSuspendModifier")
class OfficeBookingProcessor(val corSettings: OfficeCorSettings) {
    suspend fun exec(ctx: OfficeContext) {
        ctx.bookingResponse = OfficeBookingStub.get()
        ctx.state = OfficeState.RUNNING
    }
}
