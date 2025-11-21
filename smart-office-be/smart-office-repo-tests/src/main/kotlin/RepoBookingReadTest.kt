package ru.otus.otuskotlin.smartoffice.repo.tests

import ru.otus.otuskotlin.smartoffice.common.models.OfficeBooking
import ru.otus.otuskotlin.smartoffice.common.models.OfficeBookingId
import ru.otus.otuskotlin.smartoffice.common.repo.DbBookingIdRequest
import ru.otus.otuskotlin.smartoffice.common.repo.DbBookingResponseErr
import ru.otus.otuskotlin.smartoffice.common.repo.DbBookingResponseOk
import ru.otus.otuskotlin.smartoffice.common.repo.IRepoBooking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs


abstract class RepoBookingReadTest {
    abstract val repo: IRepoBooking
    protected open val readSucc = initObjects[0]

    @Test
    fun readSuccess() = runRepoTest {
        val result = repo.readBooking(DbBookingIdRequest(readSucc.id))

        assertIs<DbBookingResponseOk>(result)
        assertEquals(readSucc, result.data)
    }

    @Test
    fun readNotFound() = runRepoTest {
        val result = repo.readBooking(DbBookingIdRequest(notFoundId))

        assertIs<DbBookingResponseErr>(result)
        val error = result.errors.find { it.code == "repo-not-found" }
        assertEquals("id", error?.field)
    }

    companion object : BaseInitBookings("delete") {
        override val initObjects: List<OfficeBooking> = listOf(
            createInitTestModel("read")
        )

        val notFoundId = OfficeBookingId("booking-repo-read-notFound")

    }
}
