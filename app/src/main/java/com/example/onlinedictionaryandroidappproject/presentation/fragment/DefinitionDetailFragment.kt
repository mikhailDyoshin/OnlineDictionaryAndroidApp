package com.example.onlinedictionaryandroidappproject.presentation.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.onlinedictionaryandroidappproject.R
import com.example.onlinedictionaryandroidappproject.databinding.FragmentDefinitionDetailBinding
import com.example.onlinedictionaryandroidappproject.presentation.adapter.AntonymsListAdapter
import com.example.onlinedictionaryandroidappproject.presentation.adapter.SynonymsListAdapter
import com.example.onlinedictionaryandroidappproject.presentation.nav_arg_data.DefinitionDetailNavData
import com.example.onlinedictionaryandroidappproject.presentation.state.DefinitionsState
import com.example.onlinedictionaryandroidappproject.presentation.state.WordState
import com.example.onlinedictionaryandroidappproject.presentation.viewmodel.WordViewModel


class DefinitionDetailFragment : Fragment() {

    private val viewModel: WordViewModel by activityViewModels()

    private var definitionDetailNavData: DefinitionDetailNavData? = null

    private lateinit var binding: FragmentDefinitionDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_definition_detail,
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

            arguments?.let {
                val args = DefinitionDetailFragmentArgs.fromBundle(it)
                definitionDetailNavData = args.definitionDetailNavData
            }

            Log.d(
                "Definition detail",
                "Word ID: ${definitionDetailNavData?.wordID}, Meaning ID: ${definitionDetailNavData?.meaningID}, Definition ID:  ${definitionDetailNavData?.definitionID}"
            )

            val data = getDefinitionDetail(
                newState.data?.wordsStatesList ?: listOf(),
                definitionDetailNavData ?: DefinitionDetailNavData(0, 0,  0)
            )

            val definitionText = data.definition
            val exampleText = data.example
            val synonyms = data.synonyms
            val antonyms = data.antonyms

            val definitionTextView: TextView = view.findViewById(R.id.definition)
            definitionTextView.text = definitionText

            val exampleTextView: TextView = view.findViewById(R.id.example)
            exampleTextView.text = exampleText

            val synonymsAdapter =
                SynonymsListAdapter(synonyms)

            val antonymsAdapter = AntonymsListAdapter(antonyms)

            val synonymsRecyclerView: RecyclerView = view.findViewById(R.id.synonymsListRecyclerView)
            synonymsRecyclerView.adapter = synonymsAdapter

            val antonymsRecyclerView: RecyclerView = view.findViewById(R.id.antonymsListRecyclerView)
            antonymsRecyclerView.adapter = antonymsAdapter

        }

    }

    private fun getDefinitionDetail(
        words: List<WordState>,
        navData: DefinitionDetailNavData
    ): DefinitionsState {

        val wordID = navData.wordID
        val meaningID = navData.meaningID
        val definitionID = navData.definitionID

        if (words != mutableListOf<WordState>()) {

            return words[wordID].meanings[meaningID].definitions[definitionID]
        }

        return DefinitionsState()
    }


}