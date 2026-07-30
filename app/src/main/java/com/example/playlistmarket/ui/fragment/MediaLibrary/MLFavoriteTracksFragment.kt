package com.example.playlistmarket.ui.fragment.MediaLibrary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmarket.R
import com.example.playlistmarket.databinding.FragmentMlFavoriteTracksBinding
import com.example.playlistmarket.domain.model.MLPlaylistState
import com.example.playlistmarket.ui.ButtonVisibility
import com.example.playlistmarket.ui.ErrorAdapterMLFragment
import com.example.playlistmarket.ui.ErrorDataMLFragment
import com.example.playlistmarket.ui.viewModel.MediaLibrary.MLFavoriteTracksViewModel
import org.koin.android.ext.android.inject


class MLFavoriteTracksFragment : Fragment() {

    private  var _binding: FragmentMlFavoriteTracksBinding? = null
    private  val binding get() = _binding!!
    private val viewModel: MLFavoriteTracksViewModel by inject()
    private lateinit var recyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMlFavoriteTracksBinding.inflate(inflater,container,false)

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
                    nameError  = R.string.mediaLibraryErrorEmptyMediaLibrary,
                    buttonErrorVisibility = ButtonVisibility.INVISIBLE,
                    buttonErrorText = R.string.mediaLibraryErrorEmptyMediaLibrary,
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