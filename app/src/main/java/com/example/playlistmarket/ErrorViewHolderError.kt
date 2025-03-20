package com.example.playlistmarket

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

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
        nameError.text = error.nameError
        commentError.text = error.commentError
        buttonError.text = error.buttonErrorText
    }
    private fun loadImage(error: ErrorData){
        imageError.setImageResource(error.imageError)
    }
    private fun loadButton(error: ErrorData, retryClickListener: (ErrorData) -> Unit){
        buttonError.text = error.buttonErrorText
        when(error.buttonErrorVisibility){
            0 ->buttonError.visibility = View.INVISIBLE
            1 ->buttonError.visibility = View.VISIBLE
            2 ->buttonError.visibility = View.GONE
            else -> buttonError.visibility = View.GONE
        }
        buttonError.setOnClickListener  {
            retryClickListener(error)
        }
    }

}
