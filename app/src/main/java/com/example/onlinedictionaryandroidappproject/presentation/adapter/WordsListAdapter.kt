package com.example.onlinedictionaryandroidappproject.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.onlinedictionaryandroidappproject.R
import com.example.onlinedictionaryandroidappproject.presentation.fragment.GetWordFragmentDirections

class WordsListAdapter(private val dataSet: List<String>) :
RecyclerView.Adapter<WordsListAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val itemLayout: LinearLayout
        val itemID: TextView
        val itemContent: TextView

        init {
            itemLayout = view.findViewById(R.id.itemLayout)
            itemID = view.findViewById(R.id.itemID)
            itemContent = view.findViewById(R.id.itemContent)

        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.list_item_layout, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val currentItemContent = dataSet[position]
        val currentItemID = position + 1
        val currentItemIDString = "${currentItemID}."

        viewHolder.itemID.text = currentItemIDString
        viewHolder.itemContent.text = currentItemContent

        val action = GetWordFragmentDirections.actionGetWordFragmentToWordDetailFragment(
            wordID = position
        )

        viewHolder.itemLayout.setOnClickListener {

            viewHolder.itemView.findNavController().navigate(action)
        }


    }

    override fun getItemCount() = dataSet.size

}