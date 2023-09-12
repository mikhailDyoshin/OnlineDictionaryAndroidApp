package com.example.onlinedictionaryandroidappproject.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.onlinedictionaryandroidappproject.databinding.DefinitionListItemLayoutBinding
import com.example.onlinedictionaryandroidappproject.presentation.fragment.MeaningDetailFragmentDirections
import com.example.onlinedictionaryandroidappproject.presentation.nav_arg_data.DefinitionDetailNavData
import com.example.onlinedictionaryandroidappproject.presentation.nav_arg_data.MeaningDetailNavData
import com.example.onlinedictionaryandroidappproject.presentation.state.DefinitionsState

class DefinitionsListAdapter(private val meaningDetailNavData: MeaningDetailNavData) :
    ListAdapter<DefinitionsState, DefinitionsListAdapter.ItemHolder>(ItemComparator()) {

    class ItemHolder(private val binding: DefinitionListItemLayoutBinding, private val meaningDetailNavData: MeaningDetailNavData) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(definition: DefinitionsState, position: Int) = with(binding) {

            itemContent.text = definition.definition

            val navData = DefinitionDetailNavData(
                wordID = meaningDetailNavData.wordID,
                meaningID = meaningDetailNavData.meaningID,
                definitionID = position,
            )

            val action =
                MeaningDetailFragmentDirections.actionMeaningDetailFragmentToDefinitionDetailFragment(
                    navData
                )

            itemLayout.setOnClickListener {

                binding.root.findNavController().navigate(action)
            }
        }

        companion object {
            fun create(parent: ViewGroup, meaningDetailNavData: MeaningDetailNavData): ItemHolder {
                return ItemHolder(
                    DefinitionListItemLayoutBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    meaningDetailNavData
                )
            }
        }

    }

    class ItemComparator : DiffUtil.ItemCallback<DefinitionsState>() {
        override fun areItemsTheSame(oldItem: DefinitionsState, newItem: DefinitionsState): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: DefinitionsState, newItem: DefinitionsState): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder.create(parent, meaningDetailNavData)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

}
