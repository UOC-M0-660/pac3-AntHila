package edu.uoc.pac3.data

import android.content.Context
import edu.uoc.pac3.R

/**
 * Created by alex on 06/09/2020.
 */

class SessionManager(context: Context)
{
    val SHARED_NAME= "Mydtb"
    val SHARED_ACCESS_TOKEN = "accessToken"
    val SHARED_REFRESH_TOKEN = "refreshToken"
    val storage = context.getSharedPreferences(SHARED_NAME,0)
     val context = context

    fun isUserAvailable(): Boolean
    {
        // TODO: Implement
        return false
    }

    fun getAccessToken(): String?
    {
        return storage.getString(SHARED_ACCESS_TOKEN, context.getString(R.string.acces_token_error)).toString()
    }

    fun saveAccessToken(accessToken: String)
    {
        storage.edit().putString(SHARED_ACCESS_TOKEN,accessToken).apply()
    }

    fun clearAccessToken()
    {
        storage.edit().remove(SHARED_ACCESS_TOKEN).apply()
    }

    fun getRefreshToken(): String?
    {
        return storage.getString(SHARED_REFRESH_TOKEN, context.getString(R.string.acces_token_error)).toString()
    }

    fun saveRefreshToken(refreshToken: String)
    {
        storage.edit().putString(SHARED_REFRESH_TOKEN,refreshToken).apply()
    }

    fun clearRefreshToken()
    {
        storage.edit().remove(SHARED_REFRESH_TOKEN).apply()
    }

}