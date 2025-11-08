package ru.otus.otuskotlin.smartoffice.common.ws

interface IOfficeWsSessionRepo {
    fun add(session: IOfficeWsSession)
    fun clearAll()
    fun remove(session: IOfficeWsSession)
    suspend fun <K> sendAll(obj: K)

    companion object {
        val NONE = object : IOfficeWsSessionRepo {
            override fun add(session: IOfficeWsSession) {}
            override fun clearAll() {}
            override fun remove(session: IOfficeWsSession) {}
            override suspend fun <K> sendAll(obj: K) {}
        }
    }
}
