package repo

import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Instant
import ru.otus.otuskotlin.smartoffice.repo.tests.BookingRepositoryMock
import ru.otus.otuskotlin.smartoffice.biz.OfficeBookingProcessor
import ru.otus.otuskotlin.smartoffice.common.OfficeContext
import ru.otus.otuskotlin.smartoffice.common.OfficeCorSettings
import ru.otus.otuskotlin.smartoffice.common.models.*
import ru.otus.otuskotlin.smartoffice.common.repo.DbBookingResponseOk
import ru.otus.otuskotlin.smartoffice.common.repo.errorNotFound
import kotlin.test.assertEquals
import kotlin.test.assertNotNull


private val bookingId = OfficeBookingId("b4285985-1d55-4f75-ad25-92f592e0d965")
private val userId = OfficeUserId("321")
private val floorId = OfficeFloorId("floor_666")
private val roomId = OfficeRoomId("room_9")
private val workspaceId = OfficeWorkspaceId("040")

private val startT = Instant.parse("2028-02-19T09:00:00Z")
private val endT = Instant.parse("2028-02-19T19:00:00Z")

private val initBooking = OfficeBooking(
    id = bookingId,
    userId = userId,
    floorId = floorId,
    roomId = roomId,
    workspaceId = workspaceId,
    startTime = startT,
    endTime = endT,
    status = OfficeBookingStatus.ACTIVE,
)

private val repo = BookingRepositoryMock(
    invokeReadBooking = {
        if (it.id == initBooking.id) {
            DbBookingResponseOk(
                data = initBooking,
            )
        } else errorNotFound(it.id)
    }
)
private val settings = OfficeCorSettings(repoTest = repo)
private val processor = OfficeBookingProcessor(settings)

fun repoNotFoundTest(command: OfficeCommand) = runTest {
    val ctx = OfficeContext(
        command = command,
        state = OfficeState.NONE,
        workMode = OfficeWorkMode.TEST,
        bookingRequest = OfficeBooking(
            id = OfficeBookingId("7848abb1-cef1-4347-b9ab-86c6e71b21d2"),
            userId = userId,
            floorId = floorId,
            roomId = roomId,
            workspaceId = workspaceId,
            startTime = startT,
            endTime = endT,
            status = OfficeBookingStatus.ACTIVE,
            lock = OfficeBookingLock("123-234-abc-ABC"),
        ),
    )
    processor.exec(ctx)
    assertEquals(OfficeState.FAILING, ctx.state)
    assertEquals(OfficeBooking(), ctx.bookingResponse)
    assertEquals(1, ctx.errors.size)
    assertNotNull(ctx.errors.find { it.code == "repo-not-found" }, "Errors must contain not-found")
}
