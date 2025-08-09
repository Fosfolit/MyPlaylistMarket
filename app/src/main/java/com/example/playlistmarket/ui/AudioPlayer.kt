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
  //  private val handler = Handler(Looper.getMainLooper())
 //   private var mediaPlayer = MediaPlayer()
   // private lateinit var  timeFun :Runnable
   // private var playerState = PlayerState.STATE_DEFAULT
 //   private lateinit var newThread:Thread
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



      //  dataSet()
      //  funSet()


    }



    private fun load(){
        activTrack = provideActivTrackInteractor(this)
        trackLoad()
        plaer = provideAudioInteractor()
        trackPositionInteractor = provideStorageInteractor(this)
        loadTracPosition()


    }


    override fun onDestroy() {

     //   playerState = PlayerState.STATE_DEFAULT
        if ( chekplay){
            saveTrac()
        }
    //  handler.removeCallbacks(timeFun)
         //   mediaPlayer.stop()
            //mediaPlayer.release()
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
            plaer.startPlay()
        }
    }//Функционал кнопки "пауза"








    private fun setToolbarFunc(){
        val toolbar: Toolbar = findViewById(R.id.buttonBack)
        toolbar.setOnClickListener {
          //  playerState = PlayerState.STATE_DEFAULT
            if ( chekplay){
                saveTrac()
            }

         //      handler.removeCallbacks(timeFun)
           //     mediaPlayer.stop()
              //  mediaPlayer.release()
                finish()
        }
    }//Функционал Toolbar
/*  private fun managerTrack(){
     setButtonPause()
     trackTime()
 }//Блок управления треком
 private fun trackTime(){
     val timer: TextView = findViewById(R.id.timer)
     timer.text = "00:00"
 }//Время состояния трека
 private fun setButtonPause(){
     buttonPause.setOnClickListener{
      //   musicSwitch ()
     }
 }//Функционал кнопки "пауза"

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
                 if (trackPosition.trackUrl != thisTrack.previewUrl) {
                     saveTrac()
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
         if (thisTrack.previewUrl.isNotEmpty()){
             mediaPlayer.setDataSource( thisTrack.previewUrl)
         } else{
             mediaPlayer.setDataSource( url )
         }
         mediaPlayer.prepareAsync()
         mediaPlayer.setOnPreparedListener {
             buttonPause.isEnabled = true
             playerState = PlayerState.STATE_PREPARED
             loadTracPosition()
         }
         mediaPlayer.setOnCompletionListener {
             playerState = PlayerState.STATE_PREPARED
         }

         timerStart()
     }// Функция подготовки проигрывателя


    /* private fun timerStart() {
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
     }  if (track.trackUrl == thisTrack.previewUrl) {
                     //mediaPlayer.seekTo(track.position)
                     val timer: TextView = findViewById(R.id.timer)
                     val seconds = (track.position / 1000) % 60
                     val minutes = (track.position / (1000 * 60)) % 60
                     timer.text = "%02d:%02d".format(minutes, seconds)
         timeFun = Runnable{timerQdt()}
  //       handler.postDelayed(timeFun , 100L)
     }//Обновление таймера каждую секунду
 */*/
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
     trackPositionInteractor.saveTrackPosition(TrackPosition(thisTrack.previewUrl,0))
         //mediaPlayer.currentPosition))
 }//Функция сохраненого позиции трека
 private fun loadTracPosition() {
     trackPositionInteractor.loadTrackPosition(object : TrackPositionInteractor.StorageConsumer {
         override fun consume(track: TrackPosition) {
             runOnUiThread {
                 if (thisTrack.previewUrl!=track.trackUrl){
                     trackPosition = TrackPosition(thisTrack.previewUrl , 0)
                 }
                 plaer.prepare(MediaPlayerMy(trackPosition,buttonPause,findViewById(R.id.timer)))
             }
         }
     }
     )
 }//Функция загрузка позиции трека


}
