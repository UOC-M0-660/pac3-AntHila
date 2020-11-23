package edu.uoc.pac3

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import edu.uoc.pac3.data.streams.Stream
import edu.uoc.pac3.twitch.streams.StreamsActivity
import kotlin.coroutines.coroutineContext

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
        /*val view: View = when (viewType)
        {
            evenViewType -> {LayoutInflater.from(parent.context).inflate(R.layout.row_book_list_content_even, parent, false)}
            oddViewType -> {LayoutInflater.from(parent.context).inflate(R.layout.row_book_list_content_odd, parent, false)}
            else ->
            {
                throw IllegalStateException("Unsupported viewType $viewType")
            }
        }*/
        return ViewHolder(view)
    }

    // Binds re-usable View for a given position
    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        val stream = getStream(position)
        holder.titleView.text = stream.title
        holder.userNameView.text = stream.userName

        Glide
             .with(context)
             .load(stream.thumbnail_url)
             .fitCenter()
             .into(holder.thumbnailView)

        /*// Set View Click Listener
        holder.itemView.setOnClickListener{ view ->
            val intent = Intent(view.context, BookDetailActivity::class.java).apply{
                putExtra(BookDetailFragment.ARG_ITEM_ID, book.uid)}

            view.context.startActivity(intent)}*/
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