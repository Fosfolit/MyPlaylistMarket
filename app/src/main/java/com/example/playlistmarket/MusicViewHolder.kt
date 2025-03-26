package com.example.playlistmarket

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import java.text.SimpleDateFormat
import java.util.Locale


class MusicViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private val trackName: TextView = itemView.findViewById(R.id.trackName)
    private val trackTime: TextView = itemView.findViewById(R.id.trackTime)
    private val artistName: TextView = itemView.findViewById(R.id.artistName)
    private val artworkUrl100: ImageView = itemView.findViewById(R.id.artworkUrl100)

    fun bind(model: DataMusic) {
        trackName.text = model.trackName
        trackTime.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(model.trackTime)
        artistName.text = model.artistName
        Glide.with(itemView)
            .load(model.artworkUrl100)
            .placeholder(R.drawable.music_base)
            .centerCrop()
            .transform(RoundedCorners(16))
            .into(artworkUrl100)
    }

}
