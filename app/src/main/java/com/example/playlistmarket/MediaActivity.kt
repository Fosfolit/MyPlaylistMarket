package com.example.playlistmarket

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
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
        val toolbar: Toolbar = findViewById(R.id.buttonBack)
        dataLoad()
        dataSet()
        toolbar.setOnClickListener {
            finish()
        }
    }



    fun dataLoad(){
        sharedPrefs = getSharedPreferences(PRACTICUM_EXAMPLE_PREFERENCES, MODE_PRIVATE)
        val json =  sharedPrefs.getString("lisneng", null) ?: return
        track = Gson().fromJson(json, DataMusic::class.java)
    }//загрузка данных
    fun dataSet(){
        val trackName: TextView = findViewById(R.id.trackName)
        val artistName: TextView = findViewById(R.id.artistName)
        val timerText: TextView = findViewById(R.id.timerText)
        val albumText: TextView = findViewById(R.id.albumText)
        val yearText: TextView = findViewById(R.id.yearText)
        val genreText: TextView = findViewById(R.id.genreText)
        val countryText: TextView = findViewById(R.id.countryText)
        val timer: TextView = findViewById(R.id.timer)
        timerText.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(track.trackTime)
        albumText.text = track.collectionName
        yearText.text =  track.releaseDate.substring(0, 4)
        genreText.text = track.primaryGenreName
        countryText.text = track.country
        trackName.text = track.trackName
        artistName.text = track.artistName
        timer.text="0:0"
        val artworkUrl100: ImageView = findViewById(R.id.artworkUrl100)
        Glide.with(this)
            .load(track.artworkUrl100)
            .placeholder(R.drawable.music_base)
            .centerCrop()
            .transform(RoundedCorners(16))
            .into(artworkUrl100)
        trackName.setTextSize(22f)
        artistName.setTextSize(14f)
        timerText.setTextSize(13f)
        albumText.setTextSize(13f)
        yearText.setTextSize(13f)
        genreText.setTextSize(13f)
        countryText.setTextSize(13f)
        timer.setTextSize(14f)
    }//установка данных
}


