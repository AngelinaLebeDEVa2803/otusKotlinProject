package ru.otus.otuskotlin.smartoffice.common.helpers

import ru.otus.otuskotlin.smartoffice.common.OfficeContext
import ru.otus.otuskotlin.smartoffice.common.models.OfficeError
import ru.otus.otuskotlin.smartoffice.common.models.OfficeState

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

inline fun OfficeContext.addError(vararg error: OfficeError) = errors.addAll(error)

inline fun OfficeContext.fail(error: OfficeError) {
    addError(error)
    state = OfficeState.FAILING
}
