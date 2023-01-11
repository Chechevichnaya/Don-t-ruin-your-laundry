package com.blabla.dontruinyourlaundry.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.blabla.dontruinyourlaundry.R
import com.blabla.dontruinyourlaundry.databinding.InnerRecyclerItemInsearchByParametersBinding
import com.blabla.dontruinyourlaundry.databinding.SearchByParametersTitleBinding
import com.blabla.dontruinyourlaundry.domain.entity.SearchScreenItem

private const val TITLE = 1
private const val SEARCH_PARAMETERS = 2

class RecyclerViewAdapterSearchParameter(val clickListener: (SearchScreenItem.SearchParameter) -> Unit) :
    ListAdapter<SearchScreenItem, RecyclerView.ViewHolder>(
        diffCallback
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
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
        val item = getItem(position)
        when (holder) {
            is TitleViewHolder -> {
                holder.binding.divider.isVisible = position != 0
                holder.bind(item as SearchScreenItem.Title)
            }
            is ParametersViewHolder -> {
                holder.binding.divider.isVisible = position == itemCount - 1
                holder.bind(item as SearchScreenItem.SearchParameter)
            }
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
                text = title.name.getTitleName(context)
                setTextColor(
                    ResourcesCompat.getColor(
                        binding.titleInSearchByParameters.context.resources,
                        R.color.icon_text,
                        null
                    )
                )
                isEnabled = false
            }
        }

    }

    inner class ParametersViewHolder(val binding: InnerRecyclerItemInsearchByParametersBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(parameter: SearchScreenItem.SearchParameter) {
            val bgColor: Int = if (parameter.selected) {
                R.color.search_selected
            } else {
                R.color.search_unselected
            }
            binding.buttonInSearchByParameters.apply {
                setBackgroundColor(ResourcesCompat.getColor(context.resources,bgColor, null))
                text = parameter.name.getName(context)
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
                val result =
                    if (oldItem is SearchScreenItem.Title && newItem is SearchScreenItem.Title) {
                        newItem == oldItem
                    } else if (oldItem is SearchScreenItem.SearchParameter && newItem is SearchScreenItem.SearchParameter) {
                        newItem == oldItem
                    } else false
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
