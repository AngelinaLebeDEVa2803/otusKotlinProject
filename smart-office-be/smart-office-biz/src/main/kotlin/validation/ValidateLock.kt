package ru.otus.otuskotlin.smartoffice.biz.validation

import ru.otus.otuskotlin.smartoffice.common.OfficeContext
import ru.otus.otuskotlin.smartoffice.common.helpers.errorValidation
import ru.otus.otuskotlin.smartoffice.common.helpers.fail
import ru.otus.otuskotlin.smartoffice.common.models.OfficeState
import ru.otus.otuskotlin.smartoffice.common.models.OfficeBookingLock
import ru.otus.otuskotlin.smartoffice.cor.ICorChainDsl
import ru.otus.otuskotlin.smartoffice.cor.chain
import ru.otus.otuskotlin.smartoffice.cor.worker


fun ICorChainDsl<OfficeContext>.validateLock(title: String) = chain {
    this.title = title
    this.description = """
        Валидация lock. Должен быть не пустым и соответствовать регулярке
    """.trimIndent()
    on { state == OfficeState.RUNNING }
    worker("Обрезка пустых символов") { bookingValidating.lock = OfficeBookingLock(bookingValidating.lock.asString().trim())  }
    worker {
        this.title = "Проверка на непустой id"
        this.description = this.title
        on { state == OfficeState.RUNNING && bookingValidating.lock.asString().isEmpty() }
        handle {
            fail(
                errorValidation(
                    field = "lock",
                    violationCode = "empty",
                    description = "field must not be empty"
                )
            )
        }
    }
    worker {
        this.title = "Проверка формата id"
        this.description = this.title

        val regExp = Regex("^[0-9a-zA-Z-]+$")
        on { state == OfficeState.RUNNING && bookingValidating.lock != OfficeBookingLock.NONE && !bookingValidating.lock.asString().matches(regExp)}
        handle {
            val encodedId = bookingValidating.lock.asString()
            fail(
                errorValidation(
                    field = "lock",
                    violationCode = "badFormat",
                    description = "value $encodedId must contain only number and letters"
                )
            )
        }
    }
}