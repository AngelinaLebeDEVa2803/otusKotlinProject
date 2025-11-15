package ru.otus.otuskotlin.smartoffice.biz.validation

import ru.otus.otuskotlin.smartoffice.common.OfficeContext
import ru.otus.otuskotlin.smartoffice.common.helpers.errorValidation
import ru.otus.otuskotlin.smartoffice.common.helpers.fail
import ru.otus.otuskotlin.smartoffice.common.models.OfficeBookingStatus
import ru.otus.otuskotlin.smartoffice.cor.ICorChainDsl
import ru.otus.otuskotlin.smartoffice.cor.worker

fun ICorChainDsl<OfficeContext>.validateStatusNotEmpty(title: String) = worker {
    this.title = title

    on { bookingValidating.status == OfficeBookingStatus.NONE }
    handle {
        fail(
            errorValidation(
                field = "status",
                violationCode = "badValue",
                description = "status is not defined"
            )
        )
    }
}