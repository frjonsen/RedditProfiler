package se.jonsen

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.*

class ApplicationTest {
    @Test
    fun testRoot() {
        withTestApplication(Application::module) {
            with(handleRequest(HttpMethod.Get, "/")) {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("HELLO WORLD!", response.content)
            }
        }
    }
}
