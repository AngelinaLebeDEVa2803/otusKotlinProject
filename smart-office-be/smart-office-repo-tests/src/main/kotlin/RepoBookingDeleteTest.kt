package ru.otus.otuskotlin.smartoffice.repo.tests

import ru.otus.otuskotlin.smartoffice.common.models.OfficeBooking
import ru.otus.otuskotlin.smartoffice.common.models.OfficeBookingId
import ru.otus.otuskotlin.smartoffice.common.repo.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNotNull

abstract class RepoBookingDeleteTest {
    abstract val repo: IRepoBooking
    protected open val deleteSucc = initObjects[0]
    protected open val deleteConc = initObjects[1]
    protected open val notFoundId = OfficeBookingId("booking-repo-delete-notFound")

    @Test
    fun deleteSuccess() = runRepoTest {
        val lockOld = deleteSucc.lock
        val result = repo.deleteBooking(DbBookingIdRequest(deleteSucc.id, lock = lockOld))
        assertIs<DbBookingResponseOk>(result)
        assertEquals(deleteSucc.userId, result.data.userId)
        assertEquals(deleteSucc.floorId, result.data.floorId)
        assertEquals(deleteSucc.roomId, result.data.roomId)
        assertEquals(deleteSucc.workspaceId, result.data.workspaceId)
        assertEquals(deleteSucc.status, result.data.status)
        assertEquals(deleteSucc.startTime, result.data.startTime)
        assertEquals(deleteSucc.endTime, result.data.endTime)

    }

    @Test
    fun deleteNotFound() = runRepoTest {
        val result = repo.readBooking(DbBookingIdRequest(notFoundId, lock = lockOld))

        assertIs<DbBookingResponseErr>(result)
        val error = result.errors.find { it.code == "repo-not-found" }
        assertNotNull(error)
    }

    @Test
    fun deleteConcurrency() = runRepoTest {
        val result = repo.deleteBooking(DbBookingIdRequest(deleteConc.id, lock = lockBad))

        assertIs<DbBookingResponseErrWithData>(result)
        val error = result.errors.find { it.code == "repo-concurrency" }
        assertNotNull(error)
    }

    companion object : BaseInitBookings("delete") {
        override val initObjects: List<OfficeBooking> = listOf(
            createInitTestModel("delete"),
            createInitTestModel("deleteLock"),
        )
    }
}
