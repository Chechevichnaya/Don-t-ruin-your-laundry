package com.blabla.dontruinyourlaundry.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.blabla.dontruinyourlaundry.databinding.FirstLayoutBinding
import com.blabla.dontruinyourlaundry.databinding.SecondLayoutBinding
import com.blabla.dontruinyourlaundry.entity.SearchScreenItem

private val FIRST_TYPE = 1
private val SECOND_TYPE = 2

class FakeMultiholderAdapter(val clickListener: (SearchScreenItem) -> Unit) :
    ListAdapter<SearchScreenItem, RecyclerView.ViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == FIRST_TYPE) {
            FirstViewHolder(
                binding = FirstLayoutBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        } else {
            SecondViewHolder(
                SecondLayoutBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is FirstViewHolder -> {
                holder.bind(item as SearchScreenItem.Title)
            }
            is SecondViewHolder -> {
                holder.bind(item as SearchScreenItem.SearchParameter)
            }
        }
    }


    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is SearchScreenItem.Title -> FIRST_TYPE
            is SearchScreenItem.SearchParameter -> SECOND_TYPE
        }
    }

    inner class FirstViewHolder(val binding: FirstLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SearchScreenItem.Title) {
            binding.root.setOnClickListener {
                clickListener(item)
            }
        }
    }

    inner class SecondViewHolder(val binding: SecondLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SearchScreenItem.SearchParameter) {
            binding.root.setOnClickListener {
                clickListener(item)
            }
        }

    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<SearchScreenItem>() {
            override fun areItemsTheSame(
                oldItem: SearchScreenItem,
                newItem: SearchScreenItem
            ): Boolean {
                return (oldItem is SearchScreenItem.SearchParameter && newItem is SearchScreenItem.SearchParameter)
                        || (oldItem is SearchScreenItem.Title && newItem is SearchScreenItem.Title)
            }

            override fun areContentsTheSame(
                oldItem: SearchScreenItem,
                newItem: SearchScreenItem
            ): Boolean {
                return if (oldItem is SearchScreenItem.Title && newItem is SearchScreenItem.Title) {
                    oldItem.name == newItem.name
                } else if (oldItem is SearchScreenItem.SearchParameter && newItem is SearchScreenItem.SearchParameter) {
                    oldItem.name == newItem.name && oldItem.selected == newItem.selected
                } else false
            }
        }
    }

}

