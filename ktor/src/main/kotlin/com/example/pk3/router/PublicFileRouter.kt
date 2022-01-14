package com.example.pk3.router

import com.example.pk3.configuration.envPublicFile
import io.ktor.application.*
import io.ktor.http.content.*
import io.ktor.routing.*

fun Application.registerPublicFileRouter() {
    routing {
        static("files") {
            staticRootFolder = envPublicFile()

            default("default.txt")
            files("sample-files")
        }
    }
}
