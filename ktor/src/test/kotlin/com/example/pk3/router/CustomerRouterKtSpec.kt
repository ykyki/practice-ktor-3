package com.example.pk3.router

import com.example.pk3.domain.tutorial.customer.Customer
import io.kotest.assertions.ktor.shouldHaveContent
import io.kotest.assertions.ktor.shouldHaveStatus
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.string.shouldStartWith
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import shouldEqualJsonStrictly
import withTestRootModule

class CustomerRouterKtSpec : FunSpec({
    context("/customer") {
        test("GET") {
            withTestRootModule {
                handleRequest(method = HttpMethod.Get, uri = "/customer").apply {
                    response shouldHaveStatus HttpStatusCode.OK
                    response.content!! shouldEqualJsonStrictly
                            """
                                [
                                    {
                                        "id": 0,
                                        "firstName": "Foo",
                                        "lastName": "Bar",
                                        "email": "example@example.com"
                                    }
                                ]
                            """
                }
            }
        }
        test("POST:正当な値") {
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
                        """
                    )
                }.apply {
                    response shouldHaveStatus HttpStatusCode.Created
                    response.content shouldStartWith "Customer stored correctly!:"
                }
            }
        }
        test("POST:正当な値2") {
            withTestRootModule {
                handleRequest(method = HttpMethod.Post, uri = "/customer") {
                    addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                    setBody(Json.encodeToString(Customer(546, "花子", "佐藤", "test@example.com")))
                }.apply {
                    response shouldHaveStatus HttpStatusCode.Created
                    response.content shouldStartWith "Customer stored correctly!:"
                }
            }
        }
    }
    context("/customer/*") {
        test("GET:数値ではないときにはBadRequest") {
            withTestRootModule {
                handleRequest(method = HttpMethod.Get, uri = "/customer/abc000").apply {
                    response shouldHaveStatus HttpStatusCode.BadRequest
                    response shouldHaveContent "Request parameter id couldn't be parsed/converted to Long"
                }
            }
        }
        test("GET:存在しないときにはNotFound") {
            withTestRootModule {
                handleRequest(method = HttpMethod.Get, uri = "/customer/9999").apply {
                    response shouldHaveStatus HttpStatusCode.NotFound
                    response shouldHaveContent "No customer found (id = 9999)"
                }
            }
        }
    }
})
