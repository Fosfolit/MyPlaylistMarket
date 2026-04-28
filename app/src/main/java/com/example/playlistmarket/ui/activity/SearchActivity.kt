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
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmarket.Constants
import com.example.playlistmarket.R
import com.example.playlistmarket.ui.ButtonVisibility
import com.example.playlistmarket.ui.ErrorAdapter
import com.example.playlistmarket.ui.ErrorData
import com.example.playlistmarket.ui.MusicAdapter
import com.example.playlistmarket.ui.SearchedQueriesButtonAdapter
import com.example.playlistmarket.ui.SearchedQueriesTextAdapter
import com.example.playlistmarket.ui.viewModel.SearchViewModel
import org.koin.android.ext.android.inject


class SearchActivity : AppCompatActivity() {

    private var searchQuery: String = ""
    private lateinit var inputEditText: EditText
    private lateinit var clearButton: ImageView
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar


    private val viewModel: SearchViewModel by inject()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        initViews()
        setupSearchInputWatcher()
        setupBackButton()
        setupClearButton()
        observeViewModel ()

        inputEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.searchMusic(inputEditText.text.toString())
            }
                false
        }


    }

    private fun initViews() {
        clearButton = findViewById(R.id.clearIcon)
        inputEditText = findViewById(R.id.inputEditText)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        progressBar = findViewById(R.id.progressBar)
    }




    private fun setupSearchInputWatcher() {
        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun afterTextChanged(s: Editable?) {
                searchQuery = inputEditText.text.toString()
                if (searchQuery.isEmpty()) {
                    viewModel.switchToHistory()
                } else {
                    viewModel.searchMusic(searchQuery)
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (count == 0) {
                    viewModel.switchToHistory()
                }
                clearButton.visibility = clearButtonVisibility(s)
            }
        }
        inputEditText.addTextChangedListener(simpleTextWatcher)
    }


    // Кнопка назад
    private fun setupBackButton() {
        val toolbar: Toolbar = findViewById(R.id.buttonBack)
        toolbar.setOnClickListener {
            finish()
        }
        ViewCompat.setOnApplyWindowInsetsListener(toolbar) { view, insets ->
            val statusBar = insets.getInsets(WindowInsetsCompat.Type.statusBars())
            view.updatePadding(top = statusBar.top)
            insets }
    }

    // Кнопка для очиски поиска
    private fun setupClearButton() {
        clearButton.setOnClickListener {
            hideKeyboardAndClearFocus(inputEditText)
            inputEditText.setText("")
            recyclerView.visibility = View.INVISIBLE
        }
    }

    // сохраняем последнее записаное значение
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("search_query", searchQuery)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        searchQuery = savedInstanceState.getString("search_query", "")
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


    private fun observeViewModel () {
        viewModel.observeSearchState.observe(this) {
            if (it != null) {
                if (it.clickStatus) {
                    val displayIntent = Intent(this, AudioPlayer::class.java)
                    startActivity(displayIntent)
                }
                when (it.modelStatus) {
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
                                MusicAdapter(it.listHistory) {
                                    viewModel.handleTrackClick(it)
                                },
                            SearchedQueriesButtonAdapter(listOf("Очистить историю")) {
                                viewModel.clearSearchHistory()
                            }
                        )

                        recyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.INVISIBLE
                    }

                    Constants.sostoinWie.RESULT -> {
                        recyclerView.adapter =
                            MusicAdapter(it.listSearch) {
                                viewModel.handleTrackClick(it)
                            }
                        recyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.INVISIBLE

                    }

                    Constants.sostoinWie.ERR_FIND -> {
                        recyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.INVISIBLE
                        setErrorNothingAdapter()
                    }

                    Constants.sostoinWie.ERR_INET -> {
                        recyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.INVISIBLE
                        setErrorInetAdapter(it.errorName)
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

    private fun setErrorInetAdapter(nameError :Int){
     recyclerView.adapter = ErrorAdapter(
         listOf(
             ErrorData(
                 imageError = R.drawable.search_error_internet,
                 nameError = nameError,
                 commentError = R.string.notInternetError2,
                 buttonErrorVisibility = ButtonVisibility.VISIBLE,
                 buttonErrorText = R.string.notInternetError3,
             )
         )
     ) {viewModel.searchMusic(searchQuery)}
 }

    private fun setErrorNothingAdapter(){
    recyclerView.adapter = ErrorAdapter(
            listOf(
                ErrorData(
                    imageError = R.drawable.search_error_notfound,
                    nameError = R.string.notFoundError1,
                    commentError = R.string.notFoundError2,
                    buttonErrorVisibility = ButtonVisibility.INVISIBLE,
                    buttonErrorText = R.string.searchErrorButton
                )
            )
            ) {viewModel.searchMusic(searchQuery)}
}




}


