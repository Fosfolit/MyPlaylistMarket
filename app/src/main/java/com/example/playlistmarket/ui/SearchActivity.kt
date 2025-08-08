package com.example.playlistmarket.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmarket.Creator.provideActivTrackInteractor
import com.example.playlistmarket.Creator.provideMusicInteractor
import com.example.playlistmarket.Creator.provideTrackListInteractor
import com.example.playlistmarket.R
import com.example.playlistmarket.domain.ButtonVisibility
import com.example.playlistmarket.domain.DataMusic
import com.example.playlistmarket.domain.ErrorAdapter
import com.example.playlistmarket.domain.ErrorData
import com.example.playlistmarket.domain.api.activTrack.ActivTrackInteractor
import com.example.playlistmarket.domain.api.searchMusic.MusicInteractor
import com.example.playlistmarket.domain.api.trackList.TrackListInteractor
import java.util.LinkedList


class SearchActivity : AppCompatActivity() {

    private var countValue: String = ""
    private lateinit var inputEditText: EditText
    private lateinit var clearButton: ImageView
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var v : TrackListInteractor
    private lateinit var d : MusicInteractor
    private lateinit var activTrack : ActivTrackInteractor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        v = provideTrackListInteractor(this)
        d = provideMusicInteractor()
        activTrack = provideActivTrackInteractor(this)
        clearButton = findViewById(R.id.clearIcon)
        inputEditText = findViewById(R.id.inputEditText)
        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progressBar)
        recyclerView.layoutManager = LinearLayoutManager(this)
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

    fun textFind(textFind : String){
        progressBarOn(true)
        d.searchMusic(textFind, object : MusicInteractor.MusicConsumer {
            override fun consume(foundMusic: List<DataMusic>) {
                runOnUiThread {
                    successfulСall(foundMusic)
                    recyclerView.visibility = View.VISIBLE
                    }
                }
        })
    }

    private  fun successfulСall(response: List<DataMusic>){
        progressBarOn(false)
        if (response.isNotEmpty()) {
            progressBar.visibility = View.GONE
            recyclerView.adapter = MusicAdapter(response) {
                DataMusic ->
                v.addItem(DataMusic)
                viewTrack(DataMusic)
            }
        }
        else{
            recyclerView.adapter = ErrorAdapter(listOf(
                ErrorData(
                    imageError = R.drawable.search_error_notfound,
                    nameError = getString(R.string.notFoundError1),
                    commentError = getString(R.string.notFoundError2),
                    buttonErrorVisibility = ButtonVisibility.GONE ,
                    buttonErrorText = getString(R.string.notFoundError3)
                )
            )){}
            recyclerView.visibility = View.VISIBLE
        }
        }

    private  fun unsuccessfulСall(){
        progressBarOn(false)
        recyclerView.adapter = ErrorAdapter(listOf(
        ErrorData(
            imageError = R.drawable.search_error_internet,
            nameError = getString(R.string.notInternetError1),
            commentError = getString(R.string.notInternetError2),
            buttonErrorVisibility =  ButtonVisibility.VISIBLE,
            buttonErrorText = getString(R.string.notInternetError3),
        )
        )){textFind(inputEditText.text.toString())}
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
        val toolbar: Toolbar = findViewById(R.id.buttonBack)
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
        progressBarOn(false)
        v.isEmpty(object : TrackListInteractor.EmptyTrackList{
            override fun consume(list: Boolean) {
                runOnUiThread {
                    if(!list) {
                        recyclerView.visibility = View.VISIBLE
                        v.loadListTrack(object : TrackListInteractor.LoadTrackList{
                            override fun consume(list: LinkedList<DataMusic>) {
                                runOnUiThread {
                                    recyclerView.adapter = ConcatAdapter(
                                        SearchedQueriesTextAdapter(listOf("Вы искали")),
                                    MusicAdapter(list)
                                    { DataMusic ->
                                        d.clickDebounce(object :MusicInteractor.BoolMusicConsumer{
                                            override fun consume(click: Boolean) {
                                                runOnUiThread {
                                                if(click){
                                                    v.addItem(DataMusic)
                                                    viewTrack(DataMusic)
                                                }
                                                }
                                            }

                                        })

                                            v.addItem(DataMusic)
                                            viewTrack(DataMusic)

                                    },
                                        SearchedQueriesButtonAdapter(listOf("Очистить историю"))
                                        {
                                            v.saveListTrack(LinkedList<DataMusic>())
                                            recyclerView.visibility = View.INVISIBLE
                                        }
                                    )
                                }
                            }
                        } )}
                    else{
                        recyclerView.visibility = View.INVISIBLE
                    }
                }
            }
        })}

    private fun viewTrack(dataForSave : DataMusic){
        activTrack.saveTrack(dataForSave)
        val displayIntent = Intent(this, AudioPlayer::class.java)
        startActivity(displayIntent)

    }
    private fun progressBarOn(tri: Boolean) {
        if (tri){
            progressBar.visibility = View.VISIBLE
            recyclerView.visibility = View.INVISIBLE
        }
        else{
            progressBar.visibility = View.INVISIBLE
            recyclerView.visibility = View.VISIBLE
        }
    }//функция отображения progressBar
}