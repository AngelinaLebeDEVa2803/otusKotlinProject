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

import ru.otus.otuskotlin.smartoffice.stubs.OfficeBookingStub
import kotlin.test.assertEquals


class MapperTest {
    @Test
    fun fromTransport() {
        val req = BookingCreateRequest(
            debug = BookingDebug(
                mode = BookingRequestDebugMode.STUB,
                stub = BookingRequestDebugStubs.DB_ERROR,
            ),
            booking = OfficeBookingStub.get().toTransportCreateBooking()
        )

        val expected = OfficeBookingStub.prepareResult {
            id = OfficeBookingId.NONE
            lock = OfficeBookingLock.NONE
            permissionsClient.clear()
        }

        val context = OfficeContext()
        context.fromTransport(req)

        assertEquals(OfficeStubs.DB_ERROR, context.stubCase)
        assertEquals(OfficeWorkMode.STUB, context.workMode)
        assertEquals(expected, context.bookingRequest)
    }

    @Test
    fun toTransport() {
        val context = OfficeContext(
            requestId = OfficeRequestId("1234"),
            command = OfficeCommand.CREATE,
            bookingResponse = OfficeBookingStub.get(),
            errors = mutableListOf(
                OfficeError(
                    code = "err",
                    group = "request",
                    field = "floorId",
                    message = "invalid floorId",
                )
            ),
            state = OfficeState.RUNNING,
        )

        val req = context.toTransportBooking() as BookingCreateResponse

        assertEquals(req.booking, OfficeBookingStub.get().toTransportBooking())
        assertEquals(1, req.errors?.size)
        assertEquals("err", req.errors?.firstOrNull()?.code)
        assertEquals("request", req.errors?.firstOrNull()?.group)
        assertEquals("floorId", req.errors?.firstOrNull()?.field)
        assertEquals("invalid floorId", req.errors?.firstOrNull()?.message)
    }
}
