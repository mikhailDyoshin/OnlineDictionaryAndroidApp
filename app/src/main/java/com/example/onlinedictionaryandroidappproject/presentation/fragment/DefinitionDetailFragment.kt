package com.example.onlinedictionaryandroidappproject.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
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

        // View that contain data
        val definitionView = binding.definition
        val exampleView = binding.example
        val exampleOnymsDiveder = binding.exampleOnymsDivider
        val synonymsListHeader: TextView = binding.synonymsListHeader
        val antonymsListHeader: TextView = binding.antonymsListHeader

        // Back-button navigation
        binding.backButton.setOnClickListener {
            binding.root.findNavController().navigateUp()
        }

        // Home-button navigation
        binding.homeButton.setOnClickListener {
            val action =
                DefinitionDetailFragmentDirections.actionDefinitionDetailFragmentToGetWordFragment()
            binding.root.findNavController().navigate(action)
        }

        viewModel.wordState.observe(viewLifecycleOwner) { newState ->

            // Getting arguments
            arguments?.let {
                val args = DefinitionDetailFragmentArgs.fromBundle(it)
                definitionDetailNavData = args.definitionDetailNavData
            }

            // Getting data from the ViewModel
            val data = getDefinitionDetail(
                newState.data?.wordsStatesList ?: listOf(),
                definitionDetailNavData ?: DefinitionDetailNavData(0, 0, 0)
            )

            // Data to display
            val definitionText = "Definition: ${data.definition}"
            val exampleText = "Example: ${data.example}"
            val synonyms = data.synonyms
            val antonyms = data.antonyms


            definitionView.text = definitionText

            if (data.example == null) {
                exampleView.visibility = View.GONE
                exampleOnymsDiveder.visibility = View.GONE
            } else {
                exampleView.text = exampleText
                exampleView.visibility = View.VISIBLE
                exampleOnymsDiveder.visibility = View.VISIBLE
            }

            if (synonyms.size != 0 || antonyms.size != 0) {

                val synonymsListHeaderText = "Synonyms"
                val antonymsListHeaderText = "Antonyms"

                synonymsListHeader.text = synonymsListHeaderText
                antonymsListHeader.text = antonymsListHeaderText

                val synonymsAdapter = SynonymsListAdapter(synonyms)
                val antonymsAdapter = AntonymsListAdapter(antonyms)

                val synonymsRecyclerView: RecyclerView = binding.synonymsListRecyclerView
                synonymsRecyclerView.adapter = synonymsAdapter

                val antonymsRecyclerView: RecyclerView = binding.antonymsListRecyclerView
                antonymsRecyclerView.adapter = antonymsAdapter
            }


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