package com.blabla.dontruinyourlaundry.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.blabla.dontruinyourlaundry.R
import com.blabla.dontruinyourlaundry.roomStuff.Card
import com.blabla.dontruinyourlaundry.entity.CategoryEnum


class RecyclerViewAdapterCards(private var data: List<Card>) :
    RecyclerView.Adapter<RecyclerViewAdapterCards.ItemViewHolder>() {

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var nameClothText: TextView = view.findViewById(R.id.item_name)
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
        val image = CategoryEnum.values().find { it == item.category }?.getResIcon()
//        val image = when (item.category) {
//            CategoryDBO.CLOTH -> {
//                CategoryDBO.CLOTH.getResIcon()
//            }
//            CategoryDBO.BAD_SHEETS -> {
//                CategoryDBO.BAD_SHEETS.getResIcon()
//            }
//            CategoryDBO.BATH -> {
//                CategoryDBO.BATH.getResIcon()
//            }
//            CategoryDBO.KITCHEN -> {
//                CategoryDBO.KITCHEN.getResIcon()
//            }
//            CategoryDBO.REST -> {
//                CategoryDBO.REST.getResIcon()
//            }
//        }
        if (image != null) {
            holder.imageClothCards.setImageResource(image)
        }


    }

    override fun getItemCount() = data.size

}
