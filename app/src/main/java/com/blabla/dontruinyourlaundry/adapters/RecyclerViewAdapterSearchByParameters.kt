package com.blabla.dontruinyourlaundry.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.blabla.dontruinyourlaundry.R
import com.blabla.dontruinyourlaundry.data.SearchByParametersCards
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager


class RecyclerViewAdapterSearchByParameters(var data: List<SearchByParametersCards>) :
    RecyclerView.Adapter<RecyclerViewAdapterSearchByParameters.ItemViewHolder>() {
    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title_in_search_by_parameters)
        val recyclerView: RecyclerView =
            view.findViewById(R.id.inner_recycler_in_search_by_parameters)
        val divider: View = view.findViewById(R.id.devider)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_item_search_by_parameters, parent, false)
        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val context = holder.recyclerView.context
        val item = data[position]
        holder.apply {
            divider.isVisible = position != data.lastIndex
            title.text = item.title
            recyclerView.layoutManager =
                FlexboxLayoutManager(context, FlexDirection.ROW, FlexWrap.WRAP)
            recyclerView.adapter = InnerRecyclerViewAdapterSearchByParameters(
                item.listOfButton,
                item.selectionType
            )
        }
    }

    override fun getItemCount() = data.size
}
