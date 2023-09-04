package com.example.onlinedictionaryandroidappproject.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.onlinedictionaryandroidappproject.R
import com.example.onlinedictionaryandroidappproject.presentation.fragment.DefinitionDetailFragmentDirections
import com.example.onlinedictionaryandroidappproject.presentation.fragment.MeaningDetailFragmentDirections
import com.example.onlinedictionaryandroidappproject.presentation.nav_arg_data.DefinitionDetailNavData
import com.example.onlinedictionaryandroidappproject.presentation.nav_arg_data.MeaningDetailNavData

class DefinitionsListAdapter(
    private val dataSet: List<String>,
    private val meaningDetailNavData: MeaningDetailNavData
) :
    RecyclerView.Adapter<DefinitionsListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

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

        val data = DefinitionDetailNavData(
            wordID = meaningDetailNavData.wordID,
            meaningID = meaningDetailNavData.meaningID,
            definitionID = position,
        )

//        val action = R.id.action_meaningDetailFragment_to_definitionDetailFragment
//        val bundle = bundleOf("definitionID" to position)

        val action = MeaningDetailFragmentDirections.actionMeaningDetailFragmentToDefinitionDetailFragment(
            data
        )

        viewHolder.itemLayout.setOnClickListener {

            viewHolder.itemView.findNavController().navigate(
                action
            )
        }

    }

    override fun getItemCount() = dataSet.size
}