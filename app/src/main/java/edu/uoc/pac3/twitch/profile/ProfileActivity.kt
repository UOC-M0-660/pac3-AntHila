package edu.uoc.pac3.twitch.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.coroutineScope
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText
import edu.uoc.pac3.LaunchActivity
import edu.uoc.pac3.R
import edu.uoc.pac3.data.SessionManager
import edu.uoc.pac3.data.TwitchApiService
import edu.uoc.pac3.data.network.Network
import edu.uoc.pac3.data.oauth.OAuthConstants
import edu.uoc.pac3.data.user.User
import edu.uoc.pac3.data.user.UsersResponse
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.coroutines.launch

class ProfileActivity : AppCompatActivity()
{

    private val TAG = "ProfileActivity"

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        Log.i(TAG, OAuthConstants.scopes)

        //SetOnClickListeners for all the UI components
        setOnCLickListeners()

        showUserInfo()
    }

    fun setOnCLickListeners()
    {
        //Update Description Button
        updateDescriptionButton.setOnClickListener{
            val description: TextInputEditText = findViewById(R.id.description)
            updateUserDescription(description.text.toString())
        }

        //Log Out Button
        logOutButton.setOnClickListener{
            logOut()
        }
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

            // Get User from Twitch
            var user : UsersResponse? = twitchApiService.getUsers(accessToken)

            //Update UI
            user?.let { it.data?.get(0)?.let { it1 -> setUserInformation(it1) } }
        }

    }

    fun setUserInformation(user: User)
    {
        //Localize the UI elements
        val display_name: TextView = findViewById(R.id.display_name)
        val description: TextInputEditText = findViewById(R.id.description)
        val profile_image_url: ImageView = findViewById(R.id.profile_image_url)

        //Place the user information on the UI elements
        display_name.text = user.display_name
        description.setText(user.description)
        Glide
                .with(this)
                .load(user.profile_image_url)
                .fitCenter()
                .into(profile_image_url)
    }

    fun updateUserDescription(description:String)
    {
        // Create Twitch Service
        val twitchApiService = TwitchApiService(Network.createHttpClient(this))

        // Create a coroutine for get the Streams from Twitch
        lifecycle.coroutineScope.launch{

            // Create SessionManager
            val sessionManager = SessionManager(this@ProfileActivity)

            //Obtain accessToken from SharedPreferences
            val accessToken = sessionManager.getAccessToken()

            // Get User from Twitch
            var user : UsersResponse? = twitchApiService.updateUserDescription(accessToken,description)
        }
    }

    fun logOut()
    {
        eraseTokens()
        launchLaunchActivity()
    }

    fun eraseTokens()
    {
        // Create SessionManager
        val sessionManager = SessionManager(this)

        //Obtain accessToken from SharedPreferences
        sessionManager.clearAccessToken()
        sessionManager.clearRefreshToken()
    }

    fun launchLaunchActivity()
    {
        finish()
        startActivity(Intent(this, LaunchActivity::class.java))
    }

}
