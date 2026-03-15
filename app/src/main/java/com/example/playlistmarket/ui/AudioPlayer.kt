package com.example.playlistmarket.ui

import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmarket.Creator.provideActivTrackInteractor
import com.example.playlistmarket.Creator.provideAudioInteractor
import com.example.playlistmarket.Creator.provideStorageInteractor
import com.example.playlistmarket.domain.DataMusic
import com.example.playlistmarket.R
import com.example.playlistmarket.domain.TrackPosition
import com.example.playlistmarket.domain.api.AudioInteractor
import com.example.playlistmarket.domain.api.activTrack.ActivTrackInteractor
import com.example.playlistmarket.domain.api.trackPosition.TrackPositionInteractor
import com.example.playlistmarket.presentation.MediaPlayerMy
import com.google.android.material.button.MaterialButton
import java.text.SimpleDateFormat
import java.util.Locale

class AudioPlayer : AppCompatActivity() {

    private lateinit var plaer : AudioInteractor

    private lateinit var thisTrack: DataMusic
    private lateinit var buttonPause: MaterialButton
    private lateinit var buttonAddInPlaylist: Button
    private lateinit var buttonLike: Button
    private lateinit var trackPositionInteractor: TrackPositionInteractor
    private lateinit var trackPosition : TrackPosition
    private lateinit var activTrack : ActivTrackInteractor
    private  var chekplay : Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_media)

        buttonPause = findViewById(R.id.buttonPause)
        buttonAddInPlaylist = findViewById(R.id.buttonAddInPlaylist)
        buttonLike = findViewById(R.id.buttonLike)

        load()
        setButtonPause()
        setToolbarFunc()
    }



    private fun load(){
        activTrack = provideActivTrackInteractor(this)

        trackLoad()
        plaer = provideAudioInteractor()
        trackPositionInteractor = provideStorageInteractor(this)
        loadTracPosition()


    }
    override fun onDestroy() {
        plaer.stopPlay()
        saveTrac()
        super.onDestroy()
    }


    private fun setInfo(){
        val timerText: TextView = findViewById(R.id.timerText)
        val albumText: TextView = findViewById(R.id.albumText)
        val yearText: TextView = findViewById(R.id.yearText)
        val genreText: TextView = findViewById(R.id.genreText)
        val countryText: TextView = findViewById(R.id.countryText)
        val trackName: TextView = findViewById(R.id.trackName)
        val artistName: TextView = findViewById(R.id.artistName)
        trackName.text = thisTrack.trackName
        artistName.text = thisTrack.artistName
        timerText.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(thisTrack.trackTime)
        albumText.visibility = View.INVISIBLE
        if (thisTrack.collectionName.isNotEmpty()){
            albumText.text = thisTrack.collectionName
        }
        albumText.visibility = View.VISIBLE
        yearText.text =  thisTrack.releaseDate.substring(0, 4)
        genreText.text = thisTrack.primaryGenreName
        countryText.text = thisTrack.country
        val artworkUrl100: ImageView = findViewById(R.id.artworkUrl100)
        Glide.with(this)
            .load(thisTrack.artworkUrl100.replaceAfterLast('/',"512x512bb.jpg"))
            .placeholder(R.drawable.music_base)
            .centerCrop()
            .transform(RoundedCorners(8))
            .into(artworkUrl100)
    }//Установка данных
    private fun setButtonPause(){
        buttonPause.setOnClickListener{
            plaer.AudioSwitch()
        }
    }//Функционал кнопки "пауза"

    private fun setToolbarFunc(){
        val toolbar: Toolbar = findViewById(R.id.buttonBack)
        toolbar.setOnClickListener {
            plaer.stopPlay()
            saveTrac()
            finish()
        }
    }//Функционал Toolbar

    private fun trackLoad(){
        activTrack.loadTrack(object : ActivTrackInteractor. ActivTrackConsumer {
            override fun consume(expression: DataMusic) {
                runOnUiThread {
                    thisTrack = expression
                    setInfo()
                }
            }
        })
    }//Загрузка данных трека

    private fun saveTrac() {
        trackPositionInteractor.saveTrackPosition(TrackPosition(thisTrack.previewUrl ?: "",plaer.getTime()))
    }//Функция сохраненого позиции трека

    private fun loadTracPosition() {
        trackPositionInteractor.loadTrackPosition(object : TrackPositionInteractor.StorageConsumer {
            override fun consume(track: TrackPosition) {
                runOnUiThread {
                    trackPosition = track
                    if (thisTrack.previewUrl!=track.trackUrl){
                        trackPosition = TrackPosition(thisTrack.previewUrl?: "" , 0)
                    }
                    plaer.prepare(MediaPlayerMy(trackPosition,buttonPause,findViewById(R.id.timer)))
                }
            }
        }
        )
    }//Функция загрузка позиции трека
    }





