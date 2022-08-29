package com.blabla.dontruinyourlaundry.fragments

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blabla.dontruinyourlaundry.R
import com.blabla.dontruinyourlaundry.adapters.RecyclerViewAdapterButton
import com.blabla.dontruinyourlaundry.data.Cardinfo
import com.blabla.dontruinyourlaundry.data.ListOfCards
import com.blabla.dontruinyourlaundry.databinding.FragmentAddNewCardBinding
import com.blabla.dontruinyourlaundry.databinding.FragmentKindsOfThingsForLaundryBinding


class AddNewCardDialog : Fragment() {

    private lateinit var binding: FragmentAddNewCardBinding
//    var pickedPhoto: Uri? = null
//    var pickedPhotoBitMap: Bitmap? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddNewCardBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dataListOfAddedSymbols = ListOfCards.loadListOfAddedSymbols()
        binding.addedSymbolsRecyclerView.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.addedSymbolsRecyclerView.adapter = RecyclerViewAdapterButton(dataListOfAddedSymbols)


        binding.toolbarAddCard.title = "Добавить новую вещь"
        binding.toolbarAddCard.setNavigationIcon(view.context.getDrawable(R.drawable.ic_arrow_back))
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

                        true
                    }
                    R.id.delete_button -> {

                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)


        // var listOfCards = ListOfCards.loadListOfCards()
//        binding.closeButton.setOnClickListener { dismiss() }
//        binding.safeButton.setOnClickListener {
//            val outputNameofCloth = binding.nameOfCloth.text.toString()
//            dismiss()
//        }
//        binding.deleteButton.setOnClickListener {
//            binding.nameOfCloth.setText("")
//            binding.itemImage.setImageResource(R.drawable.image_gallery)
//            dismiss()
//        }

        val photo = binding.itemImage
        val galleryImage = registerForActivityResult(
            ActivityResultContracts.GetContent()
        ) { photo.setImageURI(it) }
        photo.setOnClickListener { galleryImage.launch("image/*") }


    }


}