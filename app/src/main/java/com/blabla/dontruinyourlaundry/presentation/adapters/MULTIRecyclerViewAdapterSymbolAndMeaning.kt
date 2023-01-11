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

private const val ADD_NEW_SYMBOL = 2
private const val SYMBOL_AND_MEANING = 1

class MULTIRecyclerViewAdapterSymbolAndMeaning(
    private val clickedDeleteItem: (SymbolGuide.SymbolForWashing) -> Unit,
    private var typeFrom: TypeOfRecyclerView
) :
    ListAdapter<SymbolGuide, RecyclerView.ViewHolder>(diffCallback) {

    inner class SymbolAndMeaningViewHolder(val binding: RecyclerViewSymbolMeaningBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private fun bind(symbol: SymbolGuide.SymbolForWashing) {
            val context = binding.iconButton.context
            binding.apply {
                iconButton.setImageResource(symbol.pictureId)
                iconButton.setBackgroundColor(context.resources.getColor(R.color.lilac200_light_lilac700_dark))
                symbolMeaningInCard.text = symbol.meaningOfSymbol
                when (typeFrom) {
                    TypeOfRecyclerView.ADD_SYMBOL_FRAGMENT ->
                        deleteButton.setOnClickListener {
                            clickedDeleteItem(
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

        fun bindFirst(symbol: SymbolGuide.SymbolForWashing) {
            bind(symbol)
            binding.divider.isVisible = false
        }

        fun bindNotFirst(symbol: SymbolGuide.SymbolForWashing) {
            bind(symbol)
        }
    }

//    inner class AddNewSymbolViewHolder(val binding: RecyclerViewAddNewCardItemAddSymbolBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//        @SuppressLint("ResourceAsColor")
//        fun bind(button: SymbolGuide.ButtonAddNewSymbol) {
//            binding.apply {
//                addSymbolButton.setImageResource(button.icon)
//                symbolMeaning.text = button.text
//                addSymbolLayout.setOnClickListener {
//                    clickedItemAddNewSymbol()
//                }
//            }
//        }
//    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is SymbolGuide.SymbolForWashing -> SYMBOL_AND_MEANING
//            is SymbolGuide.ButtonAddNewSymbol -> ADD_NEW_SYMBOL
            else -> 0
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SymbolAndMeaningViewHolder(
            RecyclerViewSymbolMeaningBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ))
//        ) else AddNewSymbolViewHolder(
//            RecyclerViewAddNewCardItemAddSymbolBinding.inflate(
//                LayoutInflater.from(parent.context),
//                parent,
//                false
//            )
//        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when {
            holder is MULTIRecyclerViewAdapterSymbolAndMeaning.SymbolAndMeaningViewHolder && position == 0 -> holder.bindFirst(item as SymbolGuide.SymbolForWashing)
            holder is SymbolAndMeaningViewHolder && position != 0 -> holder.bindNotFirst(item as SymbolGuide.SymbolForWashing)
//            holder is AddNewSymbolViewHolder -> holder.bind(item as SymbolGuide.ButtonAddNewSymbol)
        }
    }


    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<SymbolGuide>() {
            override fun areItemsTheSame(
                oldItem: SymbolGuide,
                newItem: SymbolGuide
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: SymbolGuide,
                newItem: SymbolGuide
            ): Boolean {
                return oldItem == newItem
            }
        }

    }
}