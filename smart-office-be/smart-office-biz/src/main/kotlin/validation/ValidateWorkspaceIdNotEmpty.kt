package ru.otus.otuskotlin.smartoffice.biz.validation

import ru.otus.otuskotlin.smartoffice.cor.ICorChainDsl
import ru.otus.otuskotlin.smartoffice.cor.worker
import ru.otus.otuskotlin.smartoffice.common.helpers.errorValidation
import ru.otus.otuskotlin.smartoffice.common.OfficeContext
import ru.otus.otuskotlin.smartoffice.common.helpers.fail

fun ICorChainDsl<OfficeContext>.validateWorkspaceIdNotEmpty(title: String) = worker {
    this.title = title
    on { bookingValidating.workspaceId.asString().isEmpty() }
    handle {
        fail(
            errorValidation(
                field = "workspaceId",
                violationCode = "empty",
                description = "field must not be empty"
            )
        )
    }
}
