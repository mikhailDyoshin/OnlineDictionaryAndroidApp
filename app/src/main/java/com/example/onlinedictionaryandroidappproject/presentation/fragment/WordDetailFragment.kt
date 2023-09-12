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
import com.example.onlinedictionaryandroidappproject.databinding.FragmentWordDetailBinding
import com.example.onlinedictionaryandroidappproject.presentation.adapter.MeaningsListAdapter
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

        // Back-button navigation
        binding.backButton.setOnClickListener {
            binding.root.findNavController().navigateUp()
        }

        // Home-button navigation
        binding.homeButton.setOnClickListener {
            val action = WordDetailFragmentDirections.actionWordDetailFragmentToGetWordFragment()
            binding.root.findNavController().navigate(action)
        }

        viewModel.wordState.observe(viewLifecycleOwner) { newState ->

            val wordID = arguments?.getInt("wordID")

            val meaningsList =
                newState.data?.wordsStatesList?.get(wordID ?: 0)?.meanings

            val adapter = MeaningsListAdapter(wordID = wordID ?: 0)

            val recyclerView: RecyclerView = binding.meaningsListRecyclerView
            recyclerView.adapter = adapter

            adapter.submitList(meaningsList)

        }

    }


}