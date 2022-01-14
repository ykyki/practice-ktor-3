package com.example.pk3.util

import io.ktor.features.*
import io.ktor.http.*

fun Parameters.getLong(parameterName: String): Long {
    val form = this[parameterName] ?: throw MissingRequestParameterException(parameterName)

    return form.toLongOrNull() ?: throw ParameterConversionException(parameterName, "Long")
}
