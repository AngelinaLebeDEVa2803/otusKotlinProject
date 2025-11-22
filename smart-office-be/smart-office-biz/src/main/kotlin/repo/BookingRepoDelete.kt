package ru.otus.otuskotlin.smartoffice.biz.repo

import ru.otus.otuskotlin.smartoffice.common.OfficeContext
import ru.otus.otuskotlin.smartoffice.common.helpers.fail
import ru.otus.otuskotlin.smartoffice.common.models.OfficeState
import ru.otus.otuskotlin.smartoffice.common.repo.DbBookingIdRequest
import ru.otus.otuskotlin.smartoffice.common.repo.DbBookingResponseErr
import ru.otus.otuskotlin.smartoffice.common.repo.DbBookingResponseErrWithData
import ru.otus.otuskotlin.smartoffice.common.repo.DbBookingResponseOk
import ru.otus.otuskotlin.smartoffice.cor.ICorChainDsl
import ru.otus.otuskotlin.smartoffice.cor.worker

fun ICorChainDsl<OfficeContext>.repoDelete(title: String) = worker {
    this.title = title
    description = "Удаление брони из БД по ID"
    on { state == OfficeState.RUNNING }
    handle {
        val request = DbBookingIdRequest(bookingRepoPrepare)
        when(val result = bookingRepo.deleteBooking(request)) {
            is DbBookingResponseOk -> bookingRepoDone = result.data
            is DbBookingResponseErr -> {
                fail(result.errors)
                bookingRepoDone = bookingRepoRead
            }
            is DbBookingResponseErrWithData -> {
                fail(result.errors)
                bookingRepoDone = result.data
            }
        }
    }
}
