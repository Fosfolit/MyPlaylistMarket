package com.example.playlistmarket.ui.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmarket.R
import com.example.playlistmarket.ui.viewModel.SearchViewModel


class SearchActivity : AppCompatActivity() {
    private var countValue: String = ""
    private lateinit var inputEditText: EditText
    private lateinit var clearButton: ImageView
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var viewModel: SearchViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        clearButton = findViewById(R.id.clearIcon)
        inputEditText = findViewById(R.id.inputEditText)
        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progressBar)
        recyclerView.layoutManager = LinearLayoutManager(this)
        viewModel = ViewModelProvider(this)[SearchViewModel::class.java]
        viewModel.setContext(this)

        recyclerViewReact ()
        inputEditTextWatcher()
        progressBarReact()
        toolFinish()
        buttonClear()
        inputEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.musicSearch(inputEditText.text.toString())
                true
            }
            false
        }
        inputEditText.setOnFocusChangeListener { _, _ ->
            if (countValue.isEmpty()) {
                recyclerView.visibility = View.VISIBLE
                viewModel.loadHistoryListTrack()
            }
        }

        viewTrack()

    }

    private fun inputEditTextWatcher(){
        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                 if (count==0) {
                     viewModel.loadHistoryListTrack()
                  }
            }

            override fun afterTextChanged(s: Editable?) {

                countValue = inputEditText.text.toString()
                if (countValue.isEmpty()) {
                    viewModel.loadHistoryListTrack()
                } else {
                    viewModel.musicSearch(countValue)
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (count==0) {
                    viewModel.loadHistoryListTrack()
                }
                clearButton.visibility = clearButtonVisibility(s)
            }
        }
        inputEditText.addTextChangedListener(simpleTextWatcher)
    }

private fun viewTrack(){
        viewModel.observeMusicClick.observe(this){
            if (it){
                val displayIntent = Intent(this, AudioPlayer::class.java)
                startActivity(displayIntent)
            }
        }
    }


    //Реакция на данные о recyclerView
    private fun progressBarReact() {
        viewModel.observeProgressBarVisibility.observe(this) { it ->
            progressBar.visibility = it
        }
    }//функция отображения progressBar



    //Реакция на данные о recyclerView
    private fun recyclerViewReact (){
        viewModel.observeAdapter.observe(this){ it ->
            recyclerView.adapter = it
        }
        viewModel.observeAdapterVisibility.observe(this){
            recyclerView.visibility = it
        }
    }

    // Кнопка назад
    private fun toolFinish(){
        val toolbar: Toolbar = findViewById(R.id.buttonBack)
        toolbar.setOnClickListener {
            finish()
        }
    }

    // Кнопка для очиски поиска
    private fun buttonClear(){
        clearButton.setOnClickListener {
            hideKeyboardAndClearFocus(inputEditText)
            inputEditText.setText("")
            recyclerView.visibility = View.INVISIBLE
        }
    }

    // сохраняем последнее записаное значение
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
    private fun Activity.hideKeyboardAndClearFocus(view: View) {
        view.clearFocus()
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }




}