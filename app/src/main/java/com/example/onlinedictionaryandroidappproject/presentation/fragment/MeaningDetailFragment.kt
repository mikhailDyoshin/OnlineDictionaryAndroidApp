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
import com.example.onlinedictionaryandroidappproject.databinding.FragmentMeaningDetailBinding
import com.example.onlinedictionaryandroidappproject.presentation.adapter.DefinitionsListAdapter
import com.example.onlinedictionaryandroidappproject.presentation.nav_arg_data.MeaningDetailNavData
import com.example.onlinedictionaryandroidappproject.presentation.state.WordState
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

        viewModel.wordState.observe(viewLifecycleOwner) { newState ->

            arguments?.let {
                val args = MeaningDetailFragmentArgs.fromBundle(it)
                meaningDetailNavData = args.MeaningDetailNavData
            }

            val data = getDefinitions(
                newState.data?.wordsStatesList ?: listOf(),
                meaningDetailNavData ?: MeaningDetailNavData(0, 0)
            )

            val adapter =
                DefinitionsListAdapter(data)

            val recyclerView: RecyclerView = view.findViewById(R.id.definitionsListRecyclerView)
            recyclerView.adapter = adapter

        }

    }

    private fun getDefinitions(
        words: List<WordState>,
        navData: MeaningDetailNavData
    ): List<String> {

        val wordID = navData.wordID
        val meaningID = navData.meaningID

        if (words != mutableListOf<WordState>()) {

            val meaning = words[wordID].meanings[meaningID]

            return meaning.definitions.map { it.definition ?: "" }
        }

        return listOf()
    }

}