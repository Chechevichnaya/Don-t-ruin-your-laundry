package com.blabla.dontruinyourlaundry.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blabla.dontruinyourlaundry.R
import com.blabla.dontruinyourlaundry.data.Cardinfo
import com.blabla.dontruinyourlaundry.data.ListOfCards


class RecyclerViewAdapterCards(private var data: List<Cardinfo>) :
    RecyclerView.Adapter<RecyclerViewAdapterCards.ItemViewHolder>() {

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameClothText: EditText = view.findViewById(R.id.name_of_cloth)
        val imageClothCards: ImageView = view.findViewById(R.id.item_image)
        val recyclerView: RecyclerView = view.findViewById(R.id.image_button_recycler_view)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false)
        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = data[position]
        val myDataButton = ListOfCards.loadListOfAddedSymbols()
        holder.nameClothText.setText(item.name)
        holder.imageClothCards.setImageResource(item.picture)

        holder.recyclerView.layoutManager =
            LinearLayoutManager(holder.recyclerView.context, RecyclerView.VERTICAL, false)
        holder.recyclerView.adapter = RecyclerViewAdapterButton(myDataButton)

    }

    override fun getItemCount() = data.size

}
