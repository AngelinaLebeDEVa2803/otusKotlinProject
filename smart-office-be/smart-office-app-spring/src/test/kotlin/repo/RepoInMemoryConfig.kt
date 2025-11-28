package ru.otus.otuskotlin.smartoffice.app.spring.repo


import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import ru.otus.otuskotlin.smartoffice.common.repo.IRepoBooking
import ru.otus.otuskotlin.smartoffice.repo.inmemory.BookingRepoInMemory

@TestConfiguration
class RepoInMemoryConfig {
    @Suppress("unused")
    @Bean()
    @Primary
    fun prodRepo(): IRepoBooking = BookingRepoInMemory()
}
