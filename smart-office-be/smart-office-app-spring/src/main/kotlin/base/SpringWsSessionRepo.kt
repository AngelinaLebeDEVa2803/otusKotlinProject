package ru.otus.otuskotlin.smartoffice.app.spring.base

import ru.otus.otuskotlin.smartoffice.common.ws.IOfficeWsSession
import ru.otus.otuskotlin.smartoffice.common.ws.IOfficeWsSessionRepo

class SpringWsSessionRepo: IOfficeWsSessionRepo {
    private val sessions: MutableSet<IOfficeWsSession> = mutableSetOf()
    override fun add(session: IOfficeWsSession) {
        sessions.add(session)
    }

    override fun clearAll() {
        sessions.clear()
    }

    override fun remove(session: IOfficeWsSession) {
        sessions.remove(session)
    }

    override suspend fun <T> sendAll(obj: T) {
        sessions.forEach { it.send(obj) }
    }
}
