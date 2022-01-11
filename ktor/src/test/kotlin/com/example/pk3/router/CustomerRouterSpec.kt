package com.example.pk3.router

import io.kotest.assertions.ktor.shouldHaveStatus
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldStartWith
import io.ktor.http.*
import io.ktor.server.testing.*
import withTestRootModule

class CustomerRoutesSpec : StringSpec({
    "Get: /customer" {
        withTestRootModule {
            handleRequest(method = HttpMethod.Get, uri = "/customer").apply {
                response shouldHaveStatus HttpStatusCode.OK
                response.content shouldNotBe null
            }
        }
    }
    "GET: /customer/abc000 should returns BadRequest" {
        withTestRootModule {
            handleRequest(method = HttpMethod.Get, uri = "/customer/abc000").apply {
                response shouldHaveStatus HttpStatusCode.BadRequest
                response.content shouldBe "id should be Long"
            }
        }
    }
    "POST(correct customer): /customer" {
        withTestRootModule {
            handleRequest(method = HttpMethod.Post, uri = "/customer") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(
                    """
                          {
                            "id": 1234567890,
                            "firstName": "太郎",
                            "lastName": "山田",
                            "email": "test@example.com"
                          }
                        """.trimIndent()
                )
            }.apply {
                response shouldHaveStatus HttpStatusCode.Created
                response.content shouldStartWith "Customer stored correctly!:"
            }
        }
    }
})
