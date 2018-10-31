package se.jonsen

import com.github.kittinunf.fuel.Fuel
import com.typesafe.config.ConfigFactory
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.config.HoconApplicationConfig
import io.ktor.http.HttpHeaders
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.routing
import org.koin.dsl.module.module
import org.koin.ktor.ext.inject
import org.koin.ktor.ext.installKoin
import java.lang.Exception

fun Application.reddit() {
    val client = Reddit()
    installKoin(listOf(module { single { client.refreshAccessToken() } }))

    val credentials: RedditAuthenticationConfig by inject()

    routing {
        get("/getDomains/{username}") {
            val username = call.parameters["username"] ?: throw IllegalArgumentException("Username must be specified")
            // Fuel.get("https://oauth.reddit.com/api/user/$username/comments")
            call.respond(username)
        }
    }
}

class Reddit {
    fun refreshAccessToken(): RedditAuthenticationConfig {
        val config = HoconApplicationConfig(ConfigFactory.load());
        val token = config.property("application.keys.reddit_encoded").getString()
        val response = Fuel.post("https://www.reddit.com/api/v1/access_token")
                .header(HttpHeaders.Authorization to "Basic $token")
                .header(HttpHeaders.UserAgent to "web:(test) by /u/vakz profiler/0.1.0")
                .body("grant_type=client_credentials")
                .responseObject(RedditAuthenticationConfigDeserializer())

        val error = response.third.component2()
        if (error != null) {
            throw Exception(error.message)
        }

        return response.third.component1() ?: throw Exception("Failed to get credentials")
    }
}