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

fun ICorChainDsl<OfficeContext>.repoRead(title: String) = worker {
    this.title = title
    description = "Чтение объявления из БД"
    on { state == OfficeState.RUNNING }
    handle {
        val request = DbBookingIdRequest(bookingValidated)
        when(val result = bookingRepo.readBooking(request)) {
            is DbBookingResponseOk -> bookingRepoRead = result.data
            is DbBookingResponseErr -> fail(result.errors)
            is DbBookingResponseErrWithData -> {
                fail(result.errors)
                bookingRepoRead = result.data
            }
        }
    }
}
