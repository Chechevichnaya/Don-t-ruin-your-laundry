package com.blabla.dontruinyourlaundry.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.blabla.dontruinyourlaundry.R
import com.blabla.dontruinyourlaundry.entity.ButtonsForSearching
import com.blabla.dontruinyourlaundry.databinding.InnerRecyclerItemInsearchByParametersBinding

class InnerRecyclerViewAdapterSearchByParametersVARIANT
    (private val onButtonClicked: (ButtonsForSearching) -> Unit) :
    ListAdapter<ButtonsForSearching,
            InnerRecyclerViewAdapterSearchByParametersVARIANT.ItemViewHolder>(DiffCallback) {

    class ItemViewHolder(var binding: InnerRecyclerItemInsearchByParametersBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(button: ButtonsForSearching) {
            binding.buttonInSearchByParameters.text = button.name
            val bgColor: Int = if (button.selected) {
                R.color.lilac_700
            } else {
                R.color.lilac_200
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
        holder.binding.buttonInSearchByParameters.setOnClickListener {
            onButtonClicked(currentButton)
        }
        holder.bind(currentButton)
//        holder.button.text = currentButton.name
//        holder.button.setBackgroundColor(holder.button.resources.getColor(R.color.lilac_200))
//
//        holder.button.setOnClickListener {
//            val bgColor: Int = if (currentButton.selected) {
//                R.color.lilac_200
//            } else {
//                R.color.lilac_700
//            }
//            val context = holder.button.context
//            holder.button.setBackgroundColor(context.resources.getColor(bgColor))
//            currentButton.selected = !currentButton.selected
//    }

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
