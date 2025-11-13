package ru.otus.otuskotlin.smartoffice.biz.validation

import ru.otus.otuskotlin.smartoffice.cor.ICorChainDsl
import ru.otus.otuskotlin.smartoffice.cor.worker
import ru.otus.otuskotlin.smartoffice.common.helpers.errorValidation
import ru.otus.otuskotlin.smartoffice.common.OfficeContext
import ru.otus.otuskotlin.smartoffice.common.helpers.fail

fun ICorChainDsl<OfficeContext>.validateUserIdNotEmpty(title: String) = worker {
    this.title = title
    on { bookingValidating.userId.asString().isEmpty() }
    handle {
        fail(
            errorValidation(
                field = "userId",
                violationCode = "empty",
                description = "field must not be empty"
            )
        )
    }
}

fun ICorChainDsl<OfficeContext>.validateUserIdNotEmptyFilter(title: String) = worker {
    this.title = title
    on { bookingFilterValidating.userId.asString().isEmpty() }
    handle {
        fail(
            errorValidation(
                field = "userId",
                violationCode = "empty",
                description = "field must not be empty"
            )
        )
    }
}