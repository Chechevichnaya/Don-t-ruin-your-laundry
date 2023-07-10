package com.blabla.dontruinyourlaundry.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.blabla.dontruinyourlaundry.R
import com.blabla.dontruinyourlaundry.data.database.Card
import com.blabla.dontruinyourlaundry.databinding.CardItemWithPhotoAndNameBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

class CardsListAdapter(private val onCardClicked: (Card) -> Unit) :
    ListAdapter<Card, CardsListAdapter.ItemViewHolder>(diffCallback) {

    class ItemViewHolder(private var binding: CardItemWithPhotoAndNameBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(card: Card) {
            card.category.getResIcon()
            binding.itemName.text = card.name
            if (card.picture == null) {
                val context = binding.itemImage.context
                binding.itemImage.setImageResource(card.category.getResIcon())
                binding.itemImage.setColorFilter(
                    ResourcesCompat.getColor(
                        context.resources,
                        R.color.icon_of_category_in_card,
                        null
                    )
                )
            } else {
                Glide.with(binding.itemImage.context)
                    .load(card.picture.toUri())
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .transform(CenterCrop(), RoundedCorners(24))
                    .skipMemoryCache(true)
                    .into(binding.itemImage)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        return ItemViewHolder(
            CardItemWithPhotoAndNameBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentCard = getItem(position)
        holder.itemView.setOnClickListener { onCardClicked(currentCard) }
        holder.bind(currentCard)
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Card>() {
            override fun areItemsTheSame(oldItem: Card, newItem: Card): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Card, newItem: Card): Boolean {
                return oldItem.name == newItem.name
            }
        }
    }
}