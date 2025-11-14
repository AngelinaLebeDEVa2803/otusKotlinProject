package ru.otus.otuskotlin.smartoffice.biz.validation

import ru.otus.otuskotlin.smartoffice.common.OfficeContext
import ru.otus.otuskotlin.smartoffice.common.helpers.errorValidation
import ru.otus.otuskotlin.smartoffice.common.helpers.fail
import ru.otus.otuskotlin.smartoffice.common.models.OfficeState
import ru.otus.otuskotlin.smartoffice.common.models.OfficeRoomId
import ru.otus.otuskotlin.smartoffice.cor.ICorChainDsl
import ru.otus.otuskotlin.smartoffice.cor.chain
import ru.otus.otuskotlin.smartoffice.cor.worker


fun ICorChainDsl<OfficeContext>.validateRoomId(title: String) = chain {
    this.title = title
    this.description = """
        Валидация идентификатора этажа roomId. Должен быть не пустым и соответствовать регулярке
    """.trimIndent()
    on { state == OfficeState.RUNNING }
    worker("Обрезка пустых символов") { bookingValidating.roomId = OfficeRoomId(bookingValidating.roomId.asString().trim()) }
    worker {
        this.title = "Проверка на непустой roomId"
        this.description = this.title
        on { state == OfficeState.RUNNING && bookingValidating.roomId.asString().isEmpty()}
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
    worker {
        this.title = "Проверка формата roomId"
        this.description = this.title

        val regExp = Regex("^room_[0-9]{1,4}$")
        on { state == OfficeState.RUNNING && bookingValidating.roomId != OfficeRoomId.NONE && !bookingValidating.roomId.asString().matches(regExp)}
        handle {
            val encodedId = bookingValidating.roomId.asString()
                .replace("<", "&lt;")
                .replace(">", "&gt;")
            fail(
                errorValidation(
                    field = "roomId",
                    violationCode = "badFormat",
                    description = "value $encodedId must contain only letters and numbers"
                )
            )
        }
    }
}