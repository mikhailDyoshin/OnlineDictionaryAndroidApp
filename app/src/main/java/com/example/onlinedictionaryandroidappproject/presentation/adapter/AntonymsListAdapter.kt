package com.example.onlinedictionaryandroidappproject.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.onlinedictionaryandroidappproject.R

class AntonymsListAdapter(
    private val dataSet: List<String>
) :
    RecyclerView.Adapter<AntonymsListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val itemContent: TextView

        init {
            itemContent = view.findViewById(R.id.itemContent)

        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.onym_list_item_layout, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val currentItemContent = dataSet[position]

        viewHolder.itemContent.text = currentItemContent

    }

    override fun getItemCount() = dataSet.size
}