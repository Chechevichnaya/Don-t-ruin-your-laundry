package com.blabla.dontruinyourlaundry.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blabla.dontruinyourlaundry.R
import com.blabla.dontruinyourlaundry.adapters.RecyclerViewAdapterCards
import com.blabla.dontruinyourlaundry.data.AddedCardsViewModel
import com.blabla.dontruinyourlaundry.RoomStuff.Card
import com.blabla.dontruinyourlaundry.RoomStuff.CardsApplication
import com.blabla.dontruinyourlaundry.adapters.RecyclerViewAdapterSymbolForWashing
import com.blabla.dontruinyourlaundry.data.ClothingCardsFactory
import com.blabla.dontruinyourlaundry.data.ClothingCardsViewModel
import com.blabla.dontruinyourlaundry.databinding.FragmentClothingCardsBinding
import com.blabla.dontruinyourlaundry.entity.Category
import kotlinx.coroutines.launch

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


    private lateinit var nameOfCategory: Category


    companion object {
        const val CATEGORY = "CATEGORY"
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


        val categoryImage = requireArguments().getInt(CATEGORY)
        if (viewModel.checkTableIsEmpty()) {
            view.findViewById<ImageView>(R.id.image_type_of_cloth).setImageResource(categoryImage)
        } else {

            //set adapter for recyclerView
            val recyclerView = binding.recyclerViewAddedCards
            recyclerView.layoutManager =
                LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            val dataForAdapter = viewModel.allCardsByCategory()
            // val addedCardAdapter = RecyclerViewAdapterCards(dataForAdapter)
            // recyclerView.adapter = addedCardAdapter

        }

//
//        arguments?.takeIf { it.containsKey(CATEGORY) }?.apply {
//            val category = requireArguments().getSerializable(CATEGORY)
//            //check if there is no entries in table by certain category
//            if (viewModel.checkTableIsEmpty()) {
//
//
//                Category.valueOf(category).imageResId
//                binding.imageTypeOfCloth.setImageResource(category)

//                category?.let { it: String ->
//                    Category.valueOf(
//                        it
//                    ).imageResId
//                }?.let {
//                    binding.imageTypeOfCloth.setImageResource(it)
//                }


//            } else {
//
//
//
//                //set adapter for recyclerView
//                val recyclerView = binding.recyclerViewAddedCards
//                recyclerView.layoutManager =
//                    LinearLayoutManager(context, RecyclerView.VERTICAL, false)
//                val dataForAdapter = sharedViewModel.allCardsByCategory()
//                val addedCardAdapter = RecyclerViewAdapterCards(dataForAdapter)
//                recyclerView.adapter = addedCardAdapter
//
//            }
//        }

    }
}
//   recyclerView = binding.recyclerView
//        recyclerView.layoutManager = LinearLayoutManager(requireContext())
//        val busStopAdapter = BusStopAdapter({})
//        // by passing in the stop name, filtered results are returned,
//        // and tapping rows won't trigger navigation
//        recyclerView.adapter = busStopAdapter
//        lifecycle.coroutineScope.launch {
//            viewModel.scheduleForStopName(stopName).collect() {
//                busStopAdapter.submitList(it)
//            }
//        }
