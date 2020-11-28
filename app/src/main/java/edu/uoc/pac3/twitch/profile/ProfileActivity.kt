package edu.uoc.pac3.twitch.profile

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.coroutineScope
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText
import edu.uoc.pac3.R
import edu.uoc.pac3.data.SessionManager
import edu.uoc.pac3.data.TwitchApiService
import edu.uoc.pac3.data.network.Network
import edu.uoc.pac3.data.user.User
import edu.uoc.pac3.data.user.UsersResponse
import kotlinx.coroutines.launch

class ProfileActivity : AppCompatActivity()
{

    private val TAG = "ProfileActivity"

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        showUserInfo()
    }

    private fun showUserInfo()
    {
        // Create Twitch Service
        val twitchApiService = TwitchApiService(Network.createHttpClient(this))

        // Create a coroutine for get the Streams from Twitch
        lifecycle.coroutineScope.launch{

            // Create SessionManager
            val sessionManager = SessionManager(this@ProfileActivity)

            //Obtain accessToken from SharedPreferences
            val accessToken = sessionManager.getAccessToken()

            Log.i(TAG,"antes de pedir el user")

            // Get User from Twitch
            var user : UsersResponse? = twitchApiService.getUsers(accessToken)

            Log.i(TAG,"despues de pedir el user")

            Log.i(TAG,user.toString())

            //Update UI
            user?.let { it.data?.get(0)?.let { it1 -> setUserInformation(it1) } }
        }

    }

    fun setUserInformation(user: User)
    {
        val display_name: TextView = findViewById(R.id.display_name)
        val description: TextInputEditText = findViewById(R.id.description)
        val profile_image_url: ImageView = findViewById(R.id.profile_image_url)

        display_name.text = user.display_name
        description.setText(user.description)

        Glide
                .with(this)
                .load(user.profile_image_url)
                .fitCenter()
                .into(profile_image_url)
    }

}
