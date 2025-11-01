import org.junit.Test
import ru.otus.otuskotlin.smartoffice.api.v1.models.BookingCreateRequest
import ru.otus.otuskotlin.smartoffice.api.v1.models.BookingCreateResponse

import ru.otus.otuskotlin.smartoffice.api.v1.models.BookingDebug
import ru.otus.otuskotlin.smartoffice.api.v1.models.BookingRequestDebugMode
import ru.otus.otuskotlin.smartoffice.api.v1.models.BookingRequestDebugStubs

import ru.otus.otuskotlin.smartoffice.common.OfficeContext
import ru.otus.otuskotlin.smartoffice.common.models.*
import ru.otus.otuskotlin.smartoffice.common.stubs.OfficeStubs

import ru.otus.otuskotlin.smartoffice.mappers.v1.fromTransport
import ru.otus.otuskotlin.smartoffice.mappers.v1.toTransportBooking
import ru.otus.otuskotlin.smartoffice.mappers.v1.toTransportCreateBooking

//import ru.otus.otuskotlin.smartoffice.stubs.MkplAdStub
import kotlin.test.assertEquals


class MapperTest {
    @Test
    fun fromTransport() {
        val req = BookingCreateRequest(
            debug = BookingDebug(
                mode = BookingRequestDebugMode.STUB,
                stub = BookingRequestDebugStubs.SUCCESS,
            ),
            booking = MkplAdStub.get().toTransportCreateAd() // отдельный модуль или тут
        )


        val expected = MkplAdStub.prepareResult {
            id = MkplAdId.NONE
            ownerId = MkplUserId.NONE
            lock = MkplAdLock.NONE
            permissionsClient.clear()
        }

        val context = OfficeContext()
        context.fromTransport(req)

        assertEquals(MkplStubs.SUCCESS, context.stubCase)
        assertEquals(MkplWorkMode.STUB, context.workMode)
        assertEquals(expected, context.adRequest)
    }

    @Test
    fun toTransport() {
        val context = MkplContext(
            requestId = MkplRequestId("1234"),
            command = MkplCommand.CREATE,
            adResponse = MkplAdStub.get(),
            errors = mutableListOf(
                MkplError(
                    code = "err",
                    group = "request",
                    field = "title",
                    message = "wrong title",
                )
            ),
            state = MkplState.RUNNING,
        )

        val req = context.toTransportAd() as AdCreateResponse

        assertEquals(req.ad, MkplAdStub.get().toTransportAd())
        assertEquals(1, req.errors?.size)
        assertEquals("err", req.errors?.firstOrNull()?.code)
        assertEquals("request", req.errors?.firstOrNull()?.group)
        assertEquals("title", req.errors?.firstOrNull()?.field)
        assertEquals("wrong title", req.errors?.firstOrNull()?.message)
    }
}
