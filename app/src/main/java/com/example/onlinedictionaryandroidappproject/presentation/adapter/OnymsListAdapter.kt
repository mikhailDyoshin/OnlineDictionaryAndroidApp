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
//class AntonymsListAdapter(
//    private val dataSet: List<String>
//) :
//    RecyclerView.Adapter<AntonymsListAdapter.ViewHolder>() {
//
//    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//
//        val itemContent: TextView
//
//        init {
//            itemContent = view.findViewById(R.id.itemContent)
//
//        }
//    }
//
//    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
//        // Create a new view, which defines the UI of the list item
//        val view = LayoutInflater.from(viewGroup.context)
//            .inflate(R.layout.onym_list_item_layout, viewGroup, false)
//
//        return ViewHolder(view)
//    }
//
//    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
//        val currentItemContent = dataSet[position]
//
//        viewHolder.itemContent.text = currentItemContent
//
//    }
//
//    override fun getItemCount() = dataSet.size
//}