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

class BizRepoUpdateTest {

    private val bookingId = OfficeBookingId("b4285985-1d55-4f75-ad25-92f592e0d965")
    private val userId = OfficeUserId("321")
    private val floorId = OfficeFloorId("floor_666")
    private val roomId = OfficeRoomId("room_9")
    private val workspaceId = OfficeWorkspaceId("040")

    private val startT = Instant.parse("2028-02-19T09:00:00Z")
    private val endT = Instant.parse("2028-02-19T19:00:00Z")
    private val command = OfficeCommand.UPDATE
    private val initBooking = OfficeBooking(
        id = bookingId,
        userId = userId,
        floorId = floorId,
        roomId = roomId,
        workspaceId = workspaceId,
        startTime = startT,
        endTime = endT,
        status = OfficeBookingStatus.ACTIVE,
        lock = OfficeBookingLock("123"),
    )
    private val repo = BookingRepositoryMock(
        invokeReadBooking = {
            DbBookingResponseOk(
                data = initBooking,
            )
        },
        invokeUpdateBooking = {
            DbBookingResponseOk(
                data = OfficeBooking(
                    id = bookingId,
                    userId = userId,
                    floorId = floorId,
                    roomId = roomId,
                    workspaceId = workspaceId,
                    startTime = startT,
                    endTime = endT,
                    status = OfficeBookingStatus.CANCELLED,
                    lock = OfficeBookingLock("567"),
                )
            )
        }
    )
    private val settings = OfficeCorSettings(repoTest = repo)
    private val processor = OfficeBookingProcessor(settings)

    @Test
    fun repoUpdateSuccessTest() = runTest {
        val bookingToUpdate = OfficeBooking(
            id = bookingId,
            userId = userId,
            floorId = floorId,
            roomId = roomId,
            workspaceId = workspaceId,
            startTime = startT,
            endTime = endT,
            status = OfficeBookingStatus.CANCELLED,
            lock = OfficeBookingLock("123"),
        )
        val ctx = OfficeContext(
            command = command,
            state = OfficeState.NONE,
            workMode = OfficeWorkMode.TEST,
            bookingRequest = bookingToUpdate,
        )
        processor.exec(ctx)
        println(ctx.bookingValidated)
        println(ctx.bookingRepoRead)
        println(ctx.bookingRepoPrepare)
        println(ctx.bookingRepoDone)
        assertEquals(OfficeState.FINISHING, ctx.state)
        assertEquals(bookingToUpdate.id, ctx.bookingResponse.id)
        assertEquals(bookingToUpdate.userId, ctx.bookingResponse.userId)
        assertEquals(bookingToUpdate.floorId, ctx.bookingResponse.floorId)
        assertEquals(bookingToUpdate.roomId, ctx.bookingResponse.roomId)
        assertEquals(bookingToUpdate.workspaceId, ctx.bookingResponse.workspaceId)
        assertEquals(bookingToUpdate.startTime, ctx.bookingResponse.startTime)
        assertEquals(bookingToUpdate.endTime, ctx.bookingResponse.endTime)
        assertEquals(bookingToUpdate.status, ctx.bookingResponse.status)
    }

    @Test
    fun repoUpdateNotFoundTest() = repoNotFoundTest(command)
}
