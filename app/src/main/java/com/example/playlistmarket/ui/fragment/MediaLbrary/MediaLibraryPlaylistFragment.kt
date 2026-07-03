package com.example.playlistmarket.ui.fragment.MediaLbrary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmarket.R
import com.example.playlistmarket.databinding.FragmentMediaPlaylistBinding
import com.example.playlistmarket.domain.model.MLPlaylistState
import com.example.playlistmarket.ui.ButtonVisibility
import com.example.playlistmarket.ui.ErrorAdapterMLFragment
import com.example.playlistmarket.ui.ErrorDataMLFragment
import com.example.playlistmarket.ui.viewModel.fragment.MediaLibraryPlaylistViewModel
import org.koin.android.ext.android.inject


class MediaLibraryPlaylistFragment : Fragment() {
    private val viewModel: MediaLibraryPlaylistViewModel by inject()
    private  var _binding: FragmentMediaPlaylistBinding? = null
    private  val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMediaPlaylistBinding.inflate(inflater,container,false)
        recyclerView = binding.root.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        observeViewModel()
        return binding.root
    }


    private fun setError(){
        recyclerView.adapter = ErrorAdapterMLFragment(
            listOf(
                ErrorDataMLFragment(
                    imageError = R.drawable.search_error_notfound,
                    nameError  = R.string.mediaLibraryErrorMissingPlaylists,
                    buttonErrorVisibility = ButtonVisibility.VISIBLE,
                    buttonErrorText = R.string.mediaLibraryNewPlaylists,
                )
            )
        ) {}
    }

    private fun observeViewModel() {
        viewModel.observeMediaLibraryPlaylistVMState.observe(viewLifecycleOwner){it ->
            if (it != null) {
                when (it.mediaLibraryPlaylistState) {
                    MLPlaylistState.STATE_DEFAULT ->{

                    }
                    MLPlaylistState.STATE_LOAD ->{

                    }
                    MLPlaylistState.STATE_ERROR ->{
                        setError()
                    }
                }
            }
        }

    }
}