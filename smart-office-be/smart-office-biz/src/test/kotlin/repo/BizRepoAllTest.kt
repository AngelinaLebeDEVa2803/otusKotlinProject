package ru.otus.otuskotlin.smartoffice.biz.repo

import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Instant
import ru.otus.otuskotlin.smartoffice.repo.tests.BookingRepositoryMock
import ru.otus.otuskotlin.smartoffice.biz.OfficeBookingProcessor
import ru.otus.otuskotlin.smartoffice.common.OfficeContext
import ru.otus.otuskotlin.smartoffice.common.OfficeCorSettings
import ru.otus.otuskotlin.smartoffice.common.models.*
import ru.otus.otuskotlin.smartoffice.common.repo.DbBookingsResponseOk
import kotlin.test.Test
import kotlin.test.assertEquals

class BizRepoAllTest {

    private val bookingId = OfficeBookingId("b4285985-1d55-4f75-ad25-92f592e0d965")
    private val userId = OfficeUserId("321")
    private val floorId = OfficeFloorId("floor_666")
    private val roomId = OfficeRoomId("room_9")
    private val workspaceId = OfficeWorkspaceId("040")

    private val startT = Instant.parse("2028-01-01T09:00:00Z")
    private val endT = Instant.parse("2029-02-19T19:00:00Z")
    private val lock = OfficeBookingLock("123")
    
    private val command = OfficeCommand.ALL
    private val initBooking = OfficeBooking(
        id = bookingId,
        userId = userId,
        floorId = floorId,
        roomId = roomId,
        workspaceId = workspaceId,
        startTime = Instant.parse("2028-02-19T09:00:00Z"),
        endTime = Instant.parse("2028-02-19T18:00:00Z"),
        status = OfficeBookingStatus.ACTIVE,
        lock = lock,
    )
    private val repo = BookingRepositoryMock(
        invokeAllBooking = {
            DbBookingsResponseOk(
                data = listOf(initBooking),
            )
        }
    )
    private val settings = OfficeCorSettings(repoTest = repo)
    private val processor = OfficeBookingProcessor(settings)

    @Test
    fun repoAllSuccessTest() = runTest {
        val ctx = OfficeContext(
            command = command,
            state = OfficeState.NONE,
            workMode = OfficeWorkMode.TEST,
            bookingFilterRequest = OfficeBookingFilter(
                userId = userId,
                startTime = startT,
                endTime = endT,
            ),
        )
        processor.exec(ctx)
        assertEquals(OfficeState.FINISHING, ctx.state)
        assertEquals(1, ctx.bookingsResponse.size)
    }
}
