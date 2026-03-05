package com.example.playlistmarket.ui

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmarket.R
import com.example.playlistmarket.databinding.ActivityMediaBinding
import com.example.playlistmarket.presentation.MediaPlayerMy
import com.example.playlistmarket.ui.viewModel.AudioPlayerViewModel
import java.text.SimpleDateFormat
import java.util.Locale

class AudioPlayer : AppCompatActivity() {


    private lateinit var binding: ActivityMediaBinding
    private lateinit var viewModel: AudioPlayerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMediaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[AudioPlayerViewModel::class.java]
        viewModel.setContext(this)
        setInfo()
        load()
        viewModel.trackLoad()
        setButtonPause()
     //   setToolbarFunc()
    }



    private fun load(){
        viewModel.observeTrackPosition.observe(this){track ->
            viewModel.plaerPrepare(MediaPlayerMy(track,binding.buttonPause,binding.timer))
        }
    }


    override fun onDestroy() {
    //    viewModel.plaerStop()
     //   viewModel.saveTrac()
        super.onDestroy()
    }


    private fun setInfo() {
        viewModel.observeThisTrack.observe(this) { thisTrack ->
            binding.apply {
                trackName.text = thisTrack.trackName
                artistName.text = thisTrack.artistName
                timerText.text =
                    SimpleDateFormat("mm:ss", Locale.getDefault()).format(thisTrack.trackTime)
                albumText.visibility = View.INVISIBLE
                if (thisTrack.collectionName.isNotEmpty()) {
                    albumText.text = thisTrack.collectionName
                }
                albumText.visibility = View.VISIBLE
                yearText.text = thisTrack.releaseDate.substring(0, 4)
                genreText.text = thisTrack.primaryGenreName
                countryText.text = thisTrack.country
                val artworkUrl100: ImageView = findViewById(R.id.artworkUrl100)
                Glide.with(this@AudioPlayer)
                    .load(thisTrack.artworkUrl100.replaceAfterLast('/', "512x512bb.jpg"))
                    .placeholder(R.drawable.music_base)
                    .centerCrop()
                    .transform(RoundedCorners(8))
                    .into(artworkUrl100)
            }

        }//Установка данных
    }

    private fun setButtonPause(){
        binding.buttonPause.setOnClickListener{
            viewModel.plaerAudioSwitch()
        }
    }//Функционал кнопки "пауза"

    private fun setToolbarFunc(){
        binding.buttonBack.setOnClickListener {
            viewModel.plaerStop()
            viewModel.saveTrac()
            finish()
        }
    }//Функционал Toolbar

}
