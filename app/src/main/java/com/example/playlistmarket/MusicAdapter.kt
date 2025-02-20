package com.example.playlistmarket

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MusicAdapter(private val news: List<DataMusic>) : RecyclerView.Adapter<MusicViewHolder> () {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.music_view, parent, false)
        return MusicViewHolder(view)
    }

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        holder.bind(news[position])
    }

    override fun getItemCount(): Int {
        return news.size
    }

}
