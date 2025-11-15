package ru.otus.otuskotlin.smartoffice.biz.validation

import ru.otus.otuskotlin.smartoffice.common.OfficeContext
import ru.otus.otuskotlin.smartoffice.common.helpers.errorValidation
import ru.otus.otuskotlin.smartoffice.common.helpers.fail
import ru.otus.otuskotlin.smartoffice.common.models.OfficeState
import ru.otus.otuskotlin.smartoffice.common.models.OfficeWorkspaceId
import ru.otus.otuskotlin.smartoffice.cor.ICorChainDsl
import ru.otus.otuskotlin.smartoffice.cor.chain
import ru.otus.otuskotlin.smartoffice.cor.worker


fun ICorChainDsl<OfficeContext>.validateWorkspaceId(title: String) = chain {
    this.title = title
    this.description = """
        Валидация идентификатора этажа workspaceId. Должен быть не пустым и соответствовать регулярке
    """.trimIndent()
    on { state == OfficeState.RUNNING }
    worker("Обрезка пустых символов") { bookingValidating.workspaceId = OfficeWorkspaceId(bookingValidating.workspaceId.asString().trim()) }
    worker {
        this.title = "Проверка на непустой workspaceId"
        this.description = this.title
        on { state == OfficeState.RUNNING && bookingValidating.workspaceId.asString().isEmpty()  }
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
    worker {
        this.title = "Проверка формата floorId"
        this.description = this.title

        val regExp = Regex("^[0-9a-zA-Z#:-]+\$")
        on { state == OfficeState.RUNNING && bookingValidating.workspaceId != OfficeWorkspaceId.NONE && !bookingValidating.workspaceId.asString().matches(regExp)}
        handle {
            val encodedId = bookingValidating.workspaceId.asString()
                .replace("<", "&lt;")
                .replace(">", "&gt;")
            fail(
                errorValidation(
                    field = "workspaceId",
                    violationCode = "badFormat",
                    description = "value $encodedId must contain only letters and numbers"
                )
            )
        }
    }
}