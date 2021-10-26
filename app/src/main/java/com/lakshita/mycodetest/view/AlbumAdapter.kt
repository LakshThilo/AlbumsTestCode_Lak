package com.lakshita.mycodetest.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lakshita.mycodetest.R
import com.lakshita.mycodetest.model.Album

class AlbumAdapter : RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>() {

    var albums = listOf<Album>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    class AlbumViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val userId: TextView = view.findViewById(R.id.user_id_text)
        val albumId: TextView = view.findViewById(R.id.album_id_text)
        val title: TextView = view.findViewById(R.id.album_title_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val layout = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.album_view, parent, false)

        return AlbumViewHolder(layout)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.albumId.text = albums[position].id.toString()
        holder.userId.text = albums[position].userId.toString()
        holder.title.text = albums[position].title
    }

    override fun getItemCount(): Int = albums.size


}
