package com.blabla.dontruinyourlaundry.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.blabla.dontruinyourlaundry.R
import com.blabla.dontruinyourlaundry.databinding.RecyclerViewSymbolMeaningBinding
import com.blabla.dontruinyourlaundry.domain.entity.SymbolGuide
import com.blabla.dontruinyourlaundry.domain.entity.TypeOfRecyclerView

class MULTIRecyclerViewAdapterSymbolAndMeaning(
    private val clickedItem: (SymbolGuide.SymbolForWashing) -> Unit,
    private var typeFrom: TypeOfRecyclerView
) :
    ListAdapter<SymbolGuide.SymbolForWashing, RecyclerView.ViewHolder>(diffCallback) {

    inner class ViewHolder(val binding: RecyclerViewSymbolMeaningBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(symbol: SymbolGuide.SymbolForWashing) {
            val context = binding.iconButton.context
            binding.apply {
                iconButton.setImageResource(symbol.pictureId)
                iconButton.setBackgroundColor(context.resources.getColor(R.color.lilac_200))
                symbolMeaningInCard.text = symbol.meaningOfSymbol
                when (typeFrom) {
                    TypeOfRecyclerView.ADD_SYMBOL_FRAGMENT ->
                        deleteButton.setOnClickListener {
                            clickedItem(
                                symbol
                            )
                        }
                    else -> {
                        !iconButton.isEnabled
                        deleteButton.isVisible = false
                    }
                }
            }

        }


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            RecyclerViewSymbolMeaningBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is ViewHolder -> holder.bind(item)
        }
    }


    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<SymbolGuide.SymbolForWashing>() {
            override fun areItemsTheSame(
                oldItem: SymbolGuide.SymbolForWashing,
                newItem: SymbolGuide.SymbolForWashing
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: SymbolGuide.SymbolForWashing,
                newItem: SymbolGuide.SymbolForWashing
            ): Boolean {
                return oldItem == newItem
            }
        }

    }
}