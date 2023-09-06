package com.example.onlinedictionaryandroidappproject.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.onlinedictionaryandroidappproject.R
import com.example.onlinedictionaryandroidappproject.databinding.FragmentWordBinding
import com.example.onlinedictionaryandroidappproject.presentation.adapter.WordsListAdapter
import com.example.onlinedictionaryandroidappproject.presentation.state.WordState
import com.example.onlinedictionaryandroidappproject.presentation.viewmodel.WordViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GetWordFragment : Fragment() {

    private val viewModel: WordViewModel by activityViewModels()

    private lateinit var binding: FragmentWordBinding

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
                WordsListAdapter(viewModel.getWords(newState))

            val recyclerView: RecyclerView = binding.wordsListRecyclerView
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



}