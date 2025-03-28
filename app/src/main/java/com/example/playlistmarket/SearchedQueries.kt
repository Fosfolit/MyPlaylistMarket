package com.example.playlistmarket

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView




class SearchedQueriesTextAdapter(private val news: List<String>) : RecyclerView.Adapter<SearchedQueriesTextViewHolder> () {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):  SearchedQueriesTextViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.text_description_searched_queries, parent, false)
        return  SearchedQueriesTextViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchedQueriesTextViewHolder, position: Int) {
        holder.bind(news[position])
    }

    override fun getItemCount(): Int {
        return news.size
    }

}

class SearchedQueriesTextViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private val SearchedQueriesText: TextView = itemView.findViewById(R.id.SearchedQueriesText)

    fun bind(model: String) {
        SearchedQueriesText.text = model
    }

}

class SearchedQueriesButtonAdapter(private val news: List<String>,private val retryClickListener: (String) -> Unit) : RecyclerView.Adapter<SearchedQueriesButtonViewHolder> () {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):  SearchedQueriesButtonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.button_description_searched_queries, parent, false)
        return  SearchedQueriesButtonViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchedQueriesButtonViewHolder, position: Int) {
        holder.bind(news[position],retryClickListener)
    }

    override fun getItemCount(): Int {
        return news.size
    }

}

class SearchedQueriesButtonViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private val SearchedQueriesButton: Button = itemView.findViewById(R.id.SearchedQueriesButton)

    fun bind(model: String, retryClickListener: (String) -> Unit) {
        SearchedQueriesButton.text = model
        SearchedQueriesButton.setOnClickListener  {
            retryClickListener(model)
        }
    }

}