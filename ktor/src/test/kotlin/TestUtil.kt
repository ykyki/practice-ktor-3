import com.example.pk3.*
import com.example.pk3.domain.tutorial.customer.CustomerRepository
import com.example.pk3.domain.tutorial.customer.CustomerRepositoryInMemory
import io.ktor.application.*
import io.ktor.server.testing.*
import org.koin.dsl.module

fun withTestRootModule(test: TestApplicationEngine.() -> Unit) {
    withTestApplication(Application::testRootModule) {
        test()
    }
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
