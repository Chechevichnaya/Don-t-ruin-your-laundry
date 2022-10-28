package com.blabla.dontruinyourlaundry.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.blabla.dontruinyourlaundry.R
import com.blabla.dontruinyourlaundry.data.ButtonsForSearching
import com.blabla.dontruinyourlaundry.data.DataForSearchByParameters
import com.blabla.dontruinyourlaundry.data.SelectionType
import com.google.android.material.button.MaterialButton

//class CardsListAdapter(private val onCardClicked: (Card) -> Unit) :
//    ListAdapter<Card, CardsListAdapter.ItemViewHolder>(DiffCallback) {


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

    private fun getListOfCurrentParameters(data: List<ButtonsForSearching>): List<String> {
        val listLaundry = DataForSearchByParameters.getListForLaundrySearch().map { it.name }
        val listDrying = DataForSearchByParameters.getListForDryingSearch().map { it.name }
        val listIroning = DataForSearchByParameters.getListForIroningSearch().map { it.name }
        val dataOnlyNames = data.map { it.name }
        Log.d("search", "data only names = $dataOnlyNames")

        //val selectedItems = adapter.data.map { it.symbolsByCategory }.flatten()
        //
        //                     .filter { it.selected }.toList()

        return when (dataOnlyNames) {
            listLaundry -> {
                listLaundry
            }
            listDrying -> {
                listDrying
            }
            listIroning -> {
                listIroning
            }
            else -> emptyList()
        }
    }

    override fun getItemCount() = data.size

}

