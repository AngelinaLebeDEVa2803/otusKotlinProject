package ru.otus.otuskotlin.smartoffice.api.v1

import ru.otus.otuskotlin.smartoffice.api.v1.models.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class RequestV1SerializationTest {
    private val request = BookingCreateRequest(
        debug = BookingDebug(
            mode = BookingRequestDebugMode.STUB,
            stub = BookingRequestDebugStubs.SUCCESS
        ),
        booking = BookingCreateObject(
            userId = "001",
            floorId = "floor_2",
            roomId = "418",
            workspaceId = "007",
            startTime = "2025-01-10 09:00",
            endTime = "2025-01-10 18:00",
            status = BookingStatus.ACTIVE,
        )
    )

    @Test
    fun serialize() {
        val json = apiV1Mapper.writeValueAsString(request)

        assertContains(json, Regex("\"floorId\":\\s*\"floor_2\""))
        assertContains(json, Regex("\"workspaceId\":\\s*\"007\""))
        assertContains(json, Regex("\"endTime\":\\s*\"2025-01-10 18:00\""))
        assertContains(json, Regex("\"mode\":\\s*\"stub\""))
        assertContains(json, Regex("\"stub\":\\s*\"success\""))
        assertContains(json, Regex("\"requestType\":\\s*\"create\""))
    }

//    @Test
//    fun deserialize() {
//        val json = apiV1Mapper.writeValueAsString(request)
//        val obj = apiV1Mapper.readValue(json, IRequest::class.java) as AdCreateRequest
//
//        assertEquals(request, obj)
//    }

//    @Test
//    fun deserializeNaked() {
//        val jsonString = """
//            {"ad": null}
//        """.trimIndent()
//        val obj = apiV1Mapper.readValue(jsonString, AdCreateRequest::class.java)
//
//        assertEquals(null, obj.ad)
//    }
}