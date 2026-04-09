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
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmarket.App
import com.example.playlistmarket.Constants
import com.example.playlistmarket.R
import com.example.playlistmarket.ui.ButtonVisibility
import com.example.playlistmarket.ui.ErrorAdapter
import com.example.playlistmarket.ui.ErrorData
import com.example.playlistmarket.ui.MusicAdapter
import com.example.playlistmarket.ui.SearchedQueriesButtonAdapter
import com.example.playlistmarket.ui.SearchedQueriesTextAdapter
import com.example.playlistmarket.ui.viewModel.SearchViewModel



class SearchActivity : AppCompatActivity() {

    private var searchQuery: String = ""
    private lateinit var inputEditText: EditText
    private lateinit var clearButton: ImageView
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var viewModel: SearchViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        initViews()
        recyclerView.layoutManager = LinearLayoutManager(this)
        initViewModel()
        inputEditTextWatcher()
        toolFinish()
        buttonClear()
        observOut ()
       inputEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.musicSearch(inputEditText.text.toString())
                true
            }
                false
        }


    }

    private fun initViews() {
        clearButton = findViewById(R.id.clearIcon)
        inputEditText = findViewById(R.id.inputEditText)
        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progressBar)
    }

    private fun initViewModel() {
        val factory = SearchViewModel.Factory(
            trackListInteractor = App.getInstance().trackListInteractor,
            activTrack = App.getInstance().activTrack ,
            musicInteractor = App.getInstance().musicInteractor
        )
        viewModel = ViewModelProvider(this,factory )[SearchViewModel::class.java]
    }




    private fun inputEditTextWatcher() {
        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun afterTextChanged(s: Editable?) {

                searchQuery = inputEditText.text.toString()
                if (searchQuery.isEmpty()) {
                    viewModel.swichHistoryListTrack()
                } else {
                    viewModel.musicSearch(searchQuery)
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (count == 0) {
                    viewModel.swichHistoryListTrack()
                }
                clearButton.visibility = clearButtonVisibility(s)
            }
        }
        inputEditText.addTextChangedListener(simpleTextWatcher)
    }


    // Кнопка назад
    private fun toolFinish() {
        val toolbar: Toolbar = findViewById(R.id.buttonBack)
        toolbar.setOnClickListener {
            finish()
        }
    }

    // Кнопка для очиски поиска
    private fun buttonClear() {
        clearButton.setOnClickListener {
            hideKeyboardAndClearFocus(inputEditText)
            inputEditText.setText("")
            recyclerView.visibility = View.INVISIBLE
        }
    }

    // сохраняем последнее записаное значение
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("codrush", searchQuery)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        searchQuery = savedInstanceState.getString("codrush", "")
        inputEditText.setText(searchQuery)
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
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }


    private fun observOut () {
        viewModel.observeViewCondition.observe(this) {
            if (it != null) {
                if (it.musicClick) {
                    val displayIntent = Intent(this, AudioPlayer::class.java)
                    startActivity(displayIntent)
                }
                when (it.pr) {
                    Constants.sostoinWie.START -> {
                        recyclerView.visibility = View.INVISIBLE
                        progressBar.visibility = View.INVISIBLE
                    }

                    Constants.sostoinWie.LOAD -> {
                        recyclerView.visibility = View.INVISIBLE
                        progressBar.visibility = View.VISIBLE
                    }

                    Constants.sostoinWie.HISTORY -> {
                            recyclerView.adapter = ConcatAdapter(
                            SearchedQueriesTextAdapter(listOf("Вы искали")),
                                MusicAdapter(it.listHistoryResult) {
                                    viewModel.clickSearchObject(it)
                                },
                            SearchedQueriesButtonAdapter(listOf("Очистить историю")) {
                                viewModel.clearHistoryTrack()
                            }
                        )

                        recyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.INVISIBLE
                    }

                    Constants.sostoinWie.RESULT -> {
                        recyclerView.adapter =
                            MusicAdapter(it.listSearchResult) {
                                viewModel.vlil(it)
                            }
                        recyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.INVISIBLE

                    }

                    Constants.sostoinWie.ERR_FIND -> {
                        recyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.INVISIBLE
                        recyclerView.adapter = errorNothingAdapter
                    }

                    Constants.sostoinWie.ERR_INET -> {
                        recyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.INVISIBLE
                        recyclerView.adapter = errorInetAdapter
                    }

                    else -> {
                        recyclerView.visibility = View.INVISIBLE
                        progressBar.visibility = View.INVISIBLE
                    }

                }
            } else{
            }
        }
    }



    val errorInetAdapter = ErrorAdapter(
        listOf(
            ErrorData(
                imageError = R.drawable.search_error_internet,
                nameError = R.string.notInternetError1,
                commentError = R.string.notInternetError2,
                buttonErrorVisibility = ButtonVisibility.VISIBLE,
                buttonErrorText = R.string.notInternetError3,
            )
        )
    ) {viewModel.musicSearch(searchQuery)}

    val errorNothingAdapter = ErrorAdapter(
        listOf(
            ErrorData(
                imageError = R.drawable.search_error_notfound,
                nameError = R.string.notFoundError1,
                commentError = R.string.notFoundError2,
                buttonErrorVisibility = ButtonVisibility.GONE,
                buttonErrorText = R.string.notFoundError3
            )
        )
    ) {viewModel.musicSearch(searchQuery)}




}


