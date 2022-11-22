package com.blabla.dontruinyourlaundry.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.blabla.dontruinyourlaundry.R
import com.blabla.dontruinyourlaundry.data.SymbolForWashing
import com.blabla.dontruinyourlaundry.entity.TypeOfRecyclerView

class RecyclerViewAdapterSymbolAndMeaning(
    private var data: List<SymbolForWashing>,
    private var typeFrom: TypeOfRecyclerView
) :
    RecyclerView.Adapter<RecyclerViewAdapterSymbolAndMeaning.ItemViewHolder>() {

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageSymbol: ImageButton = view.findViewById(R.id.item_button)
        val meaningSymbol: TextView = view.findViewById(R.id.symbol_meaning_in_card)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        val adapterLayout =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_button_add_symbol_to_card, parent, false)
        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
       // val context = holder.imageSymbol.context
        val item = data[position]
//        if (position != data.size) {
//            holder.imageSymbol.background.setTint(context.resources.getColor(R.color.lilac_200))
//        }
        holder.imageSymbol.setImageResource(item.pictureId)
        holder.meaningSymbol.text = item.meaningOfSymbol
        when (typeFrom) {
            TypeOfRecyclerView.CARDDETAILFRAGMENT -> {holder.imageSymbol.isEnabled = false }
            else -> {
//                holder.imageSymbol.setOnClickListener {
//                    holder.imageSymbol.findNavController()
//                        .navigate(R.id.action_addNewCard_to_addSymbolToCard)
//                }
            }
        }

    }

    override fun getItemCount() = data.size
}