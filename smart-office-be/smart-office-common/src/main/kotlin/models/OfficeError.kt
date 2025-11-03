package ru.otus.otuskotlin.smartoffice.common.models

data class OfficeError(
    val code: String = "",
    val group: String = "",
    val field: String = "",
    val message: String = "",
    val exception: Throwable? = null,
)
