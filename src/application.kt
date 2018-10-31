package se.jonsen

import io.ktor.application.*
import io.ktor.routing.*
import com.fasterxml.jackson.databind.*
import io.ktor.jackson.*
import io.ktor.features.*
import io.ktor.http.content.resources
import io.ktor.http.content.static
import io.ktor.response.respondText

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }

    reddit()

    routing {

        get("/") {
            call.respondText("HELLO WORLD!")
        }

        // Static feature. Try to access `/static/ktor_logo.svg`
        static("/static") {
            resources("static")
        }
    }
}

