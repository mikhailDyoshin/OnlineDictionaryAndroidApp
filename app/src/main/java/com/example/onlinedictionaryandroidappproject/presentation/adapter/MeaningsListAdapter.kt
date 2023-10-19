package com.example.onlinedictionaryandroidappproject.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.onlinedictionaryandroidappproject.R
import com.example.onlinedictionaryandroidappproject.databinding.ListItemLayoutBinding
import com.example.onlinedictionaryandroidappproject.presentation.fragment.WordDetailFragmentDirections
import com.example.onlinedictionaryandroidappproject.presentation.nav_arg_data.MeaningDetailNavData
import com.example.onlinedictionaryandroidappproject.presentation.state.MeaningsState

class MeaningsListAdapter(private val wordID: Int) :
    ListAdapter<MeaningsState, MeaningsListAdapter.ItemHolder>(ItemComparator()) {

    class ItemHolder(private val binding: ListItemLayoutBinding, private val wordID: Int) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(meaning: MeaningsState, position: Int) = with(binding) {

            itemContent.text = meaning.partOfSpeech

            val data = MeaningDetailNavData(
                wordID = wordID,
                meaningID = position
            )

            val action =
                WordDetailFragmentDirections.actionWordDetailFragmentToMeaningDetailFragment(
                    data
                )

            itemLayout.setOnClickListener {

                binding.root.findNavController().navigate(action)
            }
        }

        companion object {
            fun create(parent: ViewGroup, wordID: Int): ItemHolder {
                return ItemHolder(
                    ListItemLayoutBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    wordID
                )
            }
        }

    }

    class ItemComparator : DiffUtil.ItemCallback<MeaningsState>() {
        override fun areItemsTheSame(oldItem: MeaningsState, newItem: MeaningsState): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: MeaningsState, newItem: MeaningsState): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder.create(parent, wordID)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(getItem(position), position)

        if (position == itemCount - 1) {
            holder.itemView.setBackgroundResource(R.drawable.last_list_item_shape)
        }
    }

}
