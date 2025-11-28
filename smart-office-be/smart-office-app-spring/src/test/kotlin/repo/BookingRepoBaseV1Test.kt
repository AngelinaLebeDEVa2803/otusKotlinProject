package ru.otus.otuskotlin.smartoffice.app.spring.repo

import kotlinx.datetime.Instant
import org.assertj.core.api.Assertions.assertThat
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import ru.otus.otuskotlin.smartoffice.api.v1.models.*
import ru.otus.otuskotlin.smartoffice.common.OfficeContext
import ru.otus.otuskotlin.smartoffice.common.models.*
import ru.otus.otuskotlin.smartoffice.mappers.v1.*
import ru.otus.otuskotlin.smartoffice.stubs.OfficeBookingStub
import kotlin.test.Test

internal abstract class BookingRepoBaseV1Test {
    protected abstract var webClient: WebTestClient
    private val debug = BookingDebug(mode = BookingRequestDebugMode.TEST)
    protected val uuidNew = "10000000-0000-0000-0000-000000000001"

    @Test
    open fun createBooking() = testRepoBooking(
        "create",
        BookingCreateRequest(
            booking = OfficeBookingStub.get().toTransportCreate(),
            debug = debug,
        ),
        prepareCtx(OfficeBookingStub.prepareResult {
            id = OfficeBookingId(uuidNew)
            lock = OfficeBookingLock(uuidNew)
        })
            .toTransportCreate()
            .copy(responseType = "create")
    )

    @Test
    open fun readBooking() = testRepoBooking(
        "read",
        BookingReadRequest(
            booking = OfficeBookingStub.get().toTransportRead(),
            debug = debug,
        ),
        prepareCtx(OfficeBookingStub.get())
            .toTransportRead()
            .copy(responseType = "read")
    )

    @Test
    open fun updateBooking() = testRepoBooking(
        "update",
        BookingUpdateRequest(
            booking = OfficeBookingStub.prepareResult {
                userId = OfficeUserId("user1")}.toTransportUpdate(),
            debug = debug,
        ),
        prepareCtx(OfficeBookingStub.prepareResult {
            userId = OfficeUserId("user1")
            lock = OfficeBookingLock(uuidNew) })
            .toTransportUpdate().copy(responseType = "update")
    )

    @Test
    open fun deleteBooking() = testRepoBooking(
        "delete",
        BookingDeleteRequest(
            booking = OfficeBookingStub.get().toTransportDelete(),
            debug = debug,
        ),
        prepareCtx(OfficeBookingStub.get())
            .toTransportDelete()
            .copy(responseType = "delete")
    )

    @Test
    open fun allBooking() = testRepoBooking(
        "all",
        BookingAllRequest(
            bookingFilter = BookingAllFilter(userId = "test_all_spring", status = BookingStatus.CANCELLED),
            debug = debug,
        ),
        OfficeContext(
            state = OfficeState.RUNNING,
            bookingsResponse = OfficeBookingStub.prepareBookingsList(userId =  OfficeUserId("test_all_spring"),
                startTime = Instant.parse("2026-07-01T09:00:00Z"),
                endTime = Instant.parse("2026-07-01T19:00:00Z"),
                status =  OfficeBookingStatus.CANCELLED)
                .onEach { it.permissionsClient.clear() }
                .sortedBy { it.id.asString() }
                .toMutableList()
        )
            .toTransportAll().copy(responseType = "all")
    )



    private fun prepareCtx(booking: OfficeBooking) = OfficeContext(
        state = OfficeState.RUNNING,
        bookingResponse = booking.apply {
            permissionsClient.clear()
        },
    )

    private inline fun <reified Req : Any, reified Res : IResponse> testRepoBooking(
        url: String,
        requestObj: Req,
        expectObj: Res,
    ) {
        webClient
            .post()
            .uri("/v1/booking/$url")
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(requestObj))
            .exchange()
            .expectStatus().isOk
            .expectBody(Res::class.java)
            .value {
                println("RESPONSE: $it")
                val sortedResp: IResponse = when (it) {
                    is BookingAllResponse -> it.copy(bookings = it.bookings?.sortedBy { it.id })
                    else -> it
                }
                assertThat(sortedResp).isEqualTo(expectObj)
            }
    }
}
