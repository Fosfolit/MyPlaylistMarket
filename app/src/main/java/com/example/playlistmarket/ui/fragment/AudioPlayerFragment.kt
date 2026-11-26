package com.example.playlistmarket.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmarket.R
import com.example.playlistmarket.databinding.FragmentMediaScreenBinding
import com.example.playlistmarket.domain.model.DataMusic
import com.example.playlistmarket.domain.model.PlayerState
import com.example.playlistmarket.ui.viewModel.AudioPlayerViewModel
import org.koin.android.ext.android.inject
import java.text.SimpleDateFormat
import java.util.Locale

class AudioPlayerFragment: Fragment()  {
    private  var _binding: FragmentMediaScreenBinding? = null
    private  val binding get() = _binding!!
    private val viewModel: AudioPlayerViewModel by inject()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMediaScreenBinding.inflate(inflater,container,false)
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
    } // Функционал Toolbar

    private fun observeViewModel(){
        viewModel.observePlayerUiState.observe(viewLifecycleOwner) {
            setText(it.thisTrack)
            setImage(it.thisTrack.artworkUrl100)
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
    private fun setImage(artworkUrl100:String){
        if (artworkUrl100!="") {
            Glide.with(this@AudioPlayerFragment)
                .load(artworkUrl100.replaceAfterLast('/', "512x512bb.jpg"))
                .placeholder(R.drawable.music_base)
                .centerCrop()
                .transform(RoundedCorners(8))
                .into(binding.artworkUrl100)
        }
    }
    private fun setText(thisTrack:DataMusic) {
        binding.apply {
            trackName.text = thisTrack.trackName
            artistName.text = thisTrack.artistName
            timerText.text = thisTrack.trackTime
            albumText.visibility = View.INVISIBLE
            if (thisTrack.collectionName.isNotEmpty()) {
                albumText.text = thisTrack.collectionName
            }
            albumText.visibility = View.VISIBLE
            if(thisTrack.releaseDate.length>4) {
                yearText.text = thisTrack.releaseDate.substring(0, 4)
            } else{
                yearText.text = thisTrack.releaseDate
            }
            genreText.text = thisTrack.primaryGenreName
            countryText.text = thisTrack.country
        }
    }




}