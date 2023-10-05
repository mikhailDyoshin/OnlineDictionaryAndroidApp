package com.example.onlinedictionaryandroidappproject.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.onlinedictionaryandroidappproject.R
import com.example.onlinedictionaryandroidappproject.databinding.ListItemLayoutBinding
import com.example.onlinedictionaryandroidappproject.presentation.state.AudioState
import com.example.onlinedictionaryandroidappproject.presentation.viewmodel.WordViewModel

class PhoneticsListAdapter(
    private val viewModel: WordViewModel,
    private val checkAudioState: () -> Unit
) :
    ListAdapter<AudioState, PhoneticsListAdapter.ItemHolder>(ItemComparator()) {

    class ItemHolder(
        private val binding: ListItemLayoutBinding,
        private val viewModel: WordViewModel,
        private val checkAudioState: () -> Unit,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(audio: AudioState) = with(binding) {

            val currentItemContent = audio.country

            itemContent.text = currentItemContent

            itemLayout.setOnClickListener {
                viewModel.playAudio(audio = audio)
                checkAudioState()
            }


        }

        companion object {
            fun create(
                parent: ViewGroup,
                viewModel: WordViewModel,
                checkAudioState: () -> Unit
            ): ItemHolder {
                return ItemHolder(
                    ListItemLayoutBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    viewModel,
                    checkAudioState,
                )
            }
        }

    }

    class ItemComparator : DiffUtil.ItemCallback<AudioState>() {
        override fun areItemsTheSame(oldItem: AudioState, newItem: AudioState): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: AudioState, newItem: AudioState): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder.create(parent, viewModel, checkAudioState)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = getItem(position)

        holder.bind(item)

        if (position == itemCount - 1) {
            holder.itemView.setBackgroundResource(R.drawable.last_list_item_shape)
        }

    }

}