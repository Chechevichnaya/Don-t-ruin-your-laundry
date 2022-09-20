package com.blabla.dontruinyourlaundry.fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blabla.dontruinyourlaundry.R
import com.blabla.dontruinyourlaundry.adapters.RecyclerViewAdapterButton
import com.blabla.dontruinyourlaundry.data.AddedCardsViewModel
import com.blabla.dontruinyourlaundry.data.ListOfCards
import com.blabla.dontruinyourlaundry.databinding.FragmentAddNewCardBinding
import com.google.android.gms.cast.framework.media.ImagePicker


class AddNewCardDialog : Fragment() {

    private lateinit var binding: FragmentAddNewCardBinding
    private val sharedViewModel: AddedCardsViewModel by activityViewModels()

    private val cropActivityResultContract = object : ActivityResultContract<Any?, Uri?>(){
        override fun createIntent(context: Context, input: Any?): Intent {
            TODO()
        }

        override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
            TODO("Not yet implemented")
        }

    }


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
        binding.toolbarAddCard.navigationIcon =
            view.context.getDrawable(R.drawable.ic_baseline_close_24)
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
                        sharedViewModel.addNewCard()
//                        sharedViewModel.setName(binding.nameOfCloth.text.toString())
//                        sharedViewModel.cardAreHere(true)
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



//


    }

}