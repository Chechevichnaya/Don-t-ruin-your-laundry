package com.blabla.dontruinyourlaundry.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blabla.dontruinyourlaundry.R
import com.blabla.dontruinyourlaundry.data.SymbolGuide
import com.blabla.dontruinyourlaundry.entity.TypeOfRecyclerView

class RecyclerViewAdapterSymbolGuide(var data: List<SymbolGuide>, val type: TypeOfRecyclerView) :
    RecyclerView.Adapter<RecyclerViewAdapterSymbolGuide.ItemViewHolder>() {

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val head: TextView = view.findViewById(R.id.head_cloth)
        val recyclerViewSymbols: RecyclerView =
            view.findViewById(R.id.symbol_guide_grid_of_symbols_recyclerview)
    }


    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.symbol_guide_card, parent, false)
        return ItemViewHolder(adapterLayout)
    }


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = data[position]
        holder.head.text = item.headName
        holder.recyclerViewSymbols.layoutManager =
            GridLayoutManager(holder.recyclerViewSymbols.context, 5, RecyclerView.VERTICAL, false)
        holder.recyclerViewSymbols.adapter =
            RecyclerViewAdapterSymbolInGrid(item.symbolsByCategory, type)
        holder.recyclerViewSymbols.setHasFixedSize(true)

    }


}