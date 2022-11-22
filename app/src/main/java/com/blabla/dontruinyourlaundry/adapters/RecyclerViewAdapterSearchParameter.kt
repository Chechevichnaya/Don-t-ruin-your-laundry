package com.blabla.dontruinyourlaundry.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.blabla.dontruinyourlaundry.R
import com.blabla.dontruinyourlaundry.databinding.InnerRecyclerItemInsearchByParametersBinding
import com.blabla.dontruinyourlaundry.databinding.SearchByParametersTitleBinding
import com.blabla.dontruinyourlaundry.entity.SearchScreenItem

private const val TITLE = 1
private const val SEARCH_PARAMETERS = 2

class RecyclerViewAdapterSearchParameter(val clickListener: (SearchScreenItem.SearchParameter) -> Unit) :
    ListAdapter<SearchScreenItem, RecyclerView.ViewHolder>(
        diffCallback
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.d("clicked", "onCreateViewHolder")

        return if (viewType == TITLE) TitleViewHolder(
            SearchByParametersTitleBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
        else ParametersViewHolder(
            InnerRecyclerItemInsearchByParametersBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d("clicked", "onBindViewHolder")
        val item = getItem(position)
        when (holder) {
            is TitleViewHolder -> {
                holder.binding.divider.isVisible = position != 0
                holder.bind(item as SearchScreenItem.Title)
            }
            is ParametersViewHolder -> holder.bind(item as SearchScreenItem.SearchParameter)
        }

    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is SearchScreenItem.Title -> TITLE
            is SearchScreenItem.SearchParameter -> SEARCH_PARAMETERS
        }
    }

    inner class TitleViewHolder(val binding: SearchByParametersTitleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(title: SearchScreenItem.Title) {
            binding.titleInSearchByParameters.apply {
                text = title.name
                isEnabled = false
            }
        }

    }

    inner class ParametersViewHolder(val binding: InnerRecyclerItemInsearchByParametersBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(parameter: SearchScreenItem.SearchParameter) {
            val bgColor: Int = if (parameter.selected) {
                R.color.lilac_700
            } else {
                R.color.lilac_200
            }
            Log.d("clicked", "color - $bgColor")
            val context = binding.buttonInSearchByParameters.context
            binding.buttonInSearchByParameters.apply {
                setBackgroundColor(context.resources.getColor(bgColor))
                text = parameter.name
                setOnClickListener { clickListener(parameter) }
            }
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<SearchScreenItem>() {

            fun bothAreTitles(oldItem: SearchScreenItem, newItem: SearchScreenItem): Boolean {
                return oldItem is SearchScreenItem.Title && newItem is SearchScreenItem.Title
            }

            fun bothAreSearchParameters(
                oldItem: SearchScreenItem,
                newItem: SearchScreenItem
            ): Boolean {
                return oldItem is SearchScreenItem.SearchParameter && newItem is SearchScreenItem.SearchParameter
            }

            override fun areContentsTheSame(
                oldItem: SearchScreenItem,
                newItem: SearchScreenItem
            ): Boolean {
                val result = if (oldItem is SearchScreenItem.Title && newItem is SearchScreenItem.Title) {
                        val nameTheSame = newItem == oldItem
                        nameTheSame
                    } else if (oldItem is SearchScreenItem.SearchParameter && newItem is SearchScreenItem.SearchParameter) {
                    val allArgumentsTheSame = newItem == oldItem
                        allArgumentsTheSame
                    } else false
                Log.d("clicked", "content the same - $result")
                return result
            }

            override fun areItemsTheSame(
                oldItem: SearchScreenItem,
                newItem: SearchScreenItem
            ): Boolean {
                return bothAreTitles(oldItem, newItem) || bothAreSearchParameters(oldItem, newItem)

            }
        }
    }


}
