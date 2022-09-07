package com.blabla.dontruinyourlaundry.fragments

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blabla.dontruinyourlaundry.R
import com.blabla.dontruinyourlaundry.adapters.RecyclerViewAdapterButton
import com.blabla.dontruinyourlaundry.data.AddedCardsViewModel
import com.blabla.dontruinyourlaundry.data.CardInfo
import com.blabla.dontruinyourlaundry.data.ListOfCards
import com.blabla.dontruinyourlaundry.data.SymbolForWashing
import com.blabla.dontruinyourlaundry.databinding.FragmentAddNewCardBinding
import com.blabla.dontruinyourlaundry.entity.Category


class AddNewCardDialog : Fragment() {

    private lateinit var binding: FragmentAddNewCardBinding
    private val sharedViewModel: AddedCardsViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddNewCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dataListOfAddedSymbols = ListOfCards.loadListOfAddedSymbols()
        val addedSymbols = sharedViewModel.listOfSymbols.value
        if (addedSymbols != null) {
            dataListOfAddedSymbols.addAll(0, addedSymbols)
        }

        binding.addedSymbolsRecyclerView.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.addedSymbolsRecyclerView.adapter = RecyclerViewAdapterButton(dataListOfAddedSymbols)


        binding.toolbarAddCard.title = "Добавить новую вещь"
        binding.toolbarAddCard.navigationIcon = view.context.getDrawable(R.drawable.ic_baseline_close_24)
        binding.toolbarAddCard.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        val menuHost: MenuHost = binding.toolbarAddCard
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_add_card, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.save_button -> {
                        sharedViewModel.setName(binding.nameOfCloth.text.toString())
                        sharedViewModel.cardAreHere(true)
                        findNavController().popBackStack()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

//getting photo from gallery
        val photo = binding.itemImage
        val galleryImage = registerForActivityResult(
            ActivityResultContracts.GetContent()
        ) { photo.setImageURI(it) }
        photo.setOnClickListener {
            binding.textOnImage.text = ""
            galleryImage.launch("image/*")
        }
//        val image: Bitmap = Bitmap.createBitmap()
//        Bitmap dst = Bitmap.createBitmap(src, startX, startY, dstWidth, dstHeight)


    }

}