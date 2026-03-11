package com.example.playlistmarket.ui.viewModel

import android.content.Context
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmarket.Creator.provideActivTrackInteractor
import com.example.playlistmarket.Creator.provideMusicInteractor
import com.example.playlistmarket.Creator.provideThemeInteractor
import com.example.playlistmarket.Creator.provideTrackListInteractor
import com.example.playlistmarket.R
import com.example.playlistmarket.domain.ButtonVisibility
import com.example.playlistmarket.domain.DataMusic
import com.example.playlistmarket.domain.ErrorAdapter
import com.example.playlistmarket.domain.ErrorData
import com.example.playlistmarket.domain.api.activTrack.ActivTrackInteractor
import com.example.playlistmarket.domain.api.searchMusic.MusicInteractor
import com.example.playlistmarket.domain.api.theme.ThemeInteractor
import com.example.playlistmarket.domain.api.trackList.TrackListInteractor
import com.example.playlistmarket.ui.MusicAdapter
import com.example.playlistmarket.ui.SearchedQueriesButtonAdapter
import com.example.playlistmarket.ui.SearchedQueriesTextAdapter
import java.util.LinkedList

class SearchViewModel() : ViewModel(){
    private val adapter = MutableLiveData<RecyclerView.Adapter<*>>()
    var observeAdapter: LiveData<RecyclerView.Adapter<*>> = adapter


    private val adapterVisibility = MutableLiveData<Int>()
    var observeAdapterVisibility: LiveData<Int> = adapterVisibility


    private val progressBarVisibility = MutableLiveData<Int>()
    var observeProgressBarVisibility: LiveData<Int> = progressBarVisibility




    private lateinit var activTrack : ActivTrackInteractor








    val errorInetAdapter = ErrorAdapter(listOf(
        ErrorData(
            imageError = R.drawable.search_error_internet,
            nameError = R.string.notInternetError1,
            commentError = R.string.notInternetError2,
            buttonErrorVisibility =  ButtonVisibility.VISIBLE,
            buttonErrorText = R.string.notInternetError3,
        )
    )){adapterVisibility.postValue(View.VISIBLE) }
    val errorNothingAdapter = ErrorAdapter(listOf(
        ErrorData(
            imageError = R.drawable.search_error_notfound,
            nameError = R.string.notFoundError1,
            commentError =  R.string.notFoundError2,
            buttonErrorVisibility = ButtonVisibility.GONE ,
            buttonErrorText = R.string.notFoundError3
        )
    )){ musicSearch(textWork)
        adapterVisibility.postValue(View.VISIBLE)}


    private val musicClick = MutableLiveData<Boolean>()
    var observeMusicClick: LiveData<Boolean> = musicClick




    // Загрузка истории просмотра
    private lateinit var trackListInteractor : TrackListInteractor
    fun loadHistoryListTrack (){
        try {
            trackListInteractor.loadListTrack(object : TrackListInteractor.LoadTrackList {
                override fun consume(list: LinkedList<DataMusic>) {
                    if (list.isNotEmpty()) {
                        adapter.postValue(ConcatAdapter(
                            SearchedQueriesTextAdapter(listOf("Вы искали")),
                            MusicAdapter(list) {
                                musicInteractor.clickDebounce(object :
                                    MusicInteractor.BoolMusicConsumer {
                                    override fun consume(click: Boolean) {
                                        trackListInteractor.addItem(it)
                                        activTrack.saveTrack(it)
                                        musicClick.postValue(click)
                                    }
                                })
                            },
                            SearchedQueriesButtonAdapter(listOf("Очистить историю")) {
                                trackListInteractor.saveListTrack(LinkedList<DataMusic>())
                                adapterVisibility.postValue(View.INVISIBLE)
                            }
                        ))
                    }
                }
            })
        }
        catch (e: Exception){
            adapter.postValue(errorInetAdapter)///не работает
        }

        adapterVisibility.postValue(View.VISIBLE)
    }

    fun setContext(context: Context){
        trackListInteractor = provideTrackListInteractor(context)
        activTrack = provideActivTrackInteractor(context)
    }

    // Загрузка треков по тексту
    private var musicInteractor : MusicInteractor = provideMusicInteractor()
    var textWork:String =""
    fun musicSearch(string: String){
        progressBarVisibility.postValue(View.VISIBLE)
        adapterVisibility.postValue(View.INVISIBLE)
        textWork = string
        musicInteractor.searchMusic(string, object : MusicInteractor.MusicConsumer {
            override fun consume(foundMusic: List<DataMusic>) {
                if (foundMusic.isNotEmpty()) {
                    adapter.postValue(
                        MusicAdapter(foundMusic) {
                            trackListInteractor.addItem(it)
                            activTrack.saveTrack(it)
                            musicClick.postValue(true)
                    })
                } else {
                    adapter.postValue(errorNothingAdapter)
                }
                progressBarVisibility.postValue(View.INVISIBLE)
                adapterVisibility.postValue(View.VISIBLE)
            }
        })
    }





}

