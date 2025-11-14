package ru.otus.otuskotlin.smartoffice.biz.validation

import kotlinx.datetime.Instant
import ru.otus.otuskotlin.smartoffice.common.NONE
import kotlinx.coroutines.test.runTest
import ru.otus.otuskotlin.smartoffice.common.OfficeContext
import ru.otus.otuskotlin.smartoffice.common.models.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class BizValidationSearchTest: BaseBizValidationTest() {
    override val command = OfficeCommand.ALL

    @Test
    fun correctEmpty() = runTest {
        val ctx = OfficeContext(
            command = command,
            state = OfficeState.NONE,
            workMode = OfficeWorkMode.TEST,
            bookingFilterRequest = OfficeBookingFilter(userId = OfficeUserId("123"),
                startTime = Instant.NONE,
                endTime = Instant.NONE,
                status = OfficeBookingStatus.NONE)
        )
        processor.exec(ctx)
        assertEquals(0, ctx.errors.size)
        assertNotEquals(OfficeState.FAILING, ctx.state)
    }
}
