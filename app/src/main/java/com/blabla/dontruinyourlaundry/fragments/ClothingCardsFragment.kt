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
import com.blabla.dontruinyourlaundry.data.ClothingCardsFactory
import com.blabla.dontruinyourlaundry.data.ClothingCardsViewModel
import com.blabla.dontruinyourlaundry.databinding.FragmentClothingCardsBinding
import com.blabla.dontruinyourlaundry.entity.Category

class ClothingCardsFragment : Fragment() {

    private lateinit var binding: FragmentClothingCardsBinding

    //    private val viewModel: ClothingCardsViewModel by lazy {
//        ViewModelProvider(
//            this,
//            ClothingCardsFactory(
//                (requireContext().applicationContext as CardsApplication).dataBase.cardsDao
//            )
//        ).get(ClothingCardsViewModel::class.java)
//    }
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

//        val answer = viewModel.checkDBIsEmpty(category)
//        if (viewModel.checkDBIsEmpty(category)) {
//            //set full screen picture of cloth type
//            view.findViewById<ImageView>(R.id.image_type_of_cloth).setImageResource(image)
//            Log.d("cardByCategory", "category - $category, DB is empty? $answer ")
//        } else {
//            Log.d("cardByCategory", "category - $category, DB is empty? $answer ")
////            //set adapter for recyclerView
////            val recyclerView = binding.recyclerViewAddedCards
////            recyclerView.layoutManager =
////                LinearLayoutManager(context, RecyclerView.VERTICAL, false)
////            //getting data(cards) for adapter and making it just List, not LiveData List
////            val dataForAdapter = viewModel.allCardsByCategory(category)?.value
////            val addedCardAdapter = dataForAdapter?.let { RecyclerViewAdapterCards(it) }
////            recyclerView.adapter = addedCardAdapter
//        }


//        arguments?.takeIf { it.containsKey(CATEGORY) }?.apply {
//            val category = requireArguments().getSerializable(CATEGORY)
//            //check if there is no entries in table by certain category
//            if (viewModel.checkTableIsEmpty()) {
//
//
//                Category.valueOf(category).imageResId
//                binding.imageTypeOfCloth.setImageResource(category)
//
//                category?.let { it: String ->
//                    Category.valueOf(
//                        it
//                    ).imageResId
//                }?.let {
//                    binding.imageTypeOfCloth.setImageResource(it)
//                }
//
//
//            }
//        }


    }
}



