package ru.otus.otuskotlin.smartoffice.common.helpers

import ru.otus.otuskotlin.smartoffice.common.OfficeContext
import ru.otus.otuskotlin.smartoffice.common.models.OfficeError
import ru.otus.otuskotlin.smartoffice.common.models.OfficeState
import ru.otus.otuskotlin.smartoffice.logging.common.LogLevel

fun Throwable.asOfficeError(
    code: String = "unknown",
    group: String = "exceptions",
    message: String = this.message ?: "",
) = OfficeError(
    code = code,
    group = group,
    field = "",
    message = message,
    exception = this,
)

inline fun OfficeContext.addError(error: OfficeError) = errors.add(error)
inline fun OfficeContext.addErrors(error: Collection<OfficeError>) = errors.addAll(error)

inline fun OfficeContext.fail(error: OfficeError) {
    addError(error)
    state = OfficeState.FAILING
}

inline fun OfficeContext.fail(errors: Collection<OfficeError>) {
    addErrors(errors)
    state = OfficeState.FAILING
}


inline fun errorValidation(
    field: String,
    /**
     * Код, характеризующий ошибку. Не должен включать имя поля или указание на валидацию.
     * Например: empty, badSymbols, tooLong, etc
     */
    violationCode: String,
    description: String,
    level: LogLevel = LogLevel.ERROR,
) = OfficeError(
    code = "validation-$field-$violationCode",
    field = field,
    group = "validation",
    message = "Validation error for field $field: $description",
    level = level,
)

inline fun errorSystem(
    violationCode: String,
    level: LogLevel = LogLevel.ERROR,
    e: Throwable,
) = OfficeError(
    code = "system-$violationCode",
    group = "system",
    message = "System error occurred. Our stuff has been informed, please retry later",
    level = level,
    exception = e,
)