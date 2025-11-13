package ru.otus.otuskotlin.smartoffice.biz.stub

import kotlinx.datetime.Instant
import kotlinx.coroutines.test.runTest
import ru.otus.otuskotlin.smartoffice.biz.OfficeBookingProcessor
import ru.otus.otuskotlin.smartoffice.common.OfficeContext
import ru.otus.otuskotlin.smartoffice.common.models.*
import ru.otus.otuskotlin.smartoffice.common.stubs.OfficeStubs
import ru.otus.otuskotlin.smartoffice.stubs.OfficeBookingStub
import kotlin.test.Test
import kotlin.test.assertEquals

class BookingCreateStubTest {

    private val processor = OfficeBookingProcessor()
    val bookingId = OfficeBookingId("001")
    val userId = OfficeUserId("123")
    val floorId = OfficeFloorId("floor_6")
    val roomId = OfficeRoomId("room_404")
    val workspaceId = OfficeWorkspaceId("009")
    val startTime = Instant.parse("2025-03-30T09:00:00Z")
    val endTime = Instant.parse("2025-03-30T18:00:00Z")
    val status = OfficeBookingStatus.ACTIVE


    @Test
    fun create() = runTest {

        val ctx = OfficeContext(
            command = OfficeCommand.CREATE,
            state = OfficeState.NONE,
            workMode = OfficeWorkMode.STUB,
            stubCase = OfficeStubs.SUCCESS,
            bookingRequest = OfficeBooking(
                id = bookingId,
                userId = userId,
                floorId = floorId,
                roomId = roomId,
                workspaceId = workspaceId,
                startTime = startTime,
                endTime = endTime,
                status = status,
            ),
        )
        processor.exec(ctx)
        assertEquals(OfficeBookingStub.get().id, ctx.bookingResponse.id)
        assertEquals(userId, ctx.bookingResponse.userId)
        assertEquals(floorId, ctx.bookingResponse.floorId)
        assertEquals(roomId, ctx.bookingResponse.roomId)
        assertEquals(workspaceId, ctx.bookingResponse.workspaceId)
        assertEquals(startTime, ctx.bookingResponse.startTime)
        assertEquals(endTime, ctx.bookingResponse.endTime)
        assertEquals(status, ctx.bookingResponse.status)
    }

//    @Test
//    fun badTitle() = runTest {
//        val ctx = MkplContext(
//            command = MkplCommand.CREATE,
//            state = MkplState.NONE,
//            workMode = MkplWorkMode.STUB,
//            stubCase = MkplStubs.BAD_TITLE,
//            adRequest = MkplAd(
//                id = id,
//                title = "",
//                description = description,
//                adType = dealSide,
//                visibility = visibility,
//            ),
//        )
//        processor.exec(ctx)
//        assertEquals(MkplAd(), ctx.adResponse)
//        assertEquals("title", ctx.errors.firstOrNull()?.field)
//        assertEquals("validation", ctx.errors.firstOrNull()?.group)
//    }
//    @Test
//    fun badDescription() = runTest {
//        val ctx = MkplContext(
//            command = MkplCommand.CREATE,
//            state = MkplState.NONE,
//            workMode = MkplWorkMode.STUB,
//            stubCase = MkplStubs.BAD_DESCRIPTION,
//            adRequest = MkplAd(
//                id = id,
//                title = title,
//                description = "",
//                adType = dealSide,
//                visibility = visibility,
//            ),
//        )
//        processor.exec(ctx)
//        assertEquals(MkplAd(), ctx.adResponse)
//        assertEquals("description", ctx.errors.firstOrNull()?.field)
//        assertEquals("validation", ctx.errors.firstOrNull()?.group)
//    }

//    @Test
//    fun databaseError() = runTest {
//        val ctx = MkplContext(
//            command = MkplCommand.CREATE,
//            state = MkplState.NONE,
//            workMode = MkplWorkMode.STUB,
//            stubCase = MkplStubs.DB_ERROR,
//            adRequest = MkplAd(
//                id = id,
//            ),
//        )
//        processor.exec(ctx)
//        assertEquals(MkplAd(), ctx.adResponse)
//        assertEquals("internal", ctx.errors.firstOrNull()?.group)
//    }

//    @Test
//    fun badNoCase() = runTest {
//        val ctx = MkplContext(
//            command = MkplCommand.CREATE,
//            state = MkplState.NONE,
//            workMode = MkplWorkMode.STUB,
//            stubCase = MkplStubs.BAD_ID,
//            adRequest = MkplAd(
//                id = id,
//                title = title,
//                description = description,
//                adType = dealSide,
//                visibility = visibility,
//            ),
//        )
//        processor.exec(ctx)
//        assertEquals(MkplAd(), ctx.adResponse)
//        assertEquals("stub", ctx.errors.firstOrNull()?.field)
//        assertEquals("validation", ctx.errors.firstOrNull()?.group)
//    }
}
