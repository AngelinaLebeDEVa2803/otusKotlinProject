package ru.otus.otuskotlin.smartoffice.mappers.v1.exceptions

class UnknownRequestClass(clazz: Class<*>) : RuntimeException("Class $clazz cannot be mapped to OfficeContext")
