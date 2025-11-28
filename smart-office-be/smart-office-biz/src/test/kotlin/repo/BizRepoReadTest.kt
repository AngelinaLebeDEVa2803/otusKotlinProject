package ru.otus.otuskotlin.smartoffice.biz.repo

import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Instant
import repo.repoNotFoundTest
import ru.otus.otuskotlin.smartoffice.repo.tests.BookingRepositoryMock
import ru.otus.otuskotlin.smartoffice.biz.OfficeBookingProcessor
import ru.otus.otuskotlin.smartoffice.common.OfficeContext
import ru.otus.otuskotlin.smartoffice.common.OfficeCorSettings
import ru.otus.otuskotlin.smartoffice.common.models.*
import ru.otus.otuskotlin.smartoffice.common.repo.DbBookingResponseOk
import kotlin.test.Test
import kotlin.test.assertEquals

class BizRepoReadTest {

    private val bookingId = OfficeBookingId("b4285985-1d55-4f75-ad25-92f592e0d965")
    private val userId = OfficeUserId("321")
    private val floorId = OfficeFloorId("floor_666")
    private val roomId = OfficeRoomId("room_9")
    private val workspaceId = OfficeWorkspaceId("040")

    private val startT = Instant.parse("2028-02-19T09:00:00Z")
    private val endT = Instant.parse("2028-02-19T19:00:00Z")
    private val command = OfficeCommand.READ
    private val initBooking = OfficeBooking(
        id = bookingId,
        userId = userId,
        floorId = floorId,
        roomId = roomId,
        workspaceId = workspaceId,
        startTime = startT,
        endTime = endT,
        status = OfficeBookingStatus.ACTIVE,
    )
    private val repo = BookingRepositoryMock(
        invokeReadBooking = {
            DbBookingResponseOk(
                data = initBooking,
            )
        }
    )
    private val settings = OfficeCorSettings(repoTest = repo)
    private val processor = OfficeBookingProcessor(settings)

    @Test
    fun repoReadSuccessTest() = runTest {
        val ctx = OfficeContext(
            command = command,
            state = OfficeState.NONE,
            workMode = OfficeWorkMode.TEST,
            bookingRequest = OfficeBooking(
                id = bookingId,
            ),
        )
        processor.exec(ctx)
        assertEquals(OfficeState.FINISHING, ctx.state)
        assertEquals(initBooking.id, ctx.bookingResponse.id)
        assertEquals(userId, ctx.bookingResponse.userId)
        assertEquals(floorId, ctx.bookingResponse.floorId)
        assertEquals(roomId, ctx.bookingResponse.roomId)
        assertEquals(workspaceId, ctx.bookingResponse.workspaceId)
        assertEquals(startT, ctx.bookingResponse.startTime)
        assertEquals(endT, ctx.bookingResponse.endTime)
        assertEquals(OfficeBookingStatus.ACTIVE, ctx.bookingResponse.status)
    }

    @Test
    fun repoReadNotFoundTest() = repoNotFoundTest(command)
}
