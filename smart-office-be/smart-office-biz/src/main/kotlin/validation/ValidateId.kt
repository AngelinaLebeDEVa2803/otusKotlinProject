package ru.otus.otuskotlin.smartoffice.biz.validation

import ru.otus.otuskotlin.smartoffice.common.OfficeContext
import ru.otus.otuskotlin.smartoffice.common.helpers.errorValidation
import ru.otus.otuskotlin.smartoffice.common.helpers.fail
import ru.otus.otuskotlin.smartoffice.common.models.OfficeState
import ru.otus.otuskotlin.smartoffice.common.models.OfficeBookingId
import ru.otus.otuskotlin.smartoffice.cor.ICorChainDsl
import ru.otus.otuskotlin.smartoffice.cor.chain
import ru.otus.otuskotlin.smartoffice.cor.worker


fun ICorChainDsl<OfficeContext>.validateId(title: String) = chain {
    this.title = title
    this.description = """
        Валидация идентификатора бронирования id. Должен быть не пустым и соответствовать регулярке
    """.trimIndent()
    on { state == OfficeState.RUNNING }
    worker("Обрезка пустых символов") { bookingValidating.id = OfficeBookingId(bookingValidating.id.asString().trim()) }
    worker {
        this.title = "Проверка на непустой id"
        this.description = this.title
        on { state == OfficeState.RUNNING && bookingValidating.id.asString().isEmpty() }
        handle {
            fail(
                errorValidation(
                    field = "id",
                    violationCode = "empty",
                    description = "field must not be empty"
                )
            )
        }
    }
    worker {
        this.title = "Проверка формата id"
        this.description = this.title

        val regExp = Regex("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$")  // uuid
        on { state == OfficeState.RUNNING && bookingValidating.id != OfficeBookingId.NONE && !bookingValidating.id.asString().matches(regExp)}
        handle {
            val encodedId = bookingValidating.id.asString()
                .replace("<", "&lt;")
                .replace(">", "&gt;")
            fail(
                errorValidation(
                    field = "id",
                    violationCode = "badFormat",
                    description = "value $encodedId must be in uuid"
                )
            )
        }
    }
}