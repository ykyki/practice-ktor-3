package com.example.pk3.router

import io.kotest.assertions.ktor.shouldHaveContent
import io.kotest.assertions.ktor.shouldHaveContentType
import io.kotest.assertions.ktor.shouldHaveStatus
import io.kotest.core.spec.style.StringSpec
import io.ktor.http.*
import io.ktor.server.testing.*
import withTestRootModule

class PublicFileRouterKtSpec : StringSpec({
    "GET: /files" {
        withTestRootModule {
            handleRequest(method = HttpMethod.Get, uri = "/files").apply {
                response shouldHaveStatus HttpStatusCode.OK
                response.shouldHaveContentType(ContentType.Text.Plain.withCharset(Charsets.UTF_8))
                response shouldHaveContent """
                    abc12345
                    def67890
                    
                    """.trimIndent()
            }
        }
    }
    "GET: /hello-ktor.html" {
        withTestRootModule {
            handleRequest(method = HttpMethod.Get, uri = "/files/hello-ktor.html").apply {
                response shouldHaveStatus HttpStatusCode.OK
                response.shouldHaveContentType(ContentType.Text.Html.withCharset(Charsets.UTF_8))
            }
        }
    }
})
