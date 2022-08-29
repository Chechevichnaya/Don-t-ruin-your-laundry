package com.blabla.dontruinyourlaundry.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.blabla.dontruinyourlaundry.R
import com.blabla.dontruinyourlaundry.data.SymbolForWashing

class RecyclerViewAdapterButton(
    private var data: List<SymbolForWashing>
) :
    RecyclerView.Adapter<RecyclerViewAdapterButton.ItemViewHolder>() {

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageSymbol: ImageButton = view.findViewById(R.id.item_button)
        val meaningSymbol: TextView = view.findViewById(R.id.symbol_meaning_in_card)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        val adapterLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.image_buttom_item, parent, false)
        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = data[position]
        holder.imageSymbol.setImageResource(item.pictureId)
        holder.meaningSymbol.setText(item.meaningOfSymbol)
       // holder.imageSymbol.setOnClickListener { holder.imageSymbol.findNavController().navigate() }
    }

    override fun getItemCount() = data.size
}