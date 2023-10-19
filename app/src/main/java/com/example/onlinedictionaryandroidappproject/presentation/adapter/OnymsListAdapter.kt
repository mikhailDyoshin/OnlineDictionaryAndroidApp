package com.example.onlinedictionaryandroidappproject.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.onlinedictionaryandroidappproject.databinding.OnymListItemLayoutBinding

class OnymsListAdapter : ListAdapter<String, OnymsListAdapter.ItemHolder>(ItemComparator()) {

    class ItemHolder(private val binding: OnymListItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(antonym: String) = with(binding) {

            itemContent.text = antonym

        }

        companion object {
            fun create(parent: ViewGroup): ItemHolder {
                return ItemHolder(
                    OnymListItemLayoutBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }

    }

    class ItemComparator : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(getItem(position))
    }

}
