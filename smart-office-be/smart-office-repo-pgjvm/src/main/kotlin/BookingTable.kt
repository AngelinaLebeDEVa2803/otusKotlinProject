package ru.otus.otuskotlin.smartoffice.repo.pgjvm

import kotlinx.datetime.toKotlinInstant
import kotlinx.datetime.toJavaInstant
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.jetbrains.exposed.sql.javatime.timestamp
import ru.otus.otuskotlin.smartoffice.common.models.*

class BookingTable(tableName: String) : Table(tableName) {
    val id = text(SqlFields.ID)
    val userId = text(SqlFields.USER_ID)
    val floorId = text(SqlFields.FLOOR_ID)
    val roomId = text(SqlFields.ROOM_ID)
    val workspaceId = text(SqlFields.WORKSPACE_ID)
    val startTime = timestamp(SqlFields.START_TIME)
    val endTime = timestamp(SqlFields.END_TIME)
    val status = statusEnumeration(SqlFields.STATUS)
    val lock = text(SqlFields.LOCK)

    override val primaryKey = PrimaryKey(id)

    fun from(res: ResultRow) = OfficeBooking(
        id = OfficeBookingId(res[id]),
        userId = OfficeUserId(res[userId]),
        floorId = OfficeFloorId(res[floorId]),
        roomId = OfficeRoomId(res[roomId]),
        workspaceId = OfficeWorkspaceId(res[workspaceId]),
        startTime = res[startTime].toKotlinInstant(),
        endTime = res[endTime].toKotlinInstant(),
        status = res[status],
        lock = OfficeBookingLock(res[lock]),
    )

    fun UpdateBuilder<*>.to(booking: OfficeBooking, randomUuid: () -> String) {
        this[id] = booking.id.takeIf { it != OfficeBookingId.NONE }?.asString() ?: randomUuid()
        this[userId] = booking.userId.asString()
        this[floorId] = booking.floorId.asString()
        this[roomId] = booking.roomId.asString()
        this[workspaceId] = booking.workspaceId.asString()
        this[startTime] = booking.startTime.toJavaInstant()
        this[endTime] = booking.endTime.toJavaInstant()
        this[status] = booking.status
        this[lock] = booking.lock.takeIf { it != OfficeBookingLock.NONE }?.asString() ?: randomUuid()
    }
}

