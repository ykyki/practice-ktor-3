import com.example.pk3.configuration.*
import com.example.pk3.configuration.authentication.AuthenticationGroup
import com.example.pk3.configuration.authentication.AuthenticationRepository
import com.example.pk3.configuration.authentication.AuthenticationRepositoryInMemory
import com.example.pk3.domain.tutorial.customer.CustomerRepository
import com.example.pk3.domain.tutorial.customer.CustomerRepositoryInMemory
import com.example.pk3.rootRouter
import com.typesafe.config.ConfigFactory
import io.ktor.application.*
import io.ktor.config.*
import io.ktor.server.testing.*
import io.ktor.util.*
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
    configureStatusPage()
    configureLogging()
    configureKoin(testRootDiModule)
    configureAuthentication(TestNonceManager)

    rootRouter()
}

private val testRootDiModule = module {
    single<CustomerRepository> { CustomerRepositoryInMemory() }
    single<AuthenticationRepository> { AuthenticationRepositoryInMemory() }
}

private object TestNonceManager : NonceManager {
    private const val TEST_NONCE = "test-nonce-abc12345"

    override suspend fun newNonce(): String {
        return TEST_NONCE
    }

    override suspend fun verifyNonce(nonce: String): Boolean {
        return nonce == TEST_NONCE
    }
}

fun dispatchDigestAuthHeader(
    authenticationGroup: AuthenticationGroup
): String {
    if (authenticationGroup == AuthenticationGroup.DigestSampleA) {
        return """Digest username="user-abc", realm="Sample A Group", nonce="test-nonce-abc12345", uri="/test-digest-a", algorithm=MD5, response="a719c4f342c4ddeb930236cfedc79068""""
    } else {
        TODO("Unsupported")
    }
}
