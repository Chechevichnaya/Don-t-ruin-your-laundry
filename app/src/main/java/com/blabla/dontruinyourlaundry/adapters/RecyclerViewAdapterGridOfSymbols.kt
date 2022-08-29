package com.blabla.dontruinyourlaundry.adapters

import android.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.blabla.dontruinyourlaundry.R
import com.blabla.dontruinyourlaundry.data.SymbolForWashing

class RecyclerViewAdapterGridOfSymbols(private var data: List<SymbolForWashing>) :
    RecyclerView.Adapter<RecyclerViewAdapterGridOfSymbols.ItemViewHolder>() {

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val symbol: ImageButton = view.findViewById(R.id.symbol_in_guide)
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        Log.d("RecyclerView", "onCreateViewHolder - child")
        val adapterLayout =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.symbol_guide_symbols_item, parent, false)
        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(
        holder: ItemViewHolder,
        position: Int
    ) {

        Log.d("RecyclerView", "onBindViewHolder - child")
        val item = data[position]
        holder.symbol.setImageResource(item.pictureId)
        holder.symbol.setOnClickListener {
            AlertDialog.Builder(holder.symbol.context, R.style.AlertDialogTheme).apply {
                setMessage(item.meaningOfSymbol)
                setPositiveButton("Ok") { _, _ -> }
                show()
            }
        }
    }

        override fun getItemCount() = data.size
    }