package com.example.playlistmarket

import android.app.Activity
import android.content.Context
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
    public val linkedList : LinkedList<DataMusic> = LinkedList()
    private lateinit var sharedPrefs: SharedPreferences
    private var countValue: String = ""
    private lateinit var inputEditText: EditText
    private lateinit var clearButton :ImageView
    private lateinit var recyclerView :RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        sharedPrefs = getSharedPreferences(PRACTICUM_EXAMPLE_PREFERENCES, MODE_PRIVATE)
        clearButton = findViewById(R.id.clearIcon)
        inputEditText = findViewById(R.id.inputEditText)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        inputEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                textFind(inputEditText.text.toString())
                true
            }
            false
        }
        loadInPhone()
        inputEditText.setOnFocusChangeListener { view, hasFocus ->
            displayRecentlyViewed()
        }
        sharedPrefs.registerOnSharedPreferenceChangeListener { sharedPrefs, key ->
            renderingCondition()
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
            recyclerView.visibility = View.VISIBLE
    }

    private  fun successfulСall(response: Response<ListDataMusic>){
        if (response.isSuccessful && (response.body()!!.resultCount >0)) {
            recyclerView.adapter = MusicAdapter(response.body()!!.results){DataMusic -> addMusicInList(DataMusic)}
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
    }

    private fun inputEditTextWatcher(){
        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if (count<0) {
                    if (inputEditText.hasFocus()) {
                        displayRecentlyViewed()
                    } else {
                        recyclerView.visibility = View.INVISIBLE
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {
                countValue = inputEditText.text.toString()
                if (countValue.isEmpty()) {
                    if (inputEditText.hasFocus()) {
                        displayRecentlyViewed()
                    } else {
                        recyclerView.visibility = View.INVISIBLE
                    }
                } else {
                    recyclerView.visibility = View.VISIBLE
                    textFind(countValue)
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (count<0) {
                    if (inputEditText.hasFocus()) {
                        displayRecentlyViewed()
                    } else {
                        recyclerView.visibility = View.INVISIBLE
                    }
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
        if(linkedList.size>0){
            recyclerView.adapter = ConcatAdapter(textPart(), recentSearchesPart(), buttonPart())
        }
    }

    private fun textPart(): RecyclerView.Adapter<*> {
        return SearchedQueriesTextAdapter(listOf("Вы искали"))
    }
    private fun buttonPart(): RecyclerView.Adapter<*> {
        return SearchedQueriesButtonAdapter(listOf("Очистить историю")){
            linkedList.clear()
            cleanLoadHistori()
            recyclerView.visibility = View.INVISIBLE
        }
    }
    private fun recentSearchesPart(): RecyclerView.Adapter<*>{
        return  MusicAdapter(linkedList){DataMusic ->
            addMusicInList(DataMusic)
            displayRecentlyViewed()}
    }

    private fun renderingCondition(){
        if (inputEditText.text.isEmpty()) {
            if (inputEditText.hasFocus()) {
                displayRecentlyViewed()
            } else {
                recyclerView.visibility = View.INVISIBLE
            }
        }

    }

    private fun addMusicInList(te : DataMusic){
        linkedList.remove(te)
        if(linkedList.size>=5){
            linkedList.removeLast()
        }
     linkedList.push(te)
        saveInPhone()
    }
    private fun cleanLoadHistori(){
        sharedPrefs.edit()
            .remove("0")
            .remove("1")
            .remove("2")
            .remove("3")
            .remove("4")
            .remove("5")
            .apply()
    }

    private fun saveInPhone(){
        cleanLoadHistori()

        linkedList.forEachIndexed { index, item ->
            sharedPrefs.edit()
                .putString((index).toString(), Gson().toJson(item))
                .apply()
        }
    }

    private fun loadInPhone(){
        linkedList.clear()
        for(i in 0..5){
            val json =  sharedPrefs.getString(i.toString(), null) ?: return
            linkedList.offerLast(Gson().fromJson(json, DataMusic::class.java))
        }

    }

}