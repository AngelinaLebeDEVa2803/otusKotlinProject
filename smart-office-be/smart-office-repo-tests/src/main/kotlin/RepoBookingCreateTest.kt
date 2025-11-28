package ru.otus.otuskotlin.smartoffice.repo.tests

import kotlinx.datetime.Instant
import ru.otus.otuskotlin.smartoffice.common.models.*
import ru.otus.otuskotlin.smartoffice.common.repo.DbBookingRequest
import ru.otus.otuskotlin.smartoffice.common.repo.DbBookingResponseOk
import ru.otus.otuskotlin.smartoffice.repo.common.IRepoBookingInitializable
import kotlin.test.*


abstract class RepoBookingCreateTest {
    abstract val repo: IRepoBookingInitializable
    protected open val uuidNew = OfficeBookingId("10000000-0000-0000-0000-000000000001")

    private val createObj = OfficeBooking(

        userId = OfficeUserId("999"),
        floorId =  OfficeFloorId("floor_1"),
        roomId = OfficeRoomId("room_1"),
        workspaceId = OfficeWorkspaceId("23456"),
        status = OfficeBookingStatus.ACTIVE,
        startTime = Instant.parse("2026-10-10T10:00:00Z"),
        endTime = Instant.parse("2026-10-10T15:00:00Z"),
    )

    @Test
    fun createSuccess() = runRepoTest {
        val result = repo.createBooking(DbBookingRequest(createObj))
        val expected = createObj
        assertIs<DbBookingResponseOk>(result)
        assertEquals(uuidNew, result.data.id)
        assertEquals(expected.userId, result.data.userId)
        assertEquals(expected.floorId, result.data.floorId)
        assertEquals(expected.roomId, result.data.roomId)
        assertEquals(expected.workspaceId, result.data.workspaceId)
        assertEquals(expected.status, result.data.status)
        assertEquals(expected.startTime, result.data.startTime)
        assertEquals(expected.endTime, result.data.endTime)
        assertNotEquals(OfficeBookingId.NONE, result.data.id)
    }

    companion object : BaseInitBookings("create") {
        override val initObjects: List<OfficeBooking> = emptyList()
    }
}
