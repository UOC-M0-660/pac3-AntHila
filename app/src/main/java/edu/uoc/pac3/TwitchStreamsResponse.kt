package edu.uoc.pac3

import edu.uoc.pac3.data.streams.Stream
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


//@Serializable
data class TwitchStreamsResponse
(
        @SerialName("data") val data: List<Stream>? = null,
        /*@SerialName("user_name") val userName: String,
        @SerialName("title") val title: String? = null,
        @SerialName("thumbnail_url") val thumbnailImage: String? = null,*/
)