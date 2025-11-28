package ru.otus.otuskotlin.smartoffice.repo.pgjvm


import org.jetbrains.exposed.sql.Table
import org.postgresql.util.PGobject
import ru.otus.otuskotlin.smartoffice.common.models.OfficeBookingStatus

fun Table.statusEnumeration(
    columnName: String
) = customEnumeration(
    name = columnName,
    sql = SqlFields.STATUS_TYPE,
    fromDb = { value ->
        when (value.toString()) {
            SqlFields.STATUS_ACTIVE -> OfficeBookingStatus.ACTIVE
            SqlFields.STATUS_COMPLETED -> OfficeBookingStatus.COMPLETED
            SqlFields.STATUS_CANCELLED -> OfficeBookingStatus.CANCELLED
            else -> OfficeBookingStatus.NONE
        }
    },
    toDb = { value ->
        when (value) {
            OfficeBookingStatus.ACTIVE -> PgBookingStatusActive
            OfficeBookingStatus.COMPLETED -> PgBookingStatusCompleted
            OfficeBookingStatus.CANCELLED -> PgBookingStatusCancelled
            OfficeBookingStatus.NONE -> throw Exception("Wrong value of status. NONE is unsupported")
        }
    }
)

sealed class PgBookingStatusValue(enVal: String): PGobject() {
    init {
        type = SqlFields.STATUS_TYPE
        value = enVal
    }
}

object PgBookingStatusActive: PgBookingStatusValue(SqlFields.STATUS_ACTIVE) {
    private fun readResolve(): Any = PgBookingStatusActive
}

object PgBookingStatusCompleted: PgBookingStatusValue(SqlFields.STATUS_COMPLETED) {
    private fun readResolve(): Any = PgBookingStatusCompleted
}

object PgBookingStatusCancelled: PgBookingStatusValue(SqlFields.STATUS_CANCELLED) {
    private fun readResolve(): Any = PgBookingStatusCancelled
}
