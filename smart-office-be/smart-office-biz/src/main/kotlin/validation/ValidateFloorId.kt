package ru.otus.otuskotlin.smartoffice.biz.validation

import ru.otus.otuskotlin.smartoffice.common.OfficeContext
import ru.otus.otuskotlin.smartoffice.common.helpers.errorValidation
import ru.otus.otuskotlin.smartoffice.common.helpers.fail
import ru.otus.otuskotlin.smartoffice.common.models.OfficeState
import ru.otus.otuskotlin.smartoffice.common.models.OfficeFloorId
import ru.otus.otuskotlin.smartoffice.cor.ICorChainDsl
import ru.otus.otuskotlin.smartoffice.cor.chain
import ru.otus.otuskotlin.smartoffice.cor.worker


fun ICorChainDsl<OfficeContext>.validateFloorId(title: String) = chain {
    this.title = title
    this.description = """
        Валидация идентификатора этажа floorId. Должен быть не пустым и соответствовать регулярке
    """.trimIndent()
    on { state == OfficeState.RUNNING }
    worker("Обрезка пустых символов") { bookingValidating.floorId = OfficeFloorId(bookingValidating.floorId.asString().trim()) }
    worker {
        this.title = "Проверка на непустой floorId"
        this.description = this.title
        on { state == OfficeState.RUNNING && bookingValidating.floorId.asString().isEmpty() }
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
    worker {
        this.title = "Проверка формата floorId"
        this.description = this.title

        val regExp = Regex("^floor_[0-9]{1,3}$")
        on { state == OfficeState.RUNNING && bookingValidating.floorId != OfficeFloorId.NONE && !bookingValidating.floorId.asString().matches(regExp) }
        handle {
            val encodedId = bookingValidating.floorId.asString()
                .replace("<", "&lt;")
                .replace(">", "&gt;")
            fail(
                errorValidation(
                    field = "floorId",
                    violationCode = "badFormat",
                    description = "value $encodedId must be in correct format"
                )
            )
        }
    }
}