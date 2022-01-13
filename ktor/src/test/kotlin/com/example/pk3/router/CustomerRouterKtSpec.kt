package com.example.pk3.router

import io.kotest.assertions.ktor.shouldHaveStatus
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldStartWith
import io.ktor.http.*
import io.ktor.server.testing.*
import withTestRootModule

class CustomerRouterKtSpec : StringSpec({
    "GET: /customer" {
        withTestRootModule {
            handleRequest(method = HttpMethod.Get, uri = "/customer").apply {
                response shouldHaveStatus HttpStatusCode.OK
                response.content shouldNotBe null
            }
        }
    }
    "GET: /customer/abc000 should return BadRequest" {
        withTestRootModule {
            handleRequest(method = HttpMethod.Get, uri = "/customer/abc000").apply {
                response shouldHaveStatus HttpStatusCode.BadRequest
                response.content shouldBe "Request parameter id couldn't be parsed/converted to Long"
            }
        }
    }
    "GET: /customer/9999 should return NotFound" {
        withTestRootModule {
            handleRequest(method = HttpMethod.Get, uri = "/customer/9999").apply {
                response shouldHaveStatus HttpStatusCode.NotFound
                response.content shouldBe "No customer found (id = 9999)"
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
