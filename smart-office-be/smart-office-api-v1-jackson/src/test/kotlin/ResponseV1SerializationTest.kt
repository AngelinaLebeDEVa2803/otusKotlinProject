package ru.otus.otuskotlin.smartoffice.api.v1

import ru.otus.otuskotlin.smartoffice.api.v1.models.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class ResponseV1SerializationTest {
    private val response = BookingCreateResponse(
        booking = BookingResponseObject(
            userId = "4656",
            floorId = "ggu7777",
            roomId = "887",
            workspaceId = "009",
            startTime = "2025-04-03 10:00",
            endTime = "2025-04-03 19:00",
            status = BookingStatus.ACTIVE,
        )
    )

    @Test
    fun serialize() {
        val json = apiV1Mapper.writeValueAsString(response)

        assertContains(json, Regex("\"workspaceId\":\\s*\"009\""))
        assertContains(json, Regex("\"responseType\":\\s*\"create\""))
    }

    @Test
    fun deserialize() {
        val json = apiV1Mapper.writeValueAsString(response)
        val obj = apiV1Mapper.readValue(json, IResponse::class.java) as BookingCreateResponse

        assertEquals(response, obj)
    }
}