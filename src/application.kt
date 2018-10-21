package se.jonsen

import io.ktor.application.*
import io.ktor.routing.*
import com.fasterxml.jackson.databind.*
import com.typesafe.config.ConfigFactory
import io.ktor.config.HoconApplicationConfig
import io.ktor.jackson.*
import io.ktor.features.*
import io.ktor.http.content.resources
import io.ktor.http.content.static
import net.dean.jraw.http.OkHttpNetworkAdapter
import net.dean.jraw.http.UserAgent
import net.dean.jraw.oauth.Credentials
import net.dean.jraw.oauth.OAuthHelper
import org.koin.dsl.module.module
import org.koin.ktor.ext.installKoin
import java.util.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.DevelopmentEngine.main(args)

val redditModule = module {
    val config = HoconApplicationConfig(ConfigFactory.load())
    val clientId = config.property("application.keys.reddit_client_id").getString()
    val secret = config.property("application.keys.reddit_secret").getString()
    val userAgent = UserAgent("web", "se.jonsen.redditprofiler", "v0.1", "vakz")
    val credentials = Credentials.userless(clientId, secret, UUID.randomUUID())
    single { OAuthHelper.automatic(OkHttpNetworkAdapter(userAgent), credentials)  }
}

@Suppress("unused") // Referenced in application.conf
fun Application.module() {
    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }

    installKoin(listOf(redditModule))
    reddit()

    routing {

        // Static feature. Try to access `/static/ktor_logo.svg`
        static("/static") {
            resources("static")
        }
    }
}

