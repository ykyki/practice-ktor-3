import com.example.pk3.*
import com.example.pk3.domain.tutorial.customer.CustomerRepository
import com.example.pk3.domain.tutorial.customer.CustomerRepositoryInMemory
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
    configureRootContentNegotiation()
    configureRootStatusPages()
    configureRootLogging()
    configureRootKoin(testRootDiModule)

    rootRouter()
}

private val testRootDiModule = module {
    single<CustomerRepository> { CustomerRepositoryInMemory() }
}
