package ru.otus.otuskotlin.smartoffice.biz.validation

import ru.otus.otuskotlin.smartoffice.cor.ICorChainDsl
import ru.otus.otuskotlin.smartoffice.cor.worker
import ru.otus.otuskotlin.smartoffice.common.helpers.errorValidation
import ru.otus.otuskotlin.smartoffice.common.OfficeContext
import ru.otus.otuskotlin.smartoffice.common.helpers.fail

fun ICorChainDsl<OfficeContext>.validateRoomIdNotEmpty(title: String) = worker {
    this.title = title
    on { bookingValidating.roomId.asString().isEmpty() }
    handle {
        fail(
            errorValidation(
                field = "roomId",
                violationCode = "empty",
                description = "field must not be empty"
            )
        )
    }
}
