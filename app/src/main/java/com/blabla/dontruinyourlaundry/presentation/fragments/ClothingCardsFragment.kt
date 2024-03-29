package com.blabla.dontruinyourlaundry.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blabla.dontruinyourlaundry.presentation.adapters.CardsListAdapter
import com.blabla.dontruinyourlaundry.databinding.FragmentClothingCardsBinding
import com.blabla.dontruinyourlaundry.domain.entity.CategoryEnum
import com.blabla.dontruinyourlaundry.presentation.viewModels.ClothingCardsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ClothingCardsFragment : Fragment() {

    private lateinit var binding: FragmentClothingCardsBinding

    private val viewModel: ClothingCardsViewModel by viewModel()

    companion object {
        const val CATEGORY_NAME = "CATEGORY_NAME"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentClothingCardsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val category = (requireArguments().getSerializable(CATEGORY_NAME)) as CategoryEnum
        val image = category.getResIcon()


        val adapter = CardsListAdapter { card ->
            val action =
                TypeOfLaundryItemsFragmentDirections.actionKindsOfThingsForLaundryToCardDetailFragment(
                    card.cardId
                )
            this.findNavController().navigate(action)
        }

        binding.recyclerViewAddedCards.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.recyclerViewAddedCards.adapter = adapter

        viewModel.allCardsByCategory(category).observe(viewLifecycleOwner) { cards ->
            if (cards.isEmpty()) {
                binding.imageTypeOfCloth.setImageResource(image)
            } else adapter.submitList(cards)
        }
    }
}



