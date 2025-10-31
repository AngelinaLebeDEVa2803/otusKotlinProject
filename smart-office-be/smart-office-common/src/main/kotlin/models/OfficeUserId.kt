package ru.otus.otuskotlin.smartoffice.common

import kotlin.jvm.JvmInline

@JvmInline
value class OfficeUserId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = OfficeUserId("")
    }
}
