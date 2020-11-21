package edu.uoc.pac3.data

import android.content.Context
import android.util.Log
import edu.uoc.pac3.data.network.Network
import edu.uoc.pac3.data.network.Network.createHttpClient
import edu.uoc.pac3.data.oauth.OAuthAccessTokenResponse
import edu.uoc.pac3.data.oauth.OAuthConstants.clientID
import edu.uoc.pac3.data.oauth.OAuthConstants.clientSecret
import edu.uoc.pac3.data.oauth.OAuthConstants.redirectUri
import edu.uoc.pac3.data.oauth.UnauthorizedException
import edu.uoc.pac3.data.streams.StreamsResponse
import edu.uoc.pac3.data.user.User
import edu.uoc.pac3.oauth.LoginActivity
import edu.uoc.pac3.oauth.OAuthActivity
import io.ktor.client.*
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import java.security.AccessController.getContext
import kotlin.jvm.Throws

/**
 * Created by alex on 24/10/2020.
 */

class TwitchApiService(private val httpClient: HttpClient)
{
    private val TAG = "TwitchApiService"

    /// Gets Access and Refresh Tokens on Twitch
    suspend fun getTokens(authorizationCode: String): OAuthAccessTokenResponse?
    {
        // Get Tokens from Twitch"
        val response = httpClient.post<OAuthAccessTokenResponse>("https://id.twitch.tv/oauth2/token")
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
    suspend fun getStreams(cursor: String? = null): StreamsResponse?
    {
        TODO("Get Streams from Twitch")
        TODO("Support Pagination")
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