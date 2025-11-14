package ru.otus.otuskotlin.smartoffice.biz.validation

import kotlinx.datetime.Instant
import ru.otus.otuskotlin.smartoffice.common.NONE
import ru.otus.otuskotlin.smartoffice.common.OfficeContext
import ru.otus.otuskotlin.smartoffice.common.helpers.errorValidation
import ru.otus.otuskotlin.smartoffice.common.helpers.fail
import ru.otus.otuskotlin.smartoffice.cor.ICorChainDsl
import ru.otus.otuskotlin.smartoffice.cor.worker

fun ICorChainDsl<OfficeContext>.validateEndTimeNotNone(title: String) = worker {
    this.title = title

    on { bookingValidating.endTime == Instant.NONE }
    handle {
        fail(
            errorValidation(
                field = "endTime",
                violationCode = "badValue",
                description = "endTime is not defined"
            )
        )
    }
}