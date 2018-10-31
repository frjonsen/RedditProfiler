package se.jonsen

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.kittinunf.fuel.core.ResponseDeserializable

data class RedditAuthenticationConfig(@JsonProperty("access_token") val accessToken: String, @JsonProperty("token_type") val tokenType: String, @JsonProperty("expires_in") val expiresIn: Int, @JsonProperty("scope") val scope: String)

class RedditAuthenticationConfigDeserializer : ResponseDeserializable<RedditAuthenticationConfig> {
    override fun deserialize(content: String) =
        jacksonObjectMapper().readValue<RedditAuthenticationConfig>(content)
}