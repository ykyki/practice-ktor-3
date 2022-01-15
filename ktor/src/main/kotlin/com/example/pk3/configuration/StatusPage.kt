package com.example.pk3.configuration

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*

fun Application.configureStatusPages() {
    install(StatusPages) {
        exception<BadRequestException> {
            call.respond(HttpStatusCode.BadRequest, it.message.toString())
        }
        exception<NotFoundException> {
            call.respond(HttpStatusCode.NotFound, it.message.toString())
        }

        status(HttpStatusCode.NotFound) {
            call.respond(HttpStatusCode.NotFound, "Page not found")
        }
        status(HttpStatusCode.InternalServerError) {
            call.respond(HttpStatusCode.InternalServerError, "Internal server error")
        }
    }
}
