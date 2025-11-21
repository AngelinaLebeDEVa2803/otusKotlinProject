package ru.otus.otuskotlin.smartoffice.repo.tests

import kotlinx.datetime.Instant
import ru.otus.otuskotlin.smartoffice.common.models.OfficeBooking
import ru.otus.otuskotlin.smartoffice.common.models.*
import ru.otus.otuskotlin.smartoffice.common.repo.DbBookingFilterRequest
import ru.otus.otuskotlin.smartoffice.common.repo.DbBookingsResponseOk
import ru.otus.otuskotlin.smartoffice.common.repo.IRepoBooking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs


abstract class RepoBookingAllTest {
    abstract val repo: IRepoBooking

    protected open val initializedObjects: List<OfficeBooking> = initObjects

    @Test
    fun allUser() = runRepoTest {
        val result = repo.allBooking(DbBookingFilterRequest(userId = allUserId))
        assertIs<DbBookingsResponseOk>(result)
        val expected = listOf(initializedObjects[1], initializedObjects[3]).sortedBy { it.id.asString() }
        assertEquals(expected, result.data.sortedBy { it.id.asString() })
    }

    @Test
    fun allStatus() = runRepoTest {
        val result = repo.allBooking(DbBookingFilterRequest(status = statusCancelled))
        assertIs<DbBookingsResponseOk>(result)
        val expected = listOf(initializedObjects[2], initializedObjects[4]).sortedBy { it.id.asString() }
        assertEquals(expected, result.data.sortedBy { it.id.asString() })
    }

    @Test
    fun allTimes() = runRepoTest {
        val result = repo.allBooking(DbBookingFilterRequest(startTime = startTest, endTime = endTest))
        assertIs<DbBookingsResponseOk>(result)
        val expected = listOf(initializedObjects[5], initializedObjects[6]).sortedBy { it.id.asString() }
        assertEquals(expected, result.data.sortedBy { it.id.asString() })
    }

    companion object: BaseInitBookings("all") {

        val allUserId = OfficeUserId("user4test")
        val statusCancelled = OfficeBookingStatus.CANCELLED
        val startTest = Instant.parse("2025-01-01T09:00:00Z")
        val endTest = Instant.parse("2025-12-31T09:00:00Z")
        override val initObjects: List<OfficeBooking> = listOf(
            createInitTestModel("booking1"),
            createInitTestModel("booking2", userId = allUserId),
            createInitTestModel("booking3", status = statusCancelled),
            createInitTestModel("booking4", userId = allUserId),
            createInitTestModel("booking5", status = statusCancelled),
            createInitTestModel("booking6", startTime = Instant.parse("2025-03-01T09:00:00Z"),
                endTime = Instant.parse("2025-03-01T19:00:00Z")),
            createInitTestModel("booking7", startTime = Instant.parse("2025-10-01T09:00:00Z"),
                endTime = Instant.parse("2025-10-01T19:00:00Z")),
        )
    }
}
