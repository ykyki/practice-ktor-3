package com.example.pk3.configuration

import com.example.pk3.domain.tutorial.customer.CustomerRepository
import com.example.pk3.domain.tutorial.customer.CustomerRepositoryInMemory
import io.ktor.application.*
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.ktor.ext.Koin
import org.koin.logger.SLF4JLogger

fun Application.configureKoin(koinModule: Module) {
    install(Koin) {
        SLF4JLogger()
        modules(koinModule)
    }
}

val rootDiModule = module {
    single<CustomerRepository> { CustomerRepositoryInMemory() }
}
