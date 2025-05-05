package com.example.playlistmarket

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.Locale

class MediaActivity : AppCompatActivity() {
    private lateinit var sharedPrefs: SharedPreferences
    private lateinit var track: DataMusic


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_media)
        dataSet()
        managerTrack()
        setToolbarFunc()
    }

    private fun dataSet(){
        dataLoad()
        setImage()
        setMainText()
        setInfoText()
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
        albumText.text = track.collectionName
        yearText.text =  track.releaseDate.substring(0, 4)
        genreText.text = track.primaryGenreName
        countryText.text = track.country
    }//Установка дополнительных данных
    private fun setToolbarFunc(){
        val toolbar: Toolbar = findViewById(R.id.buttonBack)
        toolbar.setOnClickListener {
            finish()
        }
    }//Функционал Toolbar
    private fun managerTrack(){
        setButtonAddInPlaylist()
        setButtonAddInPlaylist()
        setButtonLike()
        trackTime()
    }//Блок управления треком
    private fun trackTime(){
        val timer: TextView = findViewById(R.id.timer)
        timer.text="00:00"
        timer.setTextSize(14f)
    }//Время состояния трека
    private fun setButtonPause(){}//Функционал кнопки "пауза"
    private fun setButtonAddInPlaylist(){}//Функционал кнопки "добавить в плей лист"
    private fun setButtonLike(){}//Функционал кнопки "лайк"
}