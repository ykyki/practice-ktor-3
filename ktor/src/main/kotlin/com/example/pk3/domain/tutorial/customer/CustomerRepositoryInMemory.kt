package com.example.pk3.domain.tutorial.customer

import java.time.LocalDateTime

class CustomerRepositoryInMemory : CustomerRepository {
    private val customerStorage = mutableListOf(
        Customer(0, "Foo", "Bar", LocalDateTime.now().toString())
    )

    override fun findAll(): List<Customer> {
        return customerStorage
    }

    override fun find(id: Long): Customer? {
        return customerStorage.find { it.id == id }
    }

    override fun create(customer: Customer) {
        customerStorage.add(customer)
    }

    override fun delete(id: Long): Boolean {
        return customerStorage.removeIf { it.id == id }
    }
}
