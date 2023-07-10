package com.blabla.dontruinyourlaundry.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.blabla.dontruinyourlaundry.R
import com.blabla.dontruinyourlaundry.databinding.RecyclerViewSymbolMeaningBinding
import com.blabla.dontruinyourlaundry.domain.entity.SymbolGuideItem
import com.blabla.dontruinyourlaundry.domain.entity.TypeOfRecyclerView

private const val SYMBOL_AND_MEANING = 1

class RecyclerViewAdapterSymbolWithMeaning(
    private val clickedDeleteItem: (SymbolGuideItem.SymbolForWashing) -> Unit,
    private val type: TypeOfRecyclerView
) :
    ListAdapter<SymbolGuideItem, RecyclerView.ViewHolder>(diffCallback) {

    inner class SymbolAndMeaningViewHolder(val binding: RecyclerViewSymbolMeaningBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(symbol: SymbolGuideItem.SymbolForWashing, showDivider: Boolean) {
            val context = binding.iconButton.context
            binding.apply {
                iconButton.setImageResource(symbol.pictureId)
                iconButton.setBackgroundColor(
                    ResourcesCompat.getColor(
                        context.resources,
                        R.color.lilac200_light_lilac700_dark,
                        null
                    )
                )
                symbolMeaningInCard.text = symbol.meaningOfSymbol
                when (type) {
                    TypeOfRecyclerView.ADD_SYMBOL_FRAGMENT ->
                        deleteButton.setOnClickListener {
                            clickedDeleteItem(
                                symbol
                            )
                        }
                    TypeOfRecyclerView.CARD_DETAIL_FRAGMENT -> {
                        deleteButton.isVisible = false
                    }
                }
                divider.isVisible = showDivider
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is SymbolGuideItem.SymbolForWashing -> SYMBOL_AND_MEANING
            else -> 0
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SymbolAndMeaningViewHolder(
            RecyclerViewSymbolMeaningBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        holder as SymbolAndMeaningViewHolder
        holder.bind(
            symbol = item as SymbolGuideItem.SymbolForWashing,
            showDivider = position == 0
        )
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<SymbolGuideItem>() {
            override fun areItemsTheSame(
                oldItem: SymbolGuideItem,
                newItem: SymbolGuideItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: SymbolGuideItem,
                newItem: SymbolGuideItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}