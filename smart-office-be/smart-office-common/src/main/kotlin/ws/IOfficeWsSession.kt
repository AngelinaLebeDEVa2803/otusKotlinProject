package ru.otus.otuskotlin.smartoffice.common.ws

interface IOfficeWsSession {
    suspend fun <T> send(obj: T)
    companion object {
        val NONE = object : IOfficeWsSession {
            override suspend fun <T> send(obj: T) {

            }
        }
    }
}
