import ru.otus.otuskotlin.smartoffice.repo.tests.*
import ru.otus.otuskotlin.smartoffice.repo.common.BookingRepoInitialized
import ru.otus.otuskotlin.smartoffice.repo.inmemory.BookingRepoInMemory

class BookingRepoInMemoryCreateTest : RepoBookingCreateTest() {
    override val repo = BookingRepoInitialized(
        BookingRepoInMemory(randomUuid = { uuidNew.asString() }),
        initObjects = initObjects,
    )
}

class BookingRepoInMemoryDeleteTest : RepoBookingDeleteTest() {
    override val repo = BookingRepoInitialized(
        BookingRepoInMemory(),
        initObjects = initObjects,
    )
}

class BookingRepoInMemoryReadTest : RepoBookingReadTest() {
    override val repo = BookingRepoInitialized(
        BookingRepoInMemory(),
        initObjects = initObjects,
    )
}

class BookingRepoInMemoryAllTest : RepoBookingAllTest() {
    override val repo = BookingRepoInitialized(
        BookingRepoInMemory(),
        initObjects = initObjects,
    )
}

class BookingRepoInMemoryUpdateTest : RepoBookingUpdateTest() {
    override val repo = BookingRepoInitialized(
        BookingRepoInMemory(randomUuid = { lockNew.asString() }),
        initObjects = initObjects,
    )
}
