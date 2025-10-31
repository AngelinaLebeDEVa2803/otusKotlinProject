package ru.otus.otuskotlin.smartoffice.common

data class OfficeError(
    val code: String = "",
    val group: String = "",
    val field: String = "",
    val message: String = "",
    val exception: Throwable? = null,
)
