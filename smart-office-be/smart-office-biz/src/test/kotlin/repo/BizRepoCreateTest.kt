package ru.otus.otuskotlin.smartoffice.biz.repo

import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Instant
import ru.otus.otuskotlin.smartoffice.repo.tests.BookingRepositoryMock
import ru.otus.otuskotlin.smartoffice.biz.OfficeBookingProcessor
import ru.otus.otuskotlin.smartoffice.common.OfficeContext
import ru.otus.otuskotlin.smartoffice.common.OfficeCorSettings
import ru.otus.otuskotlin.smartoffice.common.models.*
import ru.otus.otuskotlin.smartoffice.common.repo.DbBookingResponseOk
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class BizRepoCreateTest {

    private val userId = OfficeUserId("321")
    private val command = OfficeCommand.CREATE
    private val uuid = "10000000-0000-0000-0000-000000000001"
    private val repo = BookingRepositoryMock(
        invokeCreateBooking = {
            DbBookingResponseOk(
                data = OfficeBooking(
                    id = OfficeBookingId(uuid),
                    userId = userId,
                    floorId = OfficeFloorId("floor_666"),
                    roomId = OfficeRoomId("room_9"),
                    workspaceId = OfficeWorkspaceId("040"),
                    startTime = Instant.parse("2028-02-19T09:00:00Z"),
                    endTime = Instant.parse("2028-02-19T19:00:00Z"),
                    status = it.booking.status,
                )
            )
        }
    )
    private val settings = OfficeCorSettings(
        repoTest = repo
    )
    private val processor = OfficeBookingProcessor(settings)

    @Test
    fun repoCreateSuccessTest() = runTest {
        val ctx = OfficeContext(
            command = command,
            state = OfficeState.NONE,
            workMode = OfficeWorkMode.TEST,
            bookingRequest = OfficeBooking(
                userId = userId,
                floorId = OfficeFloorId("floor_666"),
                roomId = OfficeRoomId("room_9"),
                workspaceId = OfficeWorkspaceId("040"),
                startTime = Instant.parse("2028-02-19T09:00:00Z"),
                endTime = Instant.parse("2028-02-19T19:00:00Z"),
                status = OfficeBookingStatus.ACTIVE,
            ),
        )
        processor.exec(ctx)
        assertEquals(OfficeState.FINISHING, ctx.state)
        assertNotEquals(OfficeBookingId.NONE, ctx.bookingResponse.id)
        assertEquals(userId, ctx.bookingResponse.userId)
//        assertEquals(floorId, ctx.bookingResponse.floorId)
//        assertEquals(roomId, ctx.bookingResponse.roomId)
//        assertEquals(workspaceId, ctx.bookingResponse.workspaceId)
//        assertEquals(startTime, ctx.bookingResponse.startTime)
//        assertEquals(endTime, ctx.bookingResponse.endTime)
        assertEquals(OfficeBookingStatus.ACTIVE, ctx.bookingResponse.status)
    }
}
