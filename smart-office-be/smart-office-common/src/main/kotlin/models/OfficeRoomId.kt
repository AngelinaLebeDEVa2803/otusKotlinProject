package ru.otus.otuskotlin.smartoffice.common.models

import kotlin.jvm.JvmInline

@JvmInline
value class OfficeRoomId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = OfficeRoomId("")
    }
}
