package com.example.playlistmarket

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class SearchActivity : AppCompatActivity() {


    private val retrofit = Retrofit.Builder()
        .baseUrl("https://itunes.apple.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create<MusicInterface>()


    private var countValue: String = ""
    private lateinit var inputEditText: EditText
    private lateinit var clearButton :ImageView
    private lateinit var recyclerView :RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        clearButton = findViewById<ImageView>(R.id.clearIcon)
        inputEditText = findViewById(R.id.inputEditText)
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)

        Textsee()

        buttonclear()
        toolFinish()
    }

    private fun Textne(inputtrext : String){
        retrofit.getMusic(inputtrext).enqueue(object : Callback<ListDataMusic> {
            override fun onResponse(call: Call<ListDataMusic>, response: Response<ListDataMusic>) {
                if (response.isSuccessful && (response.body()!!.resultCount >0)) {
                    val newsAdapter = MusicAdapter(response.body()!!.results)
                    recyclerView.adapter = newsAdapter
                } else {
                    val newsAdapter = ErrorAdapter(listOf(
                        ErrorData(
                            imageError = R.drawable.search_error_notfound,
                            nameError = "Ничего не найдено",
                            commentError = "",
                            buttonErrorVisibility = 3,
                            buttonErrorText ="",
                        )))
                    recyclerView.adapter = newsAdapter
                }
            }

            override fun onFailure(call: Call<ListDataMusic>, t: Throwable) {
                val newsAdapter = ErrorAdapter(listOf(
                    ErrorData(
                        imageError = R.drawable.search_error_notfound,
                        nameError = "Проблемы со связью",
                        commentError = "Загрузка не удалась. Проверьте подключение к интернету",
                        buttonErrorVisibility = 1,
                        buttonErrorText ="Обновить",
                    )))
                recyclerView.adapter = newsAdapter

            }
        })
    }



    private fun Textsee(){
        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                countValue =  inputEditText.text.toString()
                Textne(countValue)
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                clearButton.visibility = clearButtonVisibility(s)
            }
        }
        inputEditText.addTextChangedListener(simpleTextWatcher)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("codrush", countValue)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        countValue = savedInstanceState.getString("codrush","")
        inputEditText.setText(countValue)
    }

    private fun clearButtonVisibility(s: CharSequence?): Int {
        return if (s.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    private fun toolFinish(){
        val toolbar: Toolbar = findViewById(R.id.buttonBack1)
        toolbar.setOnClickListener {
            finish()
        }
    }

    private fun buttonclear(){
        clearButton.setOnClickListener {
            hideKeyboardAndClearFocus(inputEditText)
            inputEditText.setText("")
        }
    }
    private fun Activity.hideKeyboardAndClearFocus(view: View) {
        view.clearFocus()
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

}