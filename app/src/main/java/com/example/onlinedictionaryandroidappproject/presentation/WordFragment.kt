package com.example.onlinedictionaryandroidappproject.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.onlinedictionaryandroidappproject.R
import com.example.onlinedictionaryandroidappproject.databinding.FragmentWordBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WordFragment : Fragment() {
    private val viewModel: WordViewModel by viewModels()

    private lateinit var binding: FragmentWordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_word, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.wordViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.wordState.observe(viewLifecycleOwner) { newState ->
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_list_item_1,
                getDefinitions(newState.data?.wordsStatesList ?: listOf())
            )

            val listView: ListView = view.findViewById(R.id.listView) // Replace with your ListView's ID
            listView.adapter = adapter
        }




        binding.search.setOnClickListener {
            searchWord()
        }

    }

    private fun searchWord() {
        val wordToFind = binding.textInputEditText.text.toString()

        viewModel.getWord(word = wordToFind)

    }

    private fun getDefinitions(words: List<WordState>): List<String> {

        val definitionsArray = mutableListOf<String>()

        if (words != mutableListOf<WordState>()) {

            val definitions = words[0].meanings[0].definitions

            if (definitions != mutableListOf<DefinitionsState>()) {
                for (d in definitions) {
                    if (d.definition != null) {
                        definitionsArray.add(d.definition!!)
                    }
                }
            }
        }
        return definitionsArray.toList()
    }

}