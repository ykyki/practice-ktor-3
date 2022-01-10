import com.example.pk3.rootModule
import io.ktor.application.*
import io.ktor.server.testing.*

fun withRootModule(testCallBack: TestApplicationEngine.() -> Unit) {
    withTestApplication(Application::rootModule) {
        testCallBack()
    }
}
