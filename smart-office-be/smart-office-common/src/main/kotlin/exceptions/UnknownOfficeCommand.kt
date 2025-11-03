package ru.otus.otuskotlin.smartoffice.common.exceptions

import ru.otus.otuskotlin.smartoffice.common.models.OfficeCommand

class UnknownOfficeCommand(command: OfficeCommand) : Throwable("Wrong command $command at mapping toTransport stage")