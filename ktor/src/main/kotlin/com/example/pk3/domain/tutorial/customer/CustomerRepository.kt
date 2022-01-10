package com.example.pk3.domain.tutorial.customer

interface CustomerRepository {
    fun findAll(): List<Customer>

    fun find(id: Long): Customer?

    fun create(customer: Customer)

    fun delete(id: Long): Boolean
}
