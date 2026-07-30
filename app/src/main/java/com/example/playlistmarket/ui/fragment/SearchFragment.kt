package com.example.playlistmarket.ui.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmarket.R
import com.example.playlistmarket.databinding.FragmentSearchMusicScreenBinding
import com.example.playlistmarket.domain.model.SearchViewModelState
import com.example.playlistmarket.ui.ButtonVisibility
import com.example.playlistmarket.ui.ErrorAdapter
import com.example.playlistmarket.ui.ErrorData
import com.example.playlistmarket.ui.MusicAdapter
import com.example.playlistmarket.ui.SearchedQueriesButtonAdapter
import com.example.playlistmarket.ui.SearchedQueriesTextAdapter
import com.example.playlistmarket.ui.viewModel.SearchViewModel
import com.example.playlistmarket.ui.viewModel.SettingsViewModel
import org.koin.android.ext.android.inject

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchMusicScreenBinding? = null
    private val binding get() = _binding!!
    private var searchQuery: String = ""
    private lateinit var inputEditText: EditText
    private lateinit var clearButton: ImageView
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar


    private val viewModel: SearchViewModel by inject()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchMusicScreenBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setupSearchInputWatcher()
        setupBackButton()
        setupClearButton()
        observeViewModel()
        onRestoreInstanceState(savedInstanceState)
        inputEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.searchMusic(inputEditText.text.toString())
            }
            false
        }
    }

    // сохраняем последнее записаное значение
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("search_query", searchQuery)
    }




    private fun Activity.hideKeyboardAndClearFocus(view: View) {
        view.clearFocus()
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun initViews() {
        clearButton =  binding.clearIcon
        inputEditText =  binding.inputEditText
        recyclerView =  binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        progressBar =  binding.progressBar
    }




    private fun setupSearchInputWatcher() {
        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun afterTextChanged(s: Editable?) {
                searchQuery = inputEditText.text.toString()
                if (searchQuery.isEmpty()) {
                    viewModel.switchToHistory()
                } else {
                    viewModel.searchMusic(searchQuery)
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (count == 0) {
                    viewModel.switchToHistory()
                }
                clearButton.visibility = clearButtonVisibility(s)
            }
        }
        inputEditText.addTextChangedListener(simpleTextWatcher)
    }


    // Кнопка назад
    private fun setupBackButton() {

    }

    // Кнопка для очиски поиска
    private fun setupClearButton() {
        clearButton.setOnClickListener {
            requireActivity().hideKeyboardAndClearFocus(inputEditText)
            inputEditText.setText("")
            recyclerView.visibility = View.INVISIBLE
        }
    }


    fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        if (savedInstanceState != null){
            searchQuery = savedInstanceState.getString("search_query", "")
            inputEditText.setText(searchQuery)
        }
    }

    private fun clearButtonVisibility(s: CharSequence?): Int {
        return if (s.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    private fun setErrorInetAdapter(nameError :Int){
        recyclerView.adapter = ErrorAdapter(
            listOf(
                ErrorData(
                    imageError = R.drawable.search_error_internet,
                    nameError = nameError,
                    commentError = R.string.notInternetError2,
                    buttonErrorVisibility = ButtonVisibility.VISIBLE,
                    buttonErrorText = R.string.notInternetError3,
                )
            )
        ) {viewModel.searchMusic(searchQuery)}
    }

    private fun setErrorNothingAdapter(){
        recyclerView.adapter = ErrorAdapter(
            listOf(
                ErrorData(
                    imageError = R.drawable.search_error_notfound,
                    nameError = R.string.notFoundError1,
                    commentError = R.string.notFoundError2,
                    buttonErrorVisibility = ButtonVisibility.INVISIBLE,
                    buttonErrorText = R.string.searchErrorButton
                )
            )
        ) {viewModel.searchMusic(searchQuery)}
    }


    private fun observeViewModel () {
        viewModel.observeSearchState.observe(viewLifecycleOwner) {
            if (it != null) {
                if (it.clickStatus) {
                    findNavController().navigate(R.id.action_searchFragment_to_audioPlayerFragment)
                }
                when (it.modelStatus) {
                    SearchViewModelState.START -> {
                        recyclerView.visibility = View.INVISIBLE
                        progressBar.visibility = View.INVISIBLE
                    }

                    SearchViewModelState.LOAD -> {
                        recyclerView.visibility = View.INVISIBLE
                        progressBar.visibility = View.VISIBLE
                    }

                    SearchViewModelState.HISTORY -> {
                        recyclerView.adapter = ConcatAdapter(
                            SearchedQueriesTextAdapter(listOf(getString(R.string.textSearchHistory))),
                            MusicAdapter(it.listHistory) {
                                viewModel.handleTrackClick(it)
                            },
                            SearchedQueriesButtonAdapter(listOf(getString(R.string.textSearchButtonDeleteHistory))) {
                                viewModel.clearSearchHistory()
                            }
                        )

                        recyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.INVISIBLE
                    }

                    SearchViewModelState.RESULT -> {
                        recyclerView.adapter =
                            MusicAdapter(it.listSearch) {
                                viewModel.handleTrackClick(it)
                            }
                        recyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.INVISIBLE

                    }

                    SearchViewModelState.ERR_FIND -> {
                        recyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.INVISIBLE
                        setErrorNothingAdapter()
                    }

                    SearchViewModelState.ERR_INET -> {
                        recyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.INVISIBLE
                        setErrorInetAdapter(it.errorName)
                    }

                    else -> {
                        recyclerView.visibility = View.INVISIBLE
                        progressBar.visibility = View.INVISIBLE
                    }

                }
            } else{
            }
        }
    }
}