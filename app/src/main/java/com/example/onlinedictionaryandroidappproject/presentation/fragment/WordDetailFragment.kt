package com.example.onlinedictionaryandroidappproject.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.onlinedictionaryandroidappproject.R
import com.example.onlinedictionaryandroidappproject.databinding.FragmentWordDetailBinding
import com.example.onlinedictionaryandroidappproject.presentation.adapter.MeaningsListAdapter
import com.example.onlinedictionaryandroidappproject.presentation.state.WordState
import com.example.onlinedictionaryandroidappproject.presentation.viewmodel.WordViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WordDetailFragment : Fragment() {


    private val viewModel: WordViewModel by activityViewModels()

    private lateinit var binding: FragmentWordDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_word_detail,
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

            val wordID = arguments?.getInt("wordID")

            val data = getMeanings(newState.data?.wordsStatesList ?: listOf(), wordID ?: 0)

            val adapter =
                MeaningsListAdapter(data, wordID ?: 0)

            val recyclerView: RecyclerView = view.findViewById(R.id.meaningsListRecyclerView)
            recyclerView.adapter = adapter

        }

    }

    private fun getMeanings(words: List<WordState>, id: Int): List<String> {

        if (words != mutableListOf<WordState>()) {

            val word = words[id]

            return word.meanings.map { it.partOfSpeech ?: "" }
        }

        return listOf()
    }

}