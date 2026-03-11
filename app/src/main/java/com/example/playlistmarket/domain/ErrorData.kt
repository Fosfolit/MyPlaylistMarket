package com.example.playlistmarket.domain

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmarket.R


data class ErrorData(
    val imageError: Int,
    @StringRes val nameError: Int,
    @StringRes val commentError: Int,
    val buttonErrorVisibility: ButtonVisibility,
    @StringRes  val buttonErrorText: Int,
)
enum class ButtonVisibility {
    INVISIBLE,  // 0
    VISIBLE,    // 1
    GONE        // 2
}

class ErrorAdapter(private val news: List<ErrorData>, private val retryClickListener: (ErrorData) -> Unit) : RecyclerView.Adapter<ErrorViewHolderError> () {

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
class ErrorViewHolderError(itemView: View): RecyclerView.ViewHolder(itemView) {

    private val imageError: ImageView = itemView.findViewById(R.id.imageError)
    private val nameError: TextView = itemView.findViewById(R.id.nameError)
    private val commentError: TextView = itemView.findViewById(R.id.commentError)
    private val buttonError: Button = itemView.findViewById(R.id.buttonError)

    fun bind(errorCode: ErrorData, retryClickListener: (ErrorData) -> Unit) {
        loadImage(errorCode)
        loadText(errorCode)
        loadButton(errorCode, retryClickListener)
    }

    private fun loadText(error: ErrorData){
        nameError.setText(error.nameError)
        commentError.setText(error.commentError)
        buttonError.setText(error.buttonErrorText)
    }

    private fun loadImage(error: ErrorData){
        imageError.setImageResource(error.imageError)
    }

    private fun loadButton(error: ErrorData, retryClickListener: (ErrorData) -> Unit){
        buttonError.setText(error.buttonErrorText)
        when(error.buttonErrorVisibility){
            ButtonVisibility.INVISIBLE -> View.INVISIBLE
            ButtonVisibility.VISIBLE -> View.VISIBLE
            ButtonVisibility.GONE -> View.GONE
            else -> buttonError.visibility = View.GONE
        }
        buttonError.setOnClickListener  {
            retryClickListener(error)
        }
    }
}
