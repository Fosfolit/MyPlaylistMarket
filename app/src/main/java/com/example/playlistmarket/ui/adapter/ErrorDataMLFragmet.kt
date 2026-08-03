package com.example.playlistmarket.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmarket.R
import androidx.annotation.StringRes

data class ErrorDataMLFragment(
    val imageError: Int,
    val nameError: Int,
    val buttonErrorVisibility: ButtonVisibility,
    @StringRes  val buttonErrorText: Int,
)


class ErrorAdapterMLFragment(private val news: List<ErrorDataMLFragment>, private val retryClickListener: (ErrorDataMLFragment) -> Unit) : RecyclerView.Adapter<ErrorViewHolderMLFragment> () {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ErrorViewHolderMLFragment {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ml_error_notfound, parent, false)
        return ErrorViewHolderMLFragment(view)
    }
    override fun onBindViewHolder(holder: ErrorViewHolderMLFragment, position: Int) {
        holder.bind(news[position],retryClickListener)
    }
    override fun getItemCount(): Int {
        return news.size
    }
}
class ErrorViewHolderMLFragment(itemView: View): RecyclerView.ViewHolder(itemView) {

    private val imageError: ImageView = itemView.findViewById(R.id.imageError)
    private val nameError: TextView = itemView.findViewById(R.id.nameError)
    private val buttonError: Button = itemView.findViewById(R.id.buttonError)

    fun bind(errorCode: ErrorDataMLFragment, retryClickListener: (ErrorDataMLFragment) -> Unit) {
        loadImage(errorCode)
        loadText(errorCode)
        loadButton(errorCode, retryClickListener)
    }

    private fun loadText(error: ErrorDataMLFragment){
        nameError.setText(error.nameError)
        buttonError.setText(error.buttonErrorText)
    }

    private fun loadImage(error: ErrorDataMLFragment){
        imageError.setImageResource(error.imageError)
    }

    private fun loadButton(error: ErrorDataMLFragment, retryClickListener: (ErrorDataMLFragment) -> Unit){
        buttonError.setText(error.buttonErrorText)
        when(error.buttonErrorVisibility){
            ButtonVisibility.INVISIBLE -> buttonError.visibility =View.INVISIBLE
            ButtonVisibility.VISIBLE -> buttonError.visibility =View.VISIBLE
            ButtonVisibility.GONE -> buttonError.visibility =View.GONE
        }
        buttonError.setOnClickListener  {
            retryClickListener(error)
        }
    }

}




