package com.blabla.dontruinyourlaundry.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.blabla.dontruinyourlaundry.data.dataBase.Card
import com.blabla.dontruinyourlaundry.databinding.CardItemBinding
import com.blabla.dontruinyourlaundry.domain.entity.CategoryEnum
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class CardsListAdapter(private val onCardClicked: (Card) -> Unit) :
    ListAdapter<Card, CardsListAdapter.ItemViewHolder>(DiffCallback) {

    class ItemViewHolder(private var binding: CardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(card: Card) {
            val imageCategory = when (card.category) {
                CategoryEnum.CLOTH -> {
                    CategoryEnum.CLOTH.getResIcon()
                }
                CategoryEnum.BAD_SHEETS -> {
                    CategoryEnum.BAD_SHEETS.getResIcon()
                }
                CategoryEnum.BATH -> {
                    CategoryEnum.BATH.getResIcon()
                }
                CategoryEnum.KITCHEN -> {
                    CategoryEnum.KITCHEN.getResIcon()
                }
                CategoryEnum.REST -> {
                    CategoryEnum.REST.getResIcon()
                }
            }
            binding.itemName.text = card.name
            if (card.picture == null) {
                binding.itemImage.setImageResource(imageCategory)
            } else {
                Glide.with(binding.itemImage.context)
                    .load(card.picture.toUri())
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(binding.itemImage)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        // if there is a problem with recycler view's width is not match parent - PROBLEM SOLVED HERE
        // - pass as second parameter for method INFLATE - "parent"
        return ItemViewHolder(CardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentCard = getItem(position)
        holder.itemView.setOnClickListener { onCardClicked(currentCard) }
        holder.bind(currentCard)
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Card>() {
            override fun areItemsTheSame(oldItem: Card, newItem: Card): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Card, newItem: Card): Boolean {
                return oldItem.name == newItem.name
            }
        }
    }
}