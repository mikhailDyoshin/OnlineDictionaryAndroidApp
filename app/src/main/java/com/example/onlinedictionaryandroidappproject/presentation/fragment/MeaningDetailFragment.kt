package com.example.onlinedictionaryandroidappproject.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.onlinedictionaryandroidappproject.R
import com.example.onlinedictionaryandroidappproject.databinding.FragmentMeaningDetailBinding
import com.example.onlinedictionaryandroidappproject.presentation.adapter.DefinitionsListAdapter
import com.example.onlinedictionaryandroidappproject.presentation.nav_arg_data.MeaningDetailNavData
import com.example.onlinedictionaryandroidappproject.presentation.viewmodel.WordViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MeaningDetailFragment : Fragment() {

    private val viewModel: WordViewModel by activityViewModels()

    private var meaningDetailNavData: MeaningDetailNavData? = null

    private lateinit var binding: FragmentMeaningDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_meaning_detail,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.wordViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // Back-button navigation
        binding.backButton.setOnClickListener {
            binding.root.findNavController().navigateUp()
        }

        // Home-button navigation
        binding.homeButton.setOnClickListener {
            val action =
                MeaningDetailFragmentDirections.actionMeaningDetailFragmentToGetWordFragment()
            binding.root.findNavController().navigate(action)
        }

        viewModel.wordState.observe(viewLifecycleOwner) { newState ->

            arguments?.let {
                val args = MeaningDetailFragmentArgs.fromBundle(it)
                meaningDetailNavData = args.MeaningDetailNavData
            }

            val definitionsList = newState.data?.wordsStatesList?.get(
                meaningDetailNavData?.wordID ?: 0
            )?.meanings?.get(meaningDetailNavData?.meaningID ?: 0)?.definitions


            val adapter =
                DefinitionsListAdapter(
                    meaningDetailNavData = meaningDetailNavData ?: MeaningDetailNavData(
                        wordID = 0,
                        meaningID = 0
                    )
                )

            val recyclerView: RecyclerView = binding.definitionsListRecyclerView
            recyclerView.adapter = adapter

            adapter.submitList(definitionsList)

        }

    }


}