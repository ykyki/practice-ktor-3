package com.example.pk3.util

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.WithDataTestName
import io.kotest.datatest.withData
import io.kotest.matchers.longs.shouldBeExactly
import io.kotest.matchers.throwable.shouldHaveMessage
import io.ktor.features.*
import io.ktor.http.*

class ParameterConverterKtSpec : FunSpec({
    context("getLong") {
        data class Assumption(val key: String, val value: String, val expected: Long) : WithDataTestName {
            override fun dataTestName() = "想定: $key=$value to $expected"
        }

        withData(
            Assumption("index1", "1234567890", 1234567890L),
            Assumption("index-2", "0", 0)
        ) {
            parametersOf(it.key, it.value).getLong(it.key) shouldBeExactly it.expected
        }
        test("対応する値が複数あるときには最初のものを取る") {
            parametersOf("index-1", listOf("123", "456")).getLong("index-1") shouldBeExactly 123L
        }
        test("対応する値が見つからないときには例外") {
            shouldThrow<MissingRequestParameterException> {
                parametersOf().getLong("index-name")
            }.let {
                it shouldHaveMessage "Request parameter index-name is missing"
            }
        }
        test("対応する値の変換ができないときには例外") {
            shouldThrow<ParameterConversionException> {
                parametersOf("index-name", "123A").getLong("index-name")
            }.let {
                it shouldHaveMessage "Request parameter index-name couldn't be parsed/converted to Long"
            }
        }
    }
})
