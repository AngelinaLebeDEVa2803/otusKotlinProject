package ru.otus.otuskotlin.smartoffice.repo.pgjvm


object SqlFields {
    const val ID = "id"
    const val USER_ID = "user_id"
    const val FLOOR_ID = "floor_id"
    const val ROOM_ID = "room_id"
    const val WORKSPACE_ID = "workspace_id"
    const val START_TIME = "start_time"
    const val END_TIME = "end_time"
    const val STATUS = "status"
    const val LOCK = "lock"
    const val LOCK_OLD = "lock_old"

    const val STATUS_TYPE = "status_type"
    const val STATUS_ACTIVE = "active"
    const val STATUS_COMPLETED = "completed"
    const val STATUS_CANCELLED = "cancelled"

    const val DELETE_OK = "DELETE_OK"

    fun String.quoted() = "\"$this\""
    val allFields = listOf(
        ID, USER_ID, FLOOR_ID, ROOM_ID, WORKSPACE_ID, START_TIME, END_TIME, STATUS, LOCK,
    )
}
