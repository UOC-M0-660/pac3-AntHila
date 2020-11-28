package edu.uoc.pac3.data

import android.util.Log
import edu.uoc.pac3.data.oauth.OAuthAccessTokenResponse
import edu.uoc.pac3.data.oauth.OAuthConstants.clientID
import edu.uoc.pac3.data.oauth.OAuthConstants.clientSecret
import edu.uoc.pac3.data.oauth.OAuthConstants.redirectUri
import edu.uoc.pac3.data.oauth.OAuthConstants.scopes
import edu.uoc.pac3.data.oauth.UnauthorizedException
import edu.uoc.pac3.data.streams.StreamsResponse
import edu.uoc.pac3.data.user.User
import edu.uoc.pac3.data.user.UsersResponse
import io.ktor.client.*
import io.ktor.client.request.*
import kotlin.jvm.Throws

/**
 * Created by alex on 24/10/2020.
 */

class TwitchApiService(private val httpClient: HttpClient)
{
    private val TAG = "TwitchApiService"
    private val API_URL_TOKENS = "https://id.twitch.tv/oauth2/token"
    private val API_URL_STREAMS = "https://api.twitch.tv/helix/streams"
    private val API_URL_USER = "https://api.twitch.tv/helix/users"
    private val API_URL_UPDATE_USER_DESCRIPTION = "https://api.twitch.tv/helix/users?description="

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
            parameter("scope",scopes)
        }

        Log.d(TAG, "Access Token: ${response.accessToken}. Refresh Token: ${response.refreshToken}")

        return response
    }

    /// Gets Streams on Twitch
    @Throws(UnauthorizedException::class)
    suspend fun getStreams(accessToken : String?, cursor: String? = null): StreamsResponse?
    {
        // Get Streams from Twitch
        val response = httpClient.get<StreamsResponse>(API_URL_STREAMS)
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
    suspend fun getUsers(accessToken : String?): UsersResponse?
    {
        val response = httpClient.get<UsersResponse>(API_URL_USER)
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
    suspend fun updateUserDescription(accessToken : String?, description: String): UsersResponse?
    {
        val response = httpClient.put<UsersResponse>(API_URL_UPDATE_USER_DESCRIPTION + description)
        {

            headers{
                append("Client-Id", clientID)
                append("Authorization", "Bearer $accessToken")
            }
        }

        return  response

    }
}