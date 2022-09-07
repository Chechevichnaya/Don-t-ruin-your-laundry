package com.blabla.dontruinyourlaundry.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blabla.dontruinyourlaundry.adapters.RecyclerViewAdapterCards
import com.blabla.dontruinyourlaundry.data.AddedCardsViewModel
import com.blabla.dontruinyourlaundry.data.CardInfo
import com.blabla.dontruinyourlaundry.databinding.FragmentClothingCardsBinding
import com.blabla.dontruinyourlaundry.entity.Category

class ClothingCardsFragment : Fragment() {

    private lateinit var binding: FragmentClothingCardsBinding
    private val sharedViewModel: AddedCardsViewModel by activityViewModels()

    companion object {
        const val CATEGORY_NAME = "CATEGORY_NAME"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentClothingCardsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.takeIf { it.containsKey(CATEGORY_NAME) }?.apply {
            val nameOfCategory = requireArguments().getString(CATEGORY_NAME)
            if (sharedViewModel.cardsExist.value == null) {
                nameOfCategory?.let {
                    Category.valueOf(
                        it
                    ).imageResId
                }?.let { binding.imageTypeOfCloth.setImageResource(it) }
                Log.d("TEST", "name of category - $nameOfCategory")
            } else {
                Log.d("TEST", "try to display card")
                val newCard = CardInfo(
                    sharedViewModel.nameCloth.value!!,
                    "",
                    sharedViewModel.listOfSymbols.value!!,
                    sharedViewModel.category.value!!
                )
                sharedViewModel.updateListOfCards(newCard)
                val listOfAllCards = sharedViewModel.listOfCards.value
                val listOfCardsByCategory =
                    listOfAllCards?.filter { it.category.name == nameOfCategory }
                binding.recyclerViewAddedCards.layoutManager =
                    LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                binding.recyclerViewAddedCards.adapter = listOfCardsByCategory?.let {
                    RecyclerViewAdapterCards(
                        it
                    )
                }
            }
        }

    }
}
