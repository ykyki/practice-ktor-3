package com.example.pk3.domain.tutorial.customer

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Customer(
    val id: Long,
    @SerialName("firstName") val firstName: String,
    @SerialName("lastName") val lastName: String,
    val email: String
)
