package edu.uoc.pac3.oauth

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.coroutineScope
import edu.uoc.pac3.R
import edu.uoc.pac3.data.SessionManager
import edu.uoc.pac3.data.TwitchApiService
import edu.uoc.pac3.data.network.Network.createHttpClient
import edu.uoc.pac3.data.oauth.OAuthConstants.authorizationUrl
import edu.uoc.pac3.data.oauth.OAuthConstants.clientID
import edu.uoc.pac3.data.oauth.OAuthConstants.redirectUri
import kotlinx.android.synthetic.main.activity_oauth.*
import kotlinx.coroutines.launch
import java.util.*
import edu.uoc.pac3.data.oauth.OAuthAccessTokenResponse

class OAuthActivity : AppCompatActivity()
{

    private val TAG = "OAuthActivity"

    private val uniqueState = UUID.randomUUID().toString()
    lateinit var tokens : OAuthAccessTokenResponse


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_oauth)
        launchOAuthAuthorization()
    }

    fun buildOAuthUri(): Uri
    {
        // Prepare URL
        val uri = Uri.parse(authorizationUrl)
            .buildUpon()
            .appendQueryParameter("client_id", clientID)
            .appendQueryParameter("redirect_uri", redirectUri)
            .appendQueryParameter("response_type", "code")
            //.appendQueryParameter("scope", scopes.joinToString(separator = " "))
            .appendQueryParameter("state", uniqueState)
            .build()

        return uri
    }

    private fun launchOAuthAuthorization()
    {
        //  Create URI
        val uri = buildOAuthUri()

        // Set Redirect Listener
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                request?.let {
                    // Check if this url is our OAuth redirect, otherwise ignore it
                    if (request.url.toString().startsWith(redirectUri)) {
                        // To prevent CSRF attacks, check that we got the same state value we sent, otherwise ignore it
                        val responseState = request.url.getQueryParameter("state")
                        if (responseState == uniqueState) {
                            // This is our request, obtain the code!
                            request.url.getQueryParameter("code")?.let { code ->
                                // Got it!
                                Log.d("OAuth", "Here is the authorization code! $code")
                                onAuthorizationCodeRetrieved(code)
                            } ?: run {
                                // User cancelled the login flow
                                // TODO: Handle error
                            }
                        }
                    }
                }
                return super.shouldOverrideUrlLoading(view, request)
            }
        }

        // Load OAuth Uri
        webView.settings.javaScriptEnabled = true
        webView.loadUrl(uri.toString())
    }

    // Call this method after obtaining the authorization code
    // on the WebView to obtain the tokens
    private fun onAuthorizationCodeRetrieved(authorizationCode: String)
    {
        // Show Loading Indicator
        progressBar.visibility = View.VISIBLE

        // Create Twitch Service
        val twitchApiService = TwitchApiService(createHttpClient(this))

        // Create SessionManager
        val sessionManager = SessionManager(this)

        // Create a coroutine for get the tokens from Twitch and save them with SessionManager
        lifecycle.coroutineScope.launch{
            // Get Tokens from Twitch
            tokens = twitchApiService.getTokens(authorizationCode)!!

            // Save access token
            sessionManager.saveAccessToken(tokens.accessToken)
            Log.i(TAG,"El acces per guardar es: "+ tokens.accessToken)

            // Save refresh token
            tokens.refreshToken?.let { sessionManager.saveRefreshToken(it)
                Log.i(TAG,"El refresh per guardar es: "+ tokens.refreshToken)}}
    }

}