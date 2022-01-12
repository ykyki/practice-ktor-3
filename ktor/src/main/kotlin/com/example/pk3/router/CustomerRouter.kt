package com.example.pk3.router

import com.example.pk3.domain.tutorial.customer.Customer
import com.example.pk3.domain.tutorial.customer.CustomerRepository
import com.example.pk3.util.longOf
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Application.registerCustomerRouter() {
    val customerRepository: CustomerRepository by inject()

    routing {
        customerRouting(customerRepository)
    }
}

private fun Route.customerRouting(
    customerRepository: CustomerRepository
) {
    route("/customer") {
        get {
            val allCustomer = customerRepository.findAll()

            if (allCustomer.isNotEmpty()) {
                call.respond(allCustomer)
            } else {
                call.respondText("No customer found")
            }
        }

        get("{id}") {
            val id = call.parameters.longOf("id")

            val customer = customerRepository.find(id) ?: throw NotFoundException("No customer found (id = $id)")

            call.respond(customer)
        }

        post {
            val customer = call.receiveOrNull<Customer>() ?: return@post call.respondText("Illegal post")

            customerRepository.create(customer)

            call.respondText("Customer stored correctly!: $customer", status = HttpStatusCode.Created)
        }

        delete("{id}") {
            val id = call.parameters.longOf("id")

            val isDeleted = customerRepository.delete(id)

            if (isDeleted) {
                call.respondText(
                    "Customer with id = $id removed correctly",
                    status = HttpStatusCode.Accepted
                )
            } else {
                call.respondText(
                    "No customer with id $id",
                    status = HttpStatusCode.NotFound
                )
            }
        }
    }
}
