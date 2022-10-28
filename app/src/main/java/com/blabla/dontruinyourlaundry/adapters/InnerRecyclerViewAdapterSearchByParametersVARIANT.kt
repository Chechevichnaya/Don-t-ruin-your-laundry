package com.blabla.dontruinyourlaundry.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.blabla.dontruinyourlaundry.R
import com.blabla.dontruinyourlaundry.data.ButtonsForSearching
import com.blabla.dontruinyourlaundry.databinding.InnerRecyclerItemInsearchByParametersBinding

class InnerRecyclerViewAdapterSearchByParametersVARIANT(private val onButtonClicked: (ButtonsForSearching) -> Unit) :
    ListAdapter<ButtonsForSearching, InnerRecyclerViewAdapterSearchByParametersVARIANT.ItemViewHolder>(
        DiffCallback
    ) {

    class ItemViewHolder(private var binding: InnerRecyclerItemInsearchByParametersBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(button: ButtonsForSearching) {
            binding.buttonInSearchByParameters.text = button.name
            val bgColor: Int = if (button.selected) {
                R.color.lilac_200
            } else {
                R.color.lilac_700
            }
            val context = binding.buttonInSearchByParameters.context
            binding.buttonInSearchByParameters.setBackgroundColor(context.resources.getColor(bgColor))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            InnerRecyclerItemInsearchByParametersBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentButton = getItem(position)
        holder.itemView.setOnClickListener { onButtonClicked(currentButton) }
        holder.bind(currentButton)
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<ButtonsForSearching>() {
            override fun areItemsTheSame(
                oldItem: ButtonsForSearching,
                newItem: ButtonsForSearching
            ): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(
                oldItem: ButtonsForSearching,
                newItem: ButtonsForSearching
            ): Boolean {
                return oldItem.name == newItem.name
            }
        }
    }

}
