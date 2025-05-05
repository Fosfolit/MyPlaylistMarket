package com.example.playlistmarket

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.LinkedList



class SearchActivity : AppCompatActivity() {


    private val retrofit = Retrofit.Builder()
        .baseUrl("https://itunes.apple.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create<MusicInterface>()
    private lateinit var sharedPrefs: SharedPreferences
    private var countValue: String = ""
    private lateinit var inputEditText: EditText
    private lateinit var clearButton :ImageView
    private lateinit var recyclerView :RecyclerView
    private lateinit var recentlyViewed: RecentlyViewed
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        sharedPrefs = getSharedPreferences(PRACTICUM_EXAMPLE_PREFERENCES, MODE_PRIVATE)
        clearButton = findViewById(R.id.clearIcon)
        inputEditText = findViewById(R.id.inputEditText)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recentlyViewed = RecentlyViewed(this)
        inputEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                textFind(inputEditText.text.toString())
                true
            }
            false
        }
        inputEditText.setOnFocusChangeListener { view, hasFocus ->
            recyclerView.visibility = View.VISIBLE
            displayRecentlyViewed()
        }

        inputEditTextWatcher()
        buttonClear()
        toolFinish()
    }

    private fun textFind(textFind : String){
        retrofit.getMusic(textFind).enqueue(object : Callback<ListDataMusic> {
            override fun onResponse(call: Call<ListDataMusic>, response: Response<ListDataMusic>) {
                when (response.code()) {
                    200 -> {
                        successfulСall(response)
                        recyclerView.visibility = View.VISIBLE
                    }
                    else->{
                        unsuccessfulСall()
                    }
                }
            }

            override fun onFailure(call: Call<ListDataMusic>, t: Throwable) {
                unsuccessfulСall()
            }
        })
    }

    private  fun successfulСall(response: Response<ListDataMusic>){
        if (response.isSuccessful && (response.body()!!.resultCount >0)) {
            recyclerView.adapter = MusicAdapter(response.body()!!.results) {
                DataMusic ->
                recentlyViewed.addItem(DataMusic)
                viewTrack(DataMusic)
            }
        }
        else{
            recyclerView.adapter = ErrorAdapter(listOf(
                ErrorData(
                    imageError = R.drawable.search_error_notfound,
                    nameError = getString(R.string.notFoundError1),
                    commentError = getString(R.string.notFoundError2),
                    buttonErrorVisibility = 3,
                    buttonErrorText = getString(R.string.notFoundError3)
                ))){}
            recyclerView.visibility = View.VISIBLE
        }
        }

    private  fun unsuccessfulСall(){
        recyclerView.adapter = ErrorAdapter(listOf(
        ErrorData(
            imageError = R.drawable.search_error_internet,
            nameError = getString(R.string.notInternetError1),
            commentError = getString(R.string.notInternetError2),
            buttonErrorVisibility = 1,
            buttonErrorText = getString(R.string.notInternetError3),
        ))){textFind(inputEditText.text.toString())}
        recyclerView.visibility = View.VISIBLE
    }

    private fun inputEditTextWatcher(){
        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if (count==0) {
                    displayRecentlyViewed()
                }
            }

            override fun afterTextChanged(s: Editable?) {
                countValue = inputEditText.text.toString()
                if (countValue.isEmpty()) {
                    displayRecentlyViewed()
                } else {
                    textFind(countValue)
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (count==0) {
                    displayRecentlyViewed()
                }
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
    private fun buttonClear(){
        clearButton.setOnClickListener {
            hideKeyboardAndClearFocus(inputEditText)
            inputEditText.setText("")
            recyclerView.visibility = View.INVISIBLE
        }
    }
    private fun Activity.hideKeyboardAndClearFocus(view: View) {
        view.clearFocus()
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
    private fun displayRecentlyViewed(){
        if(!recentlyViewed.isEmpty()) {
            recyclerView.visibility = View.VISIBLE
            recyclerView.adapter = ConcatAdapter(
                SearchedQueriesTextAdapter(listOf("Вы искали")),
                MusicAdapter(recentlyViewed.dataMusic())
                { DataMusic ->
                    recentlyViewed.addItem(DataMusic)
                    viewTrack(DataMusic)
                },
                SearchedQueriesButtonAdapter(listOf("Очистить историю"))
                {
                    recentlyViewed.clearPreferencesAll()
                    recyclerView.visibility = View.INVISIBLE
                }
            )
        }else{
            recyclerView.visibility = View.INVISIBLE
        }
    }
    private fun viewTrack(dataForSave : DataMusic){
    sharedPrefs.edit()
        .remove("lisneng")
        .putString("lisneng", Gson().toJson(dataForSave))
        .apply()
    val displayIntent = Intent(this, MediaActivity::class.java)
    startActivity(displayIntent)
}
}