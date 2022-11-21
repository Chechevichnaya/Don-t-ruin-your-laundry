package com.blabla.dontruinyourlaundry.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blabla.dontruinyourlaundry.KindsOfThingsForLaundryDirections
import com.blabla.dontruinyourlaundry.RoomStuff.CardsApplication
import com.blabla.dontruinyourlaundry.adapters.CardsListAdapter
import com.blabla.dontruinyourlaundry.viewModels.ClothingCardsFactory
import com.blabla.dontruinyourlaundry.viewModels.ClothingCardsViewModel
import com.blabla.dontruinyourlaundry.databinding.FragmentClothingCardsBinding
import com.blabla.dontruinyourlaundry.entity.Category

class ClothingCardsFragment : Fragment() {

    private lateinit var binding: FragmentClothingCardsBinding

    private val viewModel: ClothingCardsViewModel by viewModels {
        ClothingCardsFactory((activity?.application as CardsApplication).dataBase.cardsDao)
    }

    companion object {
        const val CATEGORY = "CATEGORY"
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

        //get enum Category from Bundle
        val category = (requireArguments().getSerializable(CATEGORY_NAME)) as Category
        val image = category.imageResId


        val adapter = CardsListAdapter { card ->
            val action = KindsOfThingsForLaundryDirections.actionKindsOfThingsForLaundryToCardDetailFragment(card.id)
            this.findNavController().navigate(action)
        }

        binding.recyclerViewAddedCards.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.recyclerViewAddedCards.adapter = adapter

        viewModel.allCardsByCategory(category).observe(viewLifecycleOwner) { cards ->
            //check if table by certain category is empty
            if (cards.isEmpty()) {
                //set full screen picture of cloth type
                binding.imageTypeOfCloth.setImageResource(image)
            } else adapter.submitList(cards)
        }
    }
}



