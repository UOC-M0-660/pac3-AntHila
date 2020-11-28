package edu.uoc.pac3.data.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User
(
        @SerialName("display_name") val display_name: String? = null,
        @SerialName("description") val description: String? = null,
        @SerialName("profile_image_url") val profile_image_url: String? = null
)


@Serializable
data class UsersResponse
(
        @SerialName("data") val data: List<User>? = null,
)