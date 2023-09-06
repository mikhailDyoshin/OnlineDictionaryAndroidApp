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
import com.example.onlinedictionaryandroidappproject.presentation.viewmodel.WordViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DefinitionDetailFragment : Fragment() {

    private val viewModel: WordViewModel by activityViewModels()

    private var definitionDetailNavData: DefinitionDetailNavData? = null

    private lateinit var binding: FragmentDefinitionDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_definition_detail, container, false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.wordViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // Views that contain data
        val definitionView = binding.definition
        val exampleView = binding.example
        val exampleOnymsDivider = binding.exampleOnymsDivider
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
            val data = viewModel.getDefinitionDetail(
                dataState = newState,
                navData = definitionDetailNavData ?: DefinitionDetailNavData(
                    wordID = 0,
                    meaningID = 0,
                    definitionID = 0,
                )
            )

            // Data to display
            val definitionText = "Definition: ${data.definition}"
            val exampleText = "Example: ${data.example}"
            val synonyms = data.synonyms
            val antonyms = data.antonyms


            definitionView.text = definitionText

            // Configure example-section rendering
            if (data.example == null) {
                exampleView.visibility = View.GONE
                exampleOnymsDivider.visibility = View.GONE
            } else {
                exampleView.text = exampleText
                exampleView.visibility = View.VISIBLE
                exampleOnymsDivider.visibility = View.VISIBLE
            }

            // Configure synonyms and antonyms lists rendering
            val synonymsAdapter = SynonymsListAdapter(synonyms)
            val antonymsAdapter = AntonymsListAdapter(antonyms)

            val synonymsRecyclerView: RecyclerView = binding.synonymsListRecyclerView
            synonymsRecyclerView.adapter = synonymsAdapter

            val antonymsRecyclerView: RecyclerView = binding.antonymsListRecyclerView
            antonymsRecyclerView.adapter = antonymsAdapter

            when {
                synonyms.isEmpty() && antonyms.isEmpty() -> {
                    synonymsListHeader.visibility = View.GONE
                    synonymsRecyclerView.visibility = View.GONE
                    antonymsListHeader.visibility = View.GONE
                    antonymsRecyclerView.visibility = View.GONE
                }

                synonyms.isEmpty() -> {
                    synonymsListHeader.visibility = View.GONE
                    synonymsRecyclerView.visibility = View.GONE

                }

                antonyms.isEmpty() -> {
                    antonymsListHeader.visibility = View.GONE
                    antonymsRecyclerView.visibility = View.GONE

                }

                else -> {
                    synonymsListHeader.visibility = View.VISIBLE
                    antonymsListHeader.visibility = View.VISIBLE
                }
            }

        }

    }

}