package com.example.onlinedictionaryandroidappproject.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.onlinedictionaryandroidappproject.R
import com.example.onlinedictionaryandroidappproject.databinding.ListItemLayoutBinding
import com.example.onlinedictionaryandroidappproject.presentation.fragment.GetWordFragmentDirections
import com.example.onlinedictionaryandroidappproject.presentation.state.WordState

class WordsListAdapter : ListAdapter<WordState, WordsListAdapter.ItemHolder>(ItemComparator()) {

    class ItemHolder(private val binding: ListItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(word: WordState, position: Int) = with(binding) {

            val currentItemID = position + 1
            val currentItemIDString = "${currentItemID}."

            itemContent.text = word.word
            itemID.text = currentItemIDString

            val action = GetWordFragmentDirections.actionGetWordFragmentToWordDetailFragment(
                wordID = position
            )

            itemLayout.setOnClickListener {

                binding.root.findNavController().navigate(action)
            }
        }

        companion object {
            fun create(parent: ViewGroup): ItemHolder {
                return ItemHolder(
                    ListItemLayoutBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }

    }

    class ItemComparator : DiffUtil.ItemCallback<WordState>() {
        override fun areItemsTheSame(oldItem: WordState, newItem: WordState): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: WordState, newItem: WordState): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(getItem(position), position)


        if (position == itemCount - 1) {
            holder.itemView.setBackgroundResource(R.drawable.last_list_item_shape)
        }
    }

}
