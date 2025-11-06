package ru.otus.otuskotlin.smartoffice.common.helpers

import ru.otus.otuskotlin.smartoffice.common.models.OfficeError

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
