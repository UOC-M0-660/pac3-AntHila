package edu.uoc.pac3.twitch.streams

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.uoc.pac3.R
import edu.uoc.pac3.StreamsListAdapter
import edu.uoc.pac3.data.SessionManager
import edu.uoc.pac3.data.TwitchApiService
import edu.uoc.pac3.data.network.Network
import edu.uoc.pac3.data.streams.Stream
import edu.uoc.pac3.data.streams.StreamsResponse
import kotlinx.coroutines.launch

class StreamsActivity : AppCompatActivity()
{

    private val TAG = "StreamsActivity"
    private lateinit var adapter: StreamsListAdapter

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_streams)

        // Init RecyclerView
        initRecyclerView()

        //Default streams for debug
        showDefaultStreams()

        //Streams from Twitch API
        showTwitchStreams()

    }

    // Init RecyclerView
    private fun initRecyclerView()
    {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        // Set Layout Manager
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        // Init Adapter
        adapter = StreamsListAdapter(this,emptyList())
        recyclerView.adapter = adapter
        Log.i(TAG,"Se acaba initRecyclerView")
    }

    private fun showTwitchStreams()
    {
        Log.i(TAG,"Empieza showTwitchStreams")

        // Create Twitch Service
        val twitchApiService = TwitchApiService(Network.createHttpClient(this))

        // Create a coroutine for get the Streams from Twitch
        lifecycle.coroutineScope.launch{

            Log.i(TAG,"Hemos entrado en la coroutine de showTwitchStreams")

            // Create SessionManager
            val sessionManager = SessionManager(this@StreamsActivity)

            //Obtain accessToken from SharedPreferences
            val accessToken = sessionManager.getAccessToken()

            // Get Streams from Twitch
            var streamsTwitchList : StreamsResponse? = twitchApiService.getStreams(accessToken,null)

            //Update UI
            streamsTwitchList?.data?.let { adapter.setStreams(it) }

        }

    }

    private fun showDefaultStreams()
    {
        //Defaults Streams for debug purposes
        val defaultStreamsList : MutableList<Stream> = createDefaultStreamsList()
        adapter.setStreams(defaultStreamsList)
    }

    private fun createDefaultStreamsList():MutableList<Stream>
    {
        var list : MutableList<Stream> = mutableListOf<Stream>()

        val s1 = Stream("yo","El mas guapiro","https://www.muycomputer.com/wp-content/uploads/2020/06/twitch.jpg")
        list.add(s1)

        val s2= Stream("el perico","amigirro","https://blog.twitch.tv/assets/uploads/twitch-generic-email-1-1-1-1.jpg")
        list.add(s2)
        Log.i(TAG,"Se acaba defaultStreams")

        return list
    }



}