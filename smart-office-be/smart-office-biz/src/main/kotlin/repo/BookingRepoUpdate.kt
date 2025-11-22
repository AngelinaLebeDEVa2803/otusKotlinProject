package ru.otus.otuskotlin.smartoffice.biz.repo

import ru.otus.otuskotlin.smartoffice.common.OfficeContext
import ru.otus.otuskotlin.smartoffice.common.helpers.fail
import ru.otus.otuskotlin.smartoffice.common.models.OfficeState
import ru.otus.otuskotlin.smartoffice.common.repo.DbBookingRequest
import ru.otus.otuskotlin.smartoffice.common.repo.DbBookingResponseErr
import ru.otus.otuskotlin.smartoffice.common.repo.DbBookingResponseErrWithData
import ru.otus.otuskotlin.smartoffice.common.repo.DbBookingResponseOk
import ru.otus.otuskotlin.smartoffice.cor.ICorChainDsl
import ru.otus.otuskotlin.smartoffice.cor.worker

fun ICorChainDsl<OfficeContext>.repoUpdate(title: String) = worker {
    this.title = title
    on { state == OfficeState.RUNNING }
    handle {
        val request = DbBookingRequest(bookingRepoPrepare)
        when(val result = bookingRepo.updateBooking(request)) {
            is DbBookingResponseOk -> bookingRepoDone = result.data
            is DbBookingResponseErr -> fail(result.errors)
            is DbBookingResponseErrWithData -> {
                fail(result.errors)
                bookingRepoDone = result.data
            }
        }
    }
}
