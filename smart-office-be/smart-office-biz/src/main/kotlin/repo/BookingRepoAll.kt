package ru.otus.otuskotlin.smartoffice.biz.repo

import ru.otus.otuskotlin.smartoffice.common.OfficeContext
import ru.otus.otuskotlin.smartoffice.common.helpers.fail
import ru.otus.otuskotlin.smartoffice.common.models.OfficeState
import ru.otus.otuskotlin.smartoffice.common.repo.DbBookingFilterRequest
import ru.otus.otuskotlin.smartoffice.common.repo.DbBookingsResponseErr
import ru.otus.otuskotlin.smartoffice.common.repo.DbBookingsResponseOk
import ru.otus.otuskotlin.smartoffice.cor.ICorChainDsl
import ru.otus.otuskotlin.smartoffice.cor.worker

fun ICorChainDsl<OfficeContext>.repoAll(title: String) = worker {
    this.title = title
    description = "Поиск бронирований в БД по фильтру"
    on { state == OfficeState.RUNNING }
    handle {
        val request = DbBookingFilterRequest(
            userId = bookingFilterValidated.userId,
            startTime = bookingFilterValidated.startTime,
            endTime = bookingFilterValidated.endTime,
            status = bookingFilterValidated.status
        )
        when(val result = bookingRepo.allBooking(request)) {
            is DbBookingsResponseOk -> bookingsRepoDone = result.data.toMutableList()
            is DbBookingsResponseErr -> fail(result.errors)
        }
    }
}
