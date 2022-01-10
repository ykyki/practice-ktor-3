package com.example.pk3.domain.tutorial.customer

import kotlinx.serialization.Serializable

@Serializable
data class Customer(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val email: String
)
