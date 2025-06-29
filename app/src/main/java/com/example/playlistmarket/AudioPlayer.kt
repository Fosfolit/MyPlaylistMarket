package com.example.playlistmarket

import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.button.MaterialButton
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.Locale

class AudioPlayer : AppCompatActivity() {
    private lateinit var sharedPrefs: SharedPreferences
    private lateinit var track: DataMusic
    private lateinit var buttonPause: MaterialButton
    private lateinit var buttonAddInPlaylist: Button
    private lateinit var buttonLike: Button
    private val handler = Handler(Looper.getMainLooper())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_media)
        buttonPause = findViewById(R.id.buttonPause)
        buttonAddInPlaylist = findViewById(R.id.buttonAddInPlaylist)
        buttonLike = findViewById(R.id.buttonLike)
        sharedPrefs = getSharedPreferences(PRACTICUM_EXAMPLE_PREFERENCES, MODE_PRIVATE)

        dataSet()
        funSet()
    }


    private fun funSet(){
        setToolbarFunc()
        managerTrack()
    }//Установка функционала
    private fun dataSet(){
        dataLoad()
        setImage()
        setMainText()
        setInfoText()
        preparePlayer()
    }//Установка всех данных трека
    private fun dataLoad(){
        sharedPrefs = getSharedPreferences(PRACTICUM_EXAMPLE_PREFERENCES, MODE_PRIVATE)
        val json =  sharedPrefs.getString("lisneng", null) ?: return
        track = Gson().fromJson(json, DataMusic::class.java)
    }//Загрузка данных
    private fun setImage(){
        val artworkUrl100: ImageView = findViewById(R.id.artworkUrl100)
        Glide.with(this)
            .load(track.artworkUrl100.replaceAfterLast('/',"512x512bb.jpg"))
            .placeholder(R.drawable.music_base)
            .centerCrop()
            .transform(RoundedCorners(8))
            .into(artworkUrl100)
    }//Установка обложки трека
    private fun setMainText(){
        val trackName: TextView = findViewById(R.id.trackName)
        val artistName: TextView = findViewById(R.id.artistName)
        trackName.text = track.trackName
        artistName.text = track.artistName
    }//Установка названия и исполниьеля трека
    private fun setInfoText(){
        val timerText: TextView = findViewById(R.id.timerText)
        val albumText: TextView = findViewById(R.id.albumText)
        val yearText: TextView = findViewById(R.id.yearText)
        val genreText: TextView = findViewById(R.id.genreText)
        val countryText: TextView = findViewById(R.id.countryText)

        timerText.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(track.trackTime)
        albumText.visibility = View.INVISIBLE
        if (track.collectionName.isNotEmpty()){
            albumText.text = track.collectionName
        }
        albumText.visibility = View.VISIBLE
        yearText.text =  track.releaseDate.substring(0, 4)
        genreText.text = track.primaryGenreName
        countryText.text = track.country
    }//Установка дополнительных данных
    private fun setToolbarFunc(){
        val toolbar: Toolbar = findViewById(R.id.buttonBack)
        toolbar.setOnClickListener {
            saveMizicPozition()
            mediaPlayer.stop()
            mediaPlayer.release()
            finish()
        }
    }//Функционал Toolbar
    private fun managerTrack(){
        setButtonAddInPlaylist()
        setButtonPause()
        setButtonLike()
        trackTime()
    }//Блок управления треком
    private fun trackTime(){
        val timer: TextView = findViewById(R.id.timer)
        timer.text = "00:00"
        timer.setTextSize(14f)
    }//Время состояния трека
    private fun setButtonPause(){
        buttonPause.setOnClickListener{
            musicSwitch ()
        }
    }//Функционал кнопки "пауза"
    private fun setButtonAddInPlaylist(){

    }//Функционал кнопки "добавить в плей лист"
    private fun setButtonLike(){

    }//Функционал кнопки "лайк"

    private fun musicSwitch () {
        when(playerState) {
            STATE_PLAYING -> {
                playerState = STATE_PAUSED
                buttonPause.setIconResource(R.drawable.button_play)
                mediaPlayer.pause()

            }
            STATE_PREPARED, STATE_PAUSED -> {
                playerState = STATE_PLAYING
                buttonPause.setIconResource(R.drawable.button_pause)
                mediaPlayer.start()

            }

        }
    }

    companion object {
    private const val STATE_DEFAULT = 0
    private const val STATE_PREPARED = 1
    private const val STATE_PLAYING = 2
    private const val STATE_PAUSED = 3
    }
    private var mediaPlayer = MediaPlayer()
    private var playerState = STATE_DEFAULT
    var url = "755"
    private fun preparePlayer() {
        if (track.previewUrl.isNotEmpty()){
            mediaPlayer.setDataSource( track.previewUrl)
            val albumText: TextView = findViewById(R.id.albumText)
            albumText.text = track.previewUrl
        } else{
            mediaPlayer.setDataSource( url )
        }
        mediaPlayer.prepareAsync()
        loadMizicPozition()
        mediaPlayer.setOnPreparedListener {
            buttonPause.isEnabled = true
            playerState = STATE_PREPARED
        }
        mediaPlayer.setOnCompletionListener {
            playerState = STATE_PREPARED
        }

        val newThread = Thread {
            timerQuiet()
        }
        newThread.start()
    }// Функция подготовки проигрывателя
    private var mytimer : Int = 0//Время играющий музыки
    private fun timerQuiet() {
        when(playerState) {
            STATE_PLAYING -> {
                mytimer += 100
                timeRecording(mytimer)
            }
            STATE_PREPARED -> {
                mytimer  = 0
                timeRecording(mytimer)
            }
            STATE_PAUSED -> {
            }
        }
        handler.postDelayed(Runnable{timerQuiet()} , 100L)
    }//Таймер считающий время
    private fun timeRecording(millis: Int) {
        val timer: TextView = findViewById(R.id.timer)
        val seconds = (millis / 1000) % 60
        val minutes = (millis / (1000 * 60)) % 60
        timer.text = "%02d:%02d".format(minutes, seconds)
    }//Функция записи в таймер по милисикундам
    private fun saveMizicPozition() {
        deletMizicPozition()
        sharedPrefs.edit()
            .putString("mytracurl", track.previewUrl)
            .putInt("mytrac", mediaPlayer.currentPosition)
            .putInt("mytime", mytimer)
            .apply()
    }
    private fun deletMizicPozition() {
        sharedPrefs.edit()
            .remove("mytracurl")
            .remove("mytrac")
            .remove("mytime")
            .apply()
    }

    private fun loadMizicPozition() {
     val track : String = sharedPrefs.getString("mytracurl", url ) ?: url
     if(track == url){
            deletMizicPozition()
        }
        else{
            timeRecording(sharedPrefs.getInt("mytime", 0) ?: 0)
            mediaPlayer.seekTo(sharedPrefs.getInt("mytrac", 0)?: 0)
        }
    }



}