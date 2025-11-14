package ru.otus.otuskotlin.smartoffice.biz.validation

import ru.otus.otuskotlin.smartoffice.common.OfficeContext
import ru.otus.otuskotlin.smartoffice.common.helpers.errorValidation
import ru.otus.otuskotlin.smartoffice.common.helpers.fail
import ru.otus.otuskotlin.smartoffice.common.models.OfficeState
import ru.otus.otuskotlin.smartoffice.common.models.OfficeUserId
import ru.otus.otuskotlin.smartoffice.cor.ICorChainDsl
import ru.otus.otuskotlin.smartoffice.cor.chain
import ru.otus.otuskotlin.smartoffice.cor.worker


fun ICorChainDsl<OfficeContext>.validateUserId(title: String) = chain {
    this.title = title
    this.description = """
        Валидация идентификатора пользователя userId. Должен быть не пустым и соответствовать регулярке
    """.trimIndent()
    on { state == OfficeState.RUNNING }
    worker("Обрезка пустых символов") { bookingValidating.userId = OfficeUserId(bookingValidating.userId.asString().trim()) }
    worker {
        this.title = "Проверка на непустой userId"
        this.description = this.title
        on { state == OfficeState.RUNNING && bookingValidating.userId.asString().isEmpty() }
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
    worker {
        this.title = "Проверка формата userId"
        this.description = this.title

        val regExp = Regex("^[0-9a-zA-Z#:-]+$")
        on { state == OfficeState.RUNNING && bookingValidating.userId != OfficeUserId.NONE && !bookingValidating.userId.asString().matches(regExp) }
        handle {
            val encodedId = bookingValidating.userId.asString()
                .replace("<", "&lt;")
                .replace(">", "&gt;")
            fail(
                errorValidation(
                    field = "userId",
                    violationCode = "badFormat",
                    description = "value $encodedId must contain only letters and numbers"
                )
            )
        }
    }
}

fun ICorChainDsl<OfficeContext>.validateUserIdFilter(title: String) = chain {
    this.title = title
    this.description = """
        Валидация идентификатора пользователя userId. Должен быть не пустым и соответствовать регулярке
    """.trimIndent()
    on { state == OfficeState.RUNNING }
    worker("Обрезка пустых символов") { bookingFilterValidating.userId = OfficeUserId(bookingFilterValidating.userId.asString().trim()) }
    worker {
        this.title = "Проверка на непустой userId"
        this.description = this.title
        on { state == OfficeState.RUNNING && bookingFilterValidating.userId.asString().isEmpty() }
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
    worker {
        this.title = "Проверка формата userId"
        this.description = this.title

        val regExp = Regex("^[0-9a-zA-Z#:-]+$")
        on { state == OfficeState.RUNNING && bookingFilterValidating.userId != OfficeUserId.NONE && !bookingFilterValidating.userId.asString().matches(regExp) }
        handle {
            val encodedId = bookingFilterValidating.userId.asString()
                .replace("<", "&lt;")
                .replace(">", "&gt;")
            fail(
                errorValidation(
                    field = "userId",
                    violationCode = "badFormat",
                    description = "value $encodedId must contain only letters and numbers"
                )
            )
        }
    }
}