package ru.otus.otuskotlin.smartoffice.app.common

import kotlinx.coroutines.test.runTest
import ru.otus.otuskotlin.smartoffice.mappers.v1.fromTransport
import ru.otus.otuskotlin.smartoffice.mappers.v1.toTransportBooking
import ru.otus.otuskotlin.smartoffice.api.v1.models.*
import ru.otus.otuskotlin.smartoffice.biz.OfficeBookingProcessor
import ru.otus.otuskotlin.smartoffice.common.OfficeCorSettings
import kotlin.test.Test
import kotlin.test.assertEquals

class ControllerV1Test {

    private val request = BookingCreateRequest(
        booking = BookingCreateObject(
            userId = "1234",
            floorId = "floor_1",
            roomId = "438339",
            workspaceId = "007",
            startTime = "2023-01-15T09:00:00Z",
            endTime = "2023-01-15T18:00:00Z",
            status = BookingStatus.ACTIVE,
        ),
        debug = BookingDebug(mode = BookingRequestDebugMode.STUB, stub = BookingRequestDebugStubs.SUCCESS)
    )

    private val appSettings: IOfficeAppSettings = object : IOfficeAppSettings {
        override val corSettings: OfficeCorSettings = OfficeCorSettings()
        override val processor: OfficeBookingProcessor = OfficeBookingProcessor(corSettings)
    }

    private suspend fun createBookingSpring(request: BookingCreateRequest): BookingCreateResponse =
        appSettings.controllerHelper(
            { fromTransport(request) },
            { toTransportBooking() as BookingCreateResponse },
            ControllerV1Test::class,
            "controller-v1-test"
        )

//    class TestApplicationCall(private val request: IRequest) {
//        var res: IResponse? = null
//
//        @Suppress("UNCHECKED_CAST")
//        fun <T : IRequest> receive(): T = request as T
//        fun respond(res: IResponse) {
//            this.res = res
//        }
//    }

    @Test
    fun springHelperTest() = runTest {
        val res = createBookingSpring(request)
        assertEquals(ResponseResult.SUCCESS, res.result)
    }

}
