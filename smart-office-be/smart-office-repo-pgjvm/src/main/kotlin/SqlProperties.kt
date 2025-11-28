package ru.otus.otuskotlin.smartoffice.repo.pgjvm

data class SqlProperties(
    val host: String = "localhost",
    val port: Int = 5432,
    val user: String = "postgres",
    val password: String = "smart123",
    val database: String = "smartoffice_bookings",
    val schema: String = "public",
    val table: String = "bookings",
) {
    val url: String
        get() = "jdbc:postgresql://${host}:${port}/${database}"
}
