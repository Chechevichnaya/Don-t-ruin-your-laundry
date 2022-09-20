package com.blabla.dontruinyourlaundry.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.blabla.dontruinyourlaundry.R
import com.blabla.dontruinyourlaundry.RoomStuff.Card
import com.blabla.dontruinyourlaundry.entity.Category


class RecyclerViewAdapterCards(private var data: List<Card>) :
    RecyclerView.Adapter<RecyclerViewAdapterCards.ItemViewHolder>() {

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var nameClothText: TextView = view.findViewById(R.id.item_title)
        val imageClothCards: ImageView = view.findViewById(R.id.item_image)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false)
        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = data[position]
        holder.nameClothText.text = item.name
        val image = when (item.category) {
            Category.CLOTH -> {
                Category.CLOTH.imageResId
            }
            Category.BAD_SHEETS -> {
                Category.BAD_SHEETS.imageResId
            }
            Category.BATH -> {
                Category.BATH.imageResId
            }
            Category.KITCHEN -> {
                Category.KITCHEN.imageResId
            }
            Category.REST -> {
                Category.REST.imageResId
            }
        }
        holder.imageClothCards.setImageResource(image)


    }

    override fun getItemCount() = data.size

}
