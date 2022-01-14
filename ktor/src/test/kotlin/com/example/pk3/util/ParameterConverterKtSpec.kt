package com.example.pk3.util

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.longs.shouldBeExactly
import io.ktor.http.*

class ParameterConverterKtSpec : FunSpec({
    context("getLong") {
        context("正常系") {
            test("sample case") {
                val paramters: Parameters = parametersOf("index1", "1234567890")

                paramters.getLong("index1") shouldBeExactly 1234567890L
            }
        }
        context("異常系") {
            xtest("Missing parameter") {
                // TODO
            }
            xtest("Parameter conversion fails") {
                // TODO
            }
        }
    }
})
