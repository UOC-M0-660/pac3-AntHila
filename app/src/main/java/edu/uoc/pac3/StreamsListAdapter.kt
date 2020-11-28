package edu.uoc.pac3

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import edu.uoc.pac3.data.streams.Stream

class StreamsListAdapter(val context: Context , private var streams: List<Stream>) : RecyclerView.Adapter<StreamsListAdapter.ViewHolder>()
{

    private fun getStream(position: Int): Stream
    {
        return streams[position]
    }

    fun setStreams(streams: List<Stream>)
    {
        this.streams = streams

        // Reloads the RecyclerView with new adapter data
        notifyDataSetChanged()
    }

    // Creates View Holder for re-use
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {

        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.stream_list_row, parent, false)

        return ViewHolder(view)
    }

    // Binds re-usable View for a given position
    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        val stream = getStream(position)
        holder.titleView.text = stream.title
        holder.userNameView.text = stream.user_name

        val urlTwitchAdapter : URLTwitchAdapter = URLTwitchAdapter()
        val thumbnailUrl = urlTwitchAdapter.adaptURL(stream.thumbnail_url)

        Glide
             .with(context)
             .load(thumbnailUrl)
             .fitCenter()
             .into(holder.thumbnailView)

        //Set View Click Listener
        holder.itemView.setOnClickListener{ view ->
            Toast.makeText(context,R.string.TODO , Toast.LENGTH_SHORT).show()}
    }

    // Returns total items in Adapter
    override fun getItemCount(): Int
    {
        return streams.size
    }

    // Holds an instance to the view for re-use
    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)
    {
        val titleView: TextView = view.findViewById(R.id.title)
        val userNameView: TextView = view.findViewById(R.id.user_name)
        val thumbnailView: ImageView = view.findViewById(R.id.thumbnail_image)
    }

}