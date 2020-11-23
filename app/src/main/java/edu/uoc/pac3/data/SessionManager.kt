package edu.uoc.pac3.data

import android.content.Context
import edu.uoc.pac3.R
import io.ktor.client.request.HttpRequestBuilder

/**
 * Created by alex on 06/09/2020.
 */

class SessionManager(context:Context)
{
    val SHARED_NAME= "SharedPreferences"
    val SHARED_ACCESS_TOKEN = "accessToken"
    val SHARED_REFRESH_TOKEN = "refreshToken"
    val SHARED_DEFAULT = ""
    val storage = context.getSharedPreferences(SHARED_NAME,0)
    val context = context

    fun isUserAvailable(): Boolean
    {
        var result :Boolean = true

        if(getAccessToken() != SHARED_DEFAULT)
        {
            result = true
        }

        return result
    }

    fun getAccessToken(): String?
    {
        return storage.getString(SHARED_ACCESS_TOKEN, SHARED_DEFAULT)
    }

    fun saveAccessToken(accessToken: String)
    {
        storage.edit().putString(SHARED_ACCESS_TOKEN,accessToken).apply()
    }

    fun clearAccessToken()
    {
        storage.edit().putString(SHARED_ACCESS_TOKEN,SHARED_DEFAULT).apply()
    }

    fun getRefreshToken(): String?
    {
        return storage.getString(SHARED_REFRESH_TOKEN, SHARED_DEFAULT)
    }

    fun saveRefreshToken(refreshToken: String)
    {
        storage.edit().putString(SHARED_REFRESH_TOKEN,refreshToken).apply()
    }

    fun clearRefreshToken()
    {
        storage.edit().putString(SHARED_REFRESH_TOKEN,SHARED_DEFAULT).apply()
    }

}