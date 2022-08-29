package com.blabla.dontruinyourlaundry.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.blabla.dontruinyourlaundry.R
import com.blabla.dontruinyourlaundry.adapters.RecyclerViewAdapterCards
import com.blabla.dontruinyourlaundry.data.ListOfCards
import com.blabla.dontruinyourlaundry.databinding.FragmentClothingCardsBinding

class ClothingCardsFragment : Fragment() {

    private lateinit var binding: FragmentClothingCardsBinding

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
        val dataCard = ListOfCards.loadListOfCards()
        binding.recyclerViewClothing.adapter = RecyclerViewAdapterCards(dataCard)
        val myValue = requireArguments().getInt(CATEGORY_NAME)
  /*      view.findViewById<ImageView>(R.id.image_type_of_cloth).setImageResource(
            getString(requireArguments().getInt(CATEGORY_NAME))
        )*/
        view.findViewById<ImageView>(R.id.image_type_of_cloth).setImageResource(myValue)
    }
}
