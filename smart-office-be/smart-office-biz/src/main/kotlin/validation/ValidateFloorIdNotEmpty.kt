package ru.otus.otuskotlin.smartoffice.biz.validation

import ru.otus.otuskotlin.smartoffice.cor.ICorChainDsl
import ru.otus.otuskotlin.smartoffice.cor.worker
import ru.otus.otuskotlin.smartoffice.common.helpers.errorValidation
import ru.otus.otuskotlin.smartoffice.common.OfficeContext
import ru.otus.otuskotlin.smartoffice.common.helpers.fail

fun ICorChainDsl<OfficeContext>.validateFloorIdNotEmpty(title: String) = worker {
    this.title = title
    on { bookingValidating.floorId.asString().isEmpty() }
    handle {
        fail(
            errorValidation(
                field = "floorId",
                violationCode = "empty",
                description = "field must not be empty"
            )
        )
    }
}
