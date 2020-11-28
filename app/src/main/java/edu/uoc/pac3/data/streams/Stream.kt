package edu.uoc.pac3.data.streams

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Stream
(
    @SerialName("user_name") val user_name: String? = null,
    @SerialName("title") val title: String? = null,
    @SerialName("thumbnail_url") val thumbnail_url: String? =null
)

@Serializable
data class StreamsResponse
(
        @SerialName("data") val data: List<Stream>? = null,
)