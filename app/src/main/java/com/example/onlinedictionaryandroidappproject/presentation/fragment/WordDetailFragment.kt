package com.example.onlinedictionaryandroidappproject.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.onlinedictionaryandroidappproject.R
import com.example.onlinedictionaryandroidappproject.common.Resource
import com.example.onlinedictionaryandroidappproject.databinding.FragmentWordDetailBinding
import com.example.onlinedictionaryandroidappproject.presentation.adapter.MeaningsListAdapter
import com.example.onlinedictionaryandroidappproject.presentation.adapter.PhoneticsListAdapter
import com.example.onlinedictionaryandroidappproject.presentation.state.RequestState
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

            fun checkAudioStatus(
                state: Resource<RequestState> = newState,
                view: View = binding.root
            ) {
                val status = state.data?.audioStatusMessage

                if (status != null) {
                    when (status) {
                        "Success" -> {
                            Toast.makeText(view.context, "Playing", Toast.LENGTH_SHORT).show()
                        }

                        "Error" -> {
                            Toast.makeText(
                                view.context,
                                "Check internet connection",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }

            val wordID = arguments?.getInt("wordID")

            val meaningsList =
                newState.data?.wordsStatesList?.get(wordID ?: 0)?.meanings

            val audiosURLsList = newState.data?.wordsStatesList?.get(wordID ?: 0)?.phoneticAudios

            val meaningsAdapter = MeaningsListAdapter(wordID = wordID ?: 0)

            val phoneticsAdapter =
                PhoneticsListAdapter(viewModel = viewModel) { checkAudioStatus() }

            val meaningsRecyclerView: RecyclerView = binding.meaningsListRecyclerView
            meaningsRecyclerView.adapter = meaningsAdapter

            val phoneticsRecyclerView: RecyclerView = binding.audiosListRecyclerView
            phoneticsRecyclerView.adapter = phoneticsAdapter

            meaningsAdapter.submitList(meaningsList)
            phoneticsAdapter.submitList(audiosURLsList)

        }

    }


}