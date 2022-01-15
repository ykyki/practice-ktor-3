import com.example.pk3.configuration.configurLogging
import com.example.pk3.configuration.configureContentNegotiation
import com.example.pk3.configuration.configureKoin
import com.example.pk3.configuration.configureStatusPages
import com.example.pk3.domain.tutorial.customer.CustomerRepository
import com.example.pk3.domain.tutorial.customer.CustomerRepositoryInMemory
import com.example.pk3.rootRouter
import com.typesafe.config.ConfigFactory
import io.ktor.application.*
import io.ktor.config.*
import io.ktor.server.testing.*
import org.koin.dsl.module

fun withTestRootModule(test: TestApplicationEngine.() -> Unit) =
    withApplication(
        createTestEnvironment {
            config = HoconApplicationConfig(ConfigFactory.load("application-test.conf"))
        }
    ) {
        application.testRootModule()
        test()
    }

private fun Application.testRootModule() {
    configureContentNegotiation()
    configureStatusPages()
    configurLogging()
    configureKoin(testRootDiModule)

    rootRouter()
}

private val testRootDiModule = module {
    single<CustomerRepository> { CustomerRepositoryInMemory() }
}
