package com.example.pk3.router

import io.ktor.application.*
import io.ktor.http.content.*
import io.ktor.routing.*
import java.io.File

fun Application.registerAssetFileRouter() {
    routing {
        println("-----------------123241")
        println(File("").absolutePath)
        println(System.getenv("PK3_KTOR_PATH"))

        static("files") {
            staticRootFolder = File("public")

            default("default.txt")
            files("sample-files")
        }
    }
}
