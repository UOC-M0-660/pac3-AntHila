package edu.uoc.pac3.data

import android.util.Log
import edu.uoc.pac3.TwitchStreamsResponse
import edu.uoc.pac3.data.oauth.OAuthAccessTokenResponse
import edu.uoc.pac3.data.oauth.OAuthConstants.clientID
import edu.uoc.pac3.data.oauth.OAuthConstants.clientSecret
import edu.uoc.pac3.data.oauth.OAuthConstants.redirectUri
import edu.uoc.pac3.data.oauth.UnauthorizedException
import edu.uoc.pac3.data.streams.StreamsResponse
import edu.uoc.pac3.data.user.User
import io.ktor.client.*
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import kotlin.jvm.Throws

/**
 * Created by alex on 24/10/2020.
 */

class TwitchApiService(private val httpClient: HttpClient)
{
    private val TAG = "TwitchApiService"
    private val API_URL_TOKENS = "https://id.twitch.tv/oauth2/token"
    private val API_URL_STREAMS = "https://api.twitch.tv/helix/streams"

    /// Gets Access and Refresh Tokens on Twitch
    suspend fun getTokens(authorizationCode: String): OAuthAccessTokenResponse?
    {
        // Get Tokens from Twitch"
        val response = httpClient.post<OAuthAccessTokenResponse>(API_URL_TOKENS)
        {
            parameter("client_id", clientID)
            parameter("client_secret", clientSecret)
            parameter("code", authorizationCode)
            parameter("grant_type", "authorization_code")
            parameter("redirect_uri", redirectUri)
        }

        Log.d(TAG, "Access Token: ${response.accessToken}. Refresh Token: ${response.refreshToken}")

        return response
    }

    /// Gets Streams on Twitch
    @Throws(UnauthorizedException::class)
    suspend fun getStreams(accessToken : String?, cursor: String? = null): StreamsResponse?
    {
        Log.i(TAG,"Hemos entrado en getStreams")

        // Get Streams from Twitch
        val response = httpClient.get<StreamsResponse>("https://api.twitch.tv/helix/streams")
        {

            headers{
                append("Client-Id", clientID)
                append("Authorization", "Bearer $accessToken")
            }
        }

        return  response
    }

    /// Gets Current Authorized User on Twitch
    @Throws(UnauthorizedException::class)
    suspend fun getUser(): User?
    {
        TODO("Get User from Twitch")
    }

    /// Gets Current Authorized User on Twitch
    @Throws(UnauthorizedException::class)
    suspend fun updateUserDescription(description: String): User?
    {
        TODO("Update User Description on Twitch")
    }
}