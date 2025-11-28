package ru.otus.otuskotlin.smartoffice.repo.tests

import kotlinx.datetime.Instant
import ru.otus.otuskotlin.smartoffice.common.models.*
import ru.otus.otuskotlin.smartoffice.common.repo.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs


abstract class RepoBookingUpdateTest {
    abstract val repo: IRepoBooking
    protected open val updateSucc = initObjects[0]
    protected open val updateConc = initObjects[1]
    protected val updateIdNotFound = OfficeBookingId("booking-repo-update-not-found")
    protected val lockBad = OfficeBookingLock("20000000-0000-0000-0000-000000000009")
    protected val lockNew = OfficeBookingLock("20000000-0000-0000-0000-000000000002")

    private val reqUpdateSucc by lazy {
        OfficeBooking(
            id = updateSucc.id,
            userId = OfficeUserId("999"),
            floorId =  OfficeFloorId("floor_1"),
            roomId = OfficeRoomId("room_1"),
            workspaceId = OfficeWorkspaceId("23456"),
            status = OfficeBookingStatus.ACTIVE,
            startTime = Instant.parse("2026-10-10T10:00:00Z"),
            endTime = Instant.parse("2026-10-10T15:00:00Z"),
            lock = initObjects.first().lock,
        )
    }
    private val reqUpdateNotFound = OfficeBooking(
        id = updateIdNotFound,
        userId = OfficeUserId("999"),
        floorId =  OfficeFloorId("floor_1"),
        roomId = OfficeRoomId("room_1"),
        workspaceId = OfficeWorkspaceId("23456"),
        status = OfficeBookingStatus.ACTIVE,
        startTime = Instant.parse("2026-10-10T10:00:00Z"),
        endTime = Instant.parse("2026-10-10T15:00:00Z"),
        lock = initObjects.first().lock,
    )
    private val reqUpdateConc by lazy {
        OfficeBooking(
            id = updateConc.id,
            userId = OfficeUserId("999"),
            floorId =  OfficeFloorId("floor_1"),
            roomId = OfficeRoomId("room_1"),
            workspaceId = OfficeWorkspaceId("23456"),
            status = OfficeBookingStatus.ACTIVE,
            startTime = Instant.parse("2026-10-10T10:00:00Z"),
            endTime = Instant.parse("2026-10-10T15:00:00Z"),
            lock = lockBad,
        )
    }

    @Test
    fun updateSuccess() = runRepoTest {
        val result = repo.updateBooking(DbBookingRequest(reqUpdateSucc))
        assertIs<DbBookingResponseOk>(result)
        assertEquals(reqUpdateSucc.id, result.data.id)
        assertEquals(reqUpdateSucc.userId, result.data.userId)
        assertEquals(reqUpdateSucc.floorId, result.data.floorId)
        assertEquals(reqUpdateSucc.roomId, result.data.roomId)
        assertEquals(reqUpdateSucc.workspaceId, result.data.workspaceId)
        assertEquals(reqUpdateSucc.status, result.data.status)
        assertEquals(reqUpdateSucc.startTime, result.data.startTime)
        assertEquals(reqUpdateSucc.endTime, result.data.endTime)
        assertEquals(lockNew, result.data.lock)
    }

    @Test
    fun updateNotFound() = runRepoTest {
        val result = repo.updateBooking(DbBookingRequest(reqUpdateNotFound))
        assertIs<DbBookingResponseErr>(result)
        val error = result.errors.find { it.code == "repo-not-found" }
        assertEquals("id", error?.field)
    }

    @Test
    fun updateConcurrencyError() = runRepoTest {
        val result = repo.updateBooking(DbBookingRequest(reqUpdateConc))
        assertIs<DbBookingResponseErrWithData>(result)
        val error = result.errors.find { it.code == "repo-concurrency" }
        assertEquals("lock", error?.field)
        assertEquals(updateConc, result.data)
    }

    companion object : BaseInitBookings("update") {
        override val initObjects: List<OfficeBooking> = listOf(
            createInitTestModel("update"),
            createInitTestModel("updateConc"),
        )
    }
}
