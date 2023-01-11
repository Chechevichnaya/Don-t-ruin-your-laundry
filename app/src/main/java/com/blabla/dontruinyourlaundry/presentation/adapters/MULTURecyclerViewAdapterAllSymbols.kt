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
import com.blabla.dontruinyourlaundry.domain.entity.SymbolGuide

private const val HEAD = 1
private const val SYMBOL_FOR_WASHING = 2

class MULTURecyclerViewAdapterAllSymbols(
    activity: Activity,
    val clickListener: (SymbolGuide.SymbolForWashing) -> Unit
) :
    ListAdapter<SymbolGuide, RecyclerView.ViewHolder>(diffCallback) {

    val width: Int

    init {
        val windowMetrics =
            WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(activity)
        val currentBounds = windowMetrics.bounds
        width = currentBounds.width()
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<SymbolGuide>() {
            fun bothHeadName(oldItem: SymbolGuide, newItem: SymbolGuide): Boolean {
                return (oldItem is SymbolGuide.HeadName && newItem is SymbolGuide.HeadName)
            }

            fun bothSymbolForWashing(oldItem: SymbolGuide, newItem: SymbolGuide): Boolean {
                return (oldItem is SymbolGuide.SymbolForWashing && newItem is SymbolGuide.SymbolForWashing)
            }

            override fun areItemsTheSame(oldItem: SymbolGuide, newItem: SymbolGuide): Boolean {
                return bothHeadName(oldItem, newItem) || bothSymbolForWashing(oldItem, newItem)
            }

            override fun areContentsTheSame(oldItem: SymbolGuide, newItem: SymbolGuide): Boolean {
                val result =
                    if (oldItem is SymbolGuide.HeadName && newItem is SymbolGuide.HeadName) {
                        oldItem == newItem
                    } else if (oldItem is SymbolGuide.SymbolForWashing && newItem is SymbolGuide.SymbolForWashing) {
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
                    bind(item as SymbolGuide.HeadName)
                }
            }
            is SymbolForWashingViewHolder -> {
                holder.bind(item as SymbolGuide.SymbolForWashing)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is SymbolGuide.HeadName -> HEAD
            is SymbolGuide.SymbolForWashing -> SYMBOL_FOR_WASHING
            else -> 0
        }
    }

    inner class HeadNameViewHolder(val binding: SymbolGuideHeadnameBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val context = binding.headCloth.context
        fun bind(head: SymbolGuide.HeadName) {
            binding.apply {
                headCloth.text = head.nameId.getTitleName(context)
            }
        }
    }

    inner class SymbolForWashingViewHolder(val binding: SymbolGuideSymbolsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(symbol: SymbolGuide.SymbolForWashing) {
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
                setBackgroundColor(context.resources.getColor(bgColor))
                setImageResource(symbol.pictureId)
                setOnClickListener { clickListener(symbol) }
            }
        }
    }
}