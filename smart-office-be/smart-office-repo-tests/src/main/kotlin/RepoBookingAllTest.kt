package ru.otus.otuskotlin.smartoffice.repo.tests

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
        val result = repo.allBooking(DbBookingFilterRequest(status = OfficeBookingStatus.ACTIVE))
        assertIs<DbBookingsResponseOk>(result)
        val expected = listOf(initializedObjects[2], initializedObjects[4]).sortedBy { it.id.asString() }
        assertEquals(expected, result.data.sortedBy { it.id.asString() })
    }

    companion object: BaseInitBookings("all") {

        val allUserId = OfficeUserId("999")
        override val initObjects: List<OfficeBooking> = listOf(
            createInitTestModel("booking1"),
            createInitTestModel("booking2", userId = allUserId),
            createInitTestModel("booking5", status = OfficeBookingStatus.ACTIVE),
        )
    }
}
