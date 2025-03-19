package com.example.playlistmarket

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ErrorAdapter(private val news: List<ErrorData>,private val retryClickListener: (ErrorData) -> Unit) : RecyclerView.Adapter<ErrorViewHolderError> () {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ErrorViewHolderError {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_error_notfound, parent, false)
        return ErrorViewHolderError(view)
    }
    override fun onBindViewHolder(holder: ErrorViewHolderError, position: Int) {
        holder.bind(news[position],retryClickListener)
    }
    override fun getItemCount(): Int {
        return news.size
    }
}
