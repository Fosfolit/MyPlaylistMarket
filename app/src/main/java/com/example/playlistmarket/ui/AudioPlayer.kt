package com.example.playlistmarket.ui

import android.content.Context
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
import com.example.playlistmarket.domain.DataMusic
import com.example.playlistmarket.R
import com.google.android.material.button.MaterialButton
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.Locale

class AudioPlayer : AppCompatActivity() {
    private lateinit var track: DataMusic
    private lateinit var buttonPause: MaterialButton
    private lateinit var buttonAddInPlaylist: Button
    private lateinit var buttonLike: Button
    private val handler = Handler(Looper.getMainLooper())
    private var mediaPlayer = MediaPlayer()
    private lateinit var  timeFun :Runnable
    private var playerState = PlayerState.STATE_DEFAULT
    private lateinit var newThread:Thread
    private lateinit var sharedPrefs: SharedPreferences
 private  var chekplay : Boolean = false
    var url = "755"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_media)
        buttonPause = findViewById(R.id.buttonPause)
        buttonAddInPlaylist = findViewById(R.id.buttonAddInPlaylist)
        buttonLike = findViewById(R.id.buttonLike)
        sharedPrefs = getSharedPreferences(
            PRACTICUM_EXAMPLE_PREFERENCES,
            Context.MODE_PRIVATE)
        dataSet()
        funSet()
    }

    override fun onDestroy() {

        playerState = PlayerState.STATE_DEFAULT
        if ( chekplay){
            saveTrac()
        }
      handler.removeCallbacks(timeFun)
            mediaPlayer.stop()
            //mediaPlayer.release()
            super.onDestroy()
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
            playerState = PlayerState.STATE_DEFAULT
            if ( chekplay){
                saveTrac()
            }

               handler.removeCallbacks(timeFun)
                mediaPlayer.stop()
              //  mediaPlayer.release()
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
            PlayerState.STATE_PLAYING -> {
                playerState = PlayerState.STATE_PAUSED
                buttonPause.setIconResource(R.drawable.button_play)
                mediaPlayer.pause()

            }
            PlayerState.STATE_PREPARED, PlayerState.STATE_PAUSED -> {
                playerState = PlayerState.STATE_PLAYING
                chekplay =true

                buttonPause.setIconResource(R.drawable.button_pause)
                mediaPlayer.start()
                val savedUrl = sharedPrefs.getString("TracFullName", null)
                if (!savedUrl.isNullOrEmpty() && savedUrl != track.previewUrl) {
                    deletTrac()
                }
            }
            else ->{}
        }
    }

    enum class PlayerState  {
        STATE_DEFAULT ,
        STATE_PREPARED ,
        STATE_PLAYING ,
        STATE_PAUSED
    }//Виды состояния медиа плеера



    private fun preparePlayer() {
        if (track.previewUrl.isNotEmpty()){
            mediaPlayer.setDataSource( track.previewUrl)
        } else{
            mediaPlayer.setDataSource( url )
        }
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            buttonPause.isEnabled = true
            playerState = PlayerState.STATE_PREPARED
            loadTrac()
        }
        mediaPlayer.setOnCompletionListener {
            playerState = PlayerState.STATE_PREPARED
        }

        timerStart()
    }// Функция подготовки проигрывателя



    private fun timerStart() {
        newThread = Thread {
            timerQdt()
        }
        newThread.start()
    }//Функция запуска таймера для отслеживания трека
    private fun timerQdt() {
        val timer: TextView = findViewById(R.id.timer)
    when(playerState) {
        PlayerState.STATE_PLAYING -> {
            val timeNow = mediaPlayer.currentPosition
            val seconds = (timeNow / 1000) % 60
            val minutes = (timeNow / (1000 * 60)) % 60
            timer.text = "%02d:%02d".format(minutes, seconds)
            saveTrac()
        }

        else ->{}
    }
        timeFun = Runnable{timerQdt()}
        handler.postDelayed(timeFun , 100L)
    }//Обновление таймера каждую секунду



    private fun deletTrac() {
        sharedPrefs.edit()
            .remove("TracFullName")
            .remove("TracTime")
            .apply()
    }//Функция удаления сохраненого трека
    private fun saveTrac() {
        deletTrac()
        sharedPrefs.edit()
            .putString("TracFullName", track.previewUrl)
            .putInt("TracTime", mediaPlayer.currentPosition)
            .apply()
    }//Функция сохраненого трека
    private fun loadTrac() {
        
        val savedUrl = sharedPrefs.getString("TracFullName", null)
        val savedPosition = sharedPrefs.getInt("TracTime", 0)
        if (!savedUrl.isNullOrEmpty() && savedUrl != track.previewUrl) {

        } else if (savedUrl == track.previewUrl) {
            mediaPlayer.seekTo(savedPosition)
            val timer: TextView = findViewById(R.id.timer)
            val seconds = (savedPosition / 1000) % 60
            val minutes = (savedPosition / (1000 * 60)) % 60
            timer.text = "%02d:%02d".format(minutes, seconds)
        }
    }//Функция загрузка трека

}