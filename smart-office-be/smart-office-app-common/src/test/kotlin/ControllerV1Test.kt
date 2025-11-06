package ru.otus.otuskotlin.smartoffice.app.common

import kotlinx.coroutines.test.runTest
import ru.otus.otuskotlin.smartoffice.mappers.v1.fromTransport
import ru.otus.otuskotlin.smartoffice.mappers.v1.toTransportBooking
import ru.otus.otuskotlin.smartoffice.api.v1.models.*
import ru.otus.otuskotlin.smartoffice.biz.OfficeBookingProcessor
import ru.otus.otuskotlin.smartoffice.common.OfficeCorSettings
import kotlin.test.Test
import kotlin.test.assertEquals

class ControllerV2Test {

    private val request = BookingCreateRequest(
        booking = BookingCreateObject(
            title = "some ad",
            description = "some description of some ad",
            adType = DealSide.DEMAND,
            visibility = AdVisibility.PUBLIC,
            productId = "some product id",
        ),
        debug = BookingDebug(mode = BookingRequestDebugMode.STUB, stub = BookingRequestDebugStubs.SUCCESS)
    )

    private val appSettings: IOfficeAppSettings = object : IOfficeAppSettings {
        override val corSettings: OfficeCorSettings = OfficeCorSettings()
        override val processor: OfficeBookingProcessor = OfficeBookingProcessor(corSettings)
    }

    private suspend fun createAdSpring(request: AdCreateRequest): AdCreateResponse =
        appSettings.controllerHelper(
            { fromTransport(request) },
            { toTransportAd() as AdCreateResponse },
            ControllerV2Test::class,
            "controller-v2-test"
        )

    class TestApplicationCall(private val request: IRequest) {
        var res: IResponse? = null

        @Suppress("UNCHECKED_CAST")
        fun <T : IRequest> receive(): T = request as T
        fun respond(res: IResponse) {
            this.res = res
        }
    }

    private suspend fun TestApplicationCall.createAdKtor(appSettings: IMkplAppSettings) {
        val resp = appSettings.controllerHelper(
            { fromTransport(receive<AdCreateRequest>()) },
            { toTransportAd() },
            ControllerV2Test::class,
            "controller-v2-test"
        )
        respond(resp)
    }

    @Test
    fun springHelperTest() = runTest {
        val res = createAdSpring(request)
        assertEquals(ResponseResult.SUCCESS, res.result)
    }

}
