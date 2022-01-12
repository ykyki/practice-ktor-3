package com.example.pk3.util

import io.ktor.features.*
import io.ktor.http.*

fun Parameters.longOf(parameterName: String): Long {
    val form: String = this[parameterName] ?: throw MissingRequestParameterException(parameterName)

    return form.toLongOrNull() ?: throw ParameterConversionException(parameterName, "Long")
}
