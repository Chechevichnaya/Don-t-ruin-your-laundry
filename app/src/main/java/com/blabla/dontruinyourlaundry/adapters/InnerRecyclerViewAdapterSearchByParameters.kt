package com.blabla.dontruinyourlaundry.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.blabla.dontruinyourlaundry.R
import com.blabla.dontruinyourlaundry.entity.ButtonsForSearching
import com.blabla.dontruinyourlaundry.data.SelectionType
import com.google.android.material.button.MaterialButton

class InnerRecyclerViewAdapterSearchByParameters(
    private var data: List<ButtonsForSearching>,
    val selectionType: SelectionType
) : RecyclerView.Adapter<InnerRecyclerViewAdapterSearchByParameters.ItemViewHolder>() {

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val button: MaterialButton = view.findViewById(R.id.button_in_search_by_parameters)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.inner_recycler_item_insearch_by_parameters, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val context = holder.button.context
        val item = data[position]

        holder.apply {
            val bgColor: Int = if (item.selected) {
                R.color.lilac_700
            } else {
                R.color.lilac_200
            }
            button.setBackgroundColor(context.resources.getColor(bgColor))
            button.text = item.name
            button.setOnClickListener {
                onItemClicked(item)
            }
        }
    }

    private fun onItemClicked(
        clickedItem: ButtonsForSearching,
    ) {
        when (selectionType) {
            SelectionType.SINGLE -> {
                data.forEach { item ->
                    item.selected = item == clickedItem
                }
            }
            SelectionType.MULTI -> clickedItem.selected = !clickedItem.selected
        }
        notifyDataSetChanged()
    }


    override fun getItemCount() = data.size

}

