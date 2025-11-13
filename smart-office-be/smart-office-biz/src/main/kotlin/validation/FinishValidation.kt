package ru.otus.otuskotlin.smartoffice.biz.validation

import ru.otus.otuskotlin.smartoffice.common.OfficeContext
import ru.otus.otuskotlin.smartoffice.common.models.OfficeState
import ru.otus.otuskotlin.smartoffice.cor.ICorChainDsl
import ru.otus.otuskotlin.smartoffice.cor.worker

fun ICorChainDsl<OfficeContext>.finishBookingValidation(title: String) = worker {
    this.title = title
    on { state == OfficeState.RUNNING }
    handle {
        bookingValidated = bookingValidating
    }
}

fun ICorChainDsl<OfficeContext>.finishBookingFilterValidation(title: String) = worker {
    this.title = title
    on { state == OfficeState.RUNNING }
    handle {
        bookingFilterValidated = bookingFilterValidating
    }
}
