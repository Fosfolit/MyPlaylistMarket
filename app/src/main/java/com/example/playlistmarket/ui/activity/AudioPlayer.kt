package com.example.playlistmarket.ui.activity

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
        setButtonPause()
        setToolbarFunc()
    }



    override fun onDestroy() {
        viewModel.saveTrac(this)
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
        viewModel.observeTimerText.observe(this){time ->
            val seconds = (time/ 1000) % 60
            val minutes = (time/ (1000 * 60)) % 60
            binding.timer.text = "%02d:%02d".format(minutes, seconds)
    }


}

    private fun setButtonPause(){
        binding.buttonPause.setOnClickListener{
            viewModel.mediaPlayerSwitch ()
        }

        viewModel.observePlayerState.observe(this){ state->
            when (state) {
                AudioPlayerViewModel.PlayerState.STATE_PREPARED ->{
                    binding.buttonPause.setIconResource(R.drawable.button_play)
                    binding.buttonPause.isEnabled = true}
                AudioPlayerViewModel.PlayerState.STATE_PLAYING ->
                    binding.buttonPause.setIconResource(R.drawable.button_pause)
                AudioPlayerViewModel.PlayerState.STATE_PAUSED ->
                    binding.buttonPause.setIconResource(R.drawable.button_play)
                else -> {}
            }
        }
    }//Функционал кнопки "пауза"

    private fun setToolbarFunc(){
        binding.buttonBack.setOnClickListener {
            viewModel.medioStop()
            viewModel.saveTrac(this)
            finish()
        }
    }//Функционал Toolbar

}
