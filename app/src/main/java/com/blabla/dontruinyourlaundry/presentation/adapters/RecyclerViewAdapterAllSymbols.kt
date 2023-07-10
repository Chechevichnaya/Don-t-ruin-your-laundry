package com.blabla.dontruinyourlaundry.presentation.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.window.layout.WindowMetricsCalculator
import com.blabla.dontruinyourlaundry.R
import com.blabla.dontruinyourlaundry.databinding.SymbolGuideHeadnameBinding
import com.blabla.dontruinyourlaundry.databinding.SymbolGuideSymbolsItemBinding
import com.blabla.dontruinyourlaundry.domain.entity.SymbolGuideItem

private const val HEAD = 1
private const val SYMBOL_FOR_WASHING = 2

class RecyclerViewAdapterAllSymbols(
    activity: Activity,
    val clickListener: (SymbolGuideItem.SymbolForWashing) -> Unit
) :
    ListAdapter<SymbolGuideItem, RecyclerView.ViewHolder>(diffCallback) {

    val width: Int

    init {
        val windowMetrics =
            WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(activity)
        val currentBounds = windowMetrics.bounds
        width = currentBounds.width()
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<SymbolGuideItem>() {
            fun bothHeadName(oldItem: SymbolGuideItem, newItem: SymbolGuideItem): Boolean {
                return (oldItem is SymbolGuideItem.HeaderName && newItem is SymbolGuideItem.HeaderName)
            }

            fun bothSymbolForWashing(oldItem: SymbolGuideItem, newItem: SymbolGuideItem): Boolean {
                return (oldItem is SymbolGuideItem.SymbolForWashing && newItem is SymbolGuideItem.SymbolForWashing)
            }

            override fun areItemsTheSame(
                oldItem: SymbolGuideItem,
                newItem: SymbolGuideItem
            ): Boolean {
                return bothHeadName(oldItem, newItem) || bothSymbolForWashing(oldItem, newItem)
            }

            override fun areContentsTheSame(
                oldItem: SymbolGuideItem,
                newItem: SymbolGuideItem
            ): Boolean {
                val result =
                    if (oldItem is SymbolGuideItem.HeaderName && newItem is SymbolGuideItem.HeaderName) {
                        oldItem == newItem
                    } else if (oldItem is SymbolGuideItem.SymbolForWashing && newItem is SymbolGuideItem.SymbolForWashing) {
                        oldItem == newItem
                    } else false
                return result
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == HEAD) HeadNameViewHolder(
            SymbolGuideHeadnameBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        ) else SymbolForWashingViewHolder(
            SymbolGuideSymbolsItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is HeadNameViewHolder -> {
                holder.apply {
                    binding.divider.isVisible = position != itemCount - 1
                    bind(item as SymbolGuideItem.HeaderName)
                }
            }
            is SymbolForWashingViewHolder -> {
                holder.bind(item as SymbolGuideItem.SymbolForWashing)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is SymbolGuideItem.HeaderName -> HEAD
            is SymbolGuideItem.SymbolForWashing -> SYMBOL_FOR_WASHING
            else -> 0
        }
    }

    inner class HeadNameViewHolder(val binding: SymbolGuideHeadnameBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(head: SymbolGuideItem.HeaderName) {
            binding.apply {
                headCloth.text = head.nameId.getTitleName(headCloth.context)
            }
        }
    }

    inner class SymbolForWashingViewHolder(val binding: SymbolGuideSymbolsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(symbol: SymbolGuideItem.SymbolForWashing) {
            val bgColor: Int = if (symbol.selected) {
                R.color.color_selected
            } else {
                R.color.color_unselected
            }
            val context = binding.symbolInGuide.context
            binding.root.layoutParams.height = width / 5
            binding.root.layoutParams.width = width / 5
            val drawable = ResourcesCompat.getDrawable(context.resources, symbol.pictureId, null)
            val wrappedDrawable = DrawableCompat.wrap(drawable!!)
            DrawableCompat.setTint(
                wrappedDrawable,
                ResourcesCompat.getColor(context.resources, R.color.black, null)
            )
            binding.symbolInGuide.apply {
                setBackgroundColor(
                    ResourcesCompat.getColor(
                        context.resources,
                        bgColor,
                        null
                    )
                )
                setImageResource(symbol.pictureId)
                setOnClickListener { clickListener(symbol) }
            }
        }
    }
}