package se.jonsen

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.routing
import net.dean.jraw.RedditClient
import org.koin.ktor.ext.inject

fun Application.reddit() {
    val client: RedditClient by inject()

    routing {
        get("/getDomains/{username}") {
            val username = call.parameters["username"] ?: throw IllegalArgumentException("Username must be specified")
            val interesting = client.user(username).history("").limit(5).build()
            val comment = interesting.next().first().body!!
            call.respond(comment)
        }
    }

}