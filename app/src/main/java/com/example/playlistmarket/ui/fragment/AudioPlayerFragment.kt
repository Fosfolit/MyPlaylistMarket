package com.example.playlistmarket.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmarket.R
import com.example.playlistmarket.databinding.MediaScreenBinding
import com.example.playlistmarket.domain.model.PlayerState
import com.example.playlistmarket.ui.viewModel.AudioPlayerViewModel
import org.koin.android.ext.android.inject
import java.text.SimpleDateFormat
import java.util.Locale

class AudioPlayerFragment: Fragment()  {
    private  var _binding: MediaScreenBinding? = null
    private  val binding get() = _binding!!
    private val viewModel: AudioPlayerViewModel by inject()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MediaScreenBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onDestroy() {
        viewModel.saveTrackPosition()
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBackButton()
        setupPlayButton()
        observeViewModel()
    }


    private fun setupPlayButton() {
        binding.buttonPause.setOnClickListener {
            viewModel.togglePlayback()
        }
    }

    private fun setupBackButton() {
        binding.buttonBack.setOnClickListener {
            viewModel.stopPlayback()
            viewModel.saveTrackPosition()
            parentFragmentManager.popBackStackImmediate()
        }
        ViewCompat.setOnApplyWindowInsetsListener(binding.buttonBack) { view, insets ->
            val statusBar = insets.getInsets(WindowInsetsCompat.Type.statusBars())
            view.updatePadding(top = statusBar.top)
            insets }
    } // Функционал Toolbar

    private fun observeViewModel(){
        viewModel.observePlayerUiState.observe(viewLifecycleOwner) {
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

                Glide.with(this@AudioPlayerFragment)
                    .load(it.thisTrack.artworkUrl100.replaceAfterLast('/', "512x512bb.jpg"))
                    .placeholder(R.drawable.music_base)
                    .centerCrop()
                    .transform(RoundedCorners(8))
                    .into(binding.artworkUrl100)
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