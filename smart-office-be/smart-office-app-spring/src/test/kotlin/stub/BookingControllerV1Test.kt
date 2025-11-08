package ru.otus.otuskotlin.smartoffice.app.spring.stub

import org.assertj.core.api.Assertions.assertThat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import ru.otus.otuskotlin.smartoffice.app.spring.config.BookingConfig
import ru.otus.otuskotlin.smartoffice.app.spring.controllers.BookingControllerV1Fine
import ru.otus.otuskotlin.smartoffice.api.v1.models.*
import ru.otus.otuskotlin.smartoffice.biz.OfficeBookingProcessor
import ru.otus.otuskotlin.smartoffice.common.OfficeContext
import ru.otus.otuskotlin.smartoffice.mappers.v1.*
import kotlin.test.Test

// Temporary simple test with stubs
@WebFluxTest(BookingControllerV1Fine::class, BookingConfig::class)
internal class AdControllerV1Test {
    @Autowired
    private lateinit var webClient: WebTestClient

    @Suppress("unused")
    @MockBean
    private lateinit var processor: OfficeBookingProcessor

    @Test
    fun createBooking() = testStubBooking(
        "/v1/booking/create",
        BookingCreateRequest(),
        OfficeContext().toTransportCreate().copy(responseType = "create")
    )

    @Test
    fun readBooking() = testStubBooking(
        "/v1/booking/read",
        BookingReadRequest(),
        OfficeContext().toTransportRead().copy(responseType = "read")
    )

    @Test
    fun updateAd() = testStubBooking(
        "/v1/booking/update",
        BookingUpdateRequest(),
        OfficeContext().toTransportUpdate().copy(responseType = "update")
    )

    @Test
    fun deleteAd() = testStubBooking(
        "/v1/booking/delete",
        BookingDeleteRequest(),
        OfficeContext().toTransportDelete().copy(responseType = "delete")
    )

    @Test
    fun searchAd() = testStubBooking(
        "/v1/booking/all",
        BookingAllRequest(),
        OfficeContext().toTransportAll().copy(responseType = "all")
    )
    

    private inline fun <reified Req : Any, reified Res : Any> testStubBooking(
        url: String,
        requestObj: Req,
        responseObj: Res,
    ) {
        webClient
            .post()
            .uri(url)
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(requestObj))
            .exchange()
            .expectStatus().isOk
            .expectBody(Res::class.java)
            .value {
                println("RESPONSE: $it")
                assertThat(it).isEqualTo(responseObj)
            }
    }
}
