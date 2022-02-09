package com.example.pk3.domain.tutorial.customer

class CustomerRepositoryInMemory : CustomerRepository {
    private val customerStorage = mutableListOf(
        Customer(0, "Foo", "Bar", "example@example.com")
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
