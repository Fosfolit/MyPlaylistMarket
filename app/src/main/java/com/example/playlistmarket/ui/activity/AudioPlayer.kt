package com.example.playlistmarket.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmarket.Constants
import com.example.playlistmarket.R
import com.example.playlistmarket.databinding.ActivityMediaBinding
import com.example.playlistmarket.domain.model.PlayerState
import com.example.playlistmarket.ui.viewModel.AudioPlayerViewModel
import org.koin.android.ext.android.inject
import java.text.SimpleDateFormat
import java.util.Locale

class AudioPlayer : AppCompatActivity() {

    private lateinit var binding: ActivityMediaBinding
    private val viewModel: AudioPlayerViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMediaBinding.inflate(layoutInflater)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContentView(binding.root)

        observeViewModel()
        setupPlayButton()
        setupBackButton()
    }



    override fun onDestroy() {
        viewModel.saveTrackPosition()
        super.onDestroy()
    }

    private fun setupPlayButton() {
        binding.buttonPause.setOnClickListener {
            viewModel.togglePlayback()
        }
    } // Функционал кнопки "пауза"

    private fun setupBackButton() {
        binding.buttonBack.setOnClickListener {
            viewModel.stopPlayback()
            viewModel.saveTrackPosition()
            finish()
        }
        ViewCompat.setOnApplyWindowInsetsListener(binding.buttonBack) { view, insets ->
            val statusBar = insets.getInsets(WindowInsetsCompat.Type.statusBars())
            view.updatePadding(top = statusBar.top)
            insets }
    } // Функционал Toolbar

    private fun observeViewModel(){
        viewModel.observePlayerUiState.observe(this) {
            binding.apply {
                trackName.text = it.thisTrack.trackName
                artistName.text = it.thisTrack.artistName
                timerText.text = SimpleDateFormat("mm:ss", Locale.getDefault())
                    .format(it.thisTrack.trackTime)

                albumText.visibility = View.INVISIBLE
                if (it.thisTrack.collectionName.isNotEmpty()) {
                    albumText.text = it.thisTrack.collectionName
                }
                albumText.visibility = View.VISIBLE

                yearText.text = it.thisTrack.releaseDate.substring(0, 4)
                genreText.text = it.thisTrack.primaryGenreName
                countryText.text = it.thisTrack.country

                val artworkUrl100: ImageView = findViewById(R.id.artworkUrl100)
                Glide.with(this@AudioPlayer)
                    .load(it.thisTrack.artworkUrl100.replaceAfterLast('/', "512x512bb.jpg"))
                    .placeholder(R.drawable.music_base)
                    .centerCrop()
                    .transform(RoundedCorners(8))
                    .into(artworkUrl100)
            }
            val seconds = (it.currentPosition / 1000) % 60
            val minutes = (it.currentPosition / (1000 * 60)) % 60
            binding.timer.text = "%02d:%02d".format(minutes, seconds)

            when (it.playerState) {
                PlayerState.STATE_PREPARED -> {
                    binding.buttonPause.setIconResource(R.drawable.button_play)
                    binding.buttonPause.isEnabled = true
                }

                PlayerState.STATE_PLAYING -> {
                    binding.buttonPause.setIconResource(R.drawable.button_pause)
                }

                PlayerState.STATE_PAUSED -> {
                    binding.buttonPause.setIconResource(R.drawable.button_play)
                }

                else -> {}

            }
        }
    }

}
