package com.example.onlinedictionaryandroidappproject.presentation.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.onlinedictionaryandroidappproject.MainActivity
import com.example.onlinedictionaryandroidappproject.R
import com.example.onlinedictionaryandroidappproject.databinding.FragmentWordBinding
import com.example.onlinedictionaryandroidappproject.presentation.adapter.WordsListAdapter
import com.example.onlinedictionaryandroidappproject.presentation.state.WordState
import com.example.onlinedictionaryandroidappproject.presentation.viewmodel.WordViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class GetWordFragment : Fragment() {

    @Inject
    lateinit var viewModel: WordViewModel

    private val viewModel: WordViewModel by viewModels()

    private lateinit var binding: FragmentWordBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // Inject the ViewModel when the fragment is attached
        (activity?.applicationContext as MainActivity).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_word,
            container,
            false
        )


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.wordViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.wordState.observe(viewLifecycleOwner) { newState ->

            val adapter =
                WordsListAdapter(getWords(newState.data?.wordsStatesList ?: listOf()))

            val recyclerView: RecyclerView = view.findViewById(R.id.wordsListRecyclerView)
            recyclerView.adapter = adapter

        }

        binding.search.setOnClickListener {
            searchWord()
        }

    }

    private fun searchWord() {
        val wordToFind = binding.textInputEditText.text.toString()

        viewModel.getWord(word = wordToFind)

    }

    private fun getWords(words: List<WordState>): List<String> {


        if (words != mutableListOf<WordState>()) {

            return words.map { it.word ?: "" }
        }

        return listOf()
    }

}