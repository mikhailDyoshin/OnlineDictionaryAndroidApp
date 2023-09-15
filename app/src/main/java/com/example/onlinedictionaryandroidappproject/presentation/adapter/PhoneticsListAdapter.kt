package com.example.onlinedictionaryandroidappproject.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.onlinedictionaryandroidappproject.databinding.ListItemLayoutBinding
import com.example.onlinedictionaryandroidappproject.presentation.state.AudioState
import com.example.onlinedictionaryandroidappproject.presentation.viewmodel.WordViewModel

class PhoneticsListAdapter(private val context: Context, private val viewModel: WordViewModel) :
    ListAdapter<AudioState, PhoneticsListAdapter.ItemHolder>(ItemComparator()) {

    class ItemHolder(
        private val binding: ListItemLayoutBinding,
        private val context: Context,
        private val viewModel: WordViewModel
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(audio: AudioState, position: Int) = with(binding) {


            val currentItemID = position + 1
            val currentItemIDString = "${currentItemID}."

            itemContent.text = "Play"

            itemLayout.setOnClickListener {
                viewModel.playAudio(audio = audio)
                Toast.makeText(context, audio.audioURL, Toast.LENGTH_SHORT).show()
            }


        }

        companion object {
            fun create(parent: ViewGroup, context: Context, viewModel: WordViewModel): ItemHolder {
                return ItemHolder(
                    ListItemLayoutBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    context,
                    viewModel,
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
        return ItemHolder.create(parent, context, viewModel)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = getItem(position)

        holder.bind(item, position)

    }

}