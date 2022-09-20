package com.blabla.dontruinyourlaundry.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blabla.dontruinyourlaundry.R
import com.blabla.dontruinyourlaundry.adapters.RecyclerViewAdapterSymbolForWashing
import com.blabla.dontruinyourlaundry.data.AddedCardsViewModel
import com.blabla.dontruinyourlaundry.data.ListOfCards
import com.blabla.dontruinyourlaundry.databinding.FragmentAddSymbolToCardBinding
import com.blabla.dontruinyourlaundry.entity.TypeOfRecyclerView

class ChooseSymbolsToCard : Fragment() {
    private lateinit var binding: FragmentAddSymbolToCardBinding
    private val viewModel: AddedCardsViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddSymbolToCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbarAddSymbolsToCard.title = "Выбери символы"
        binding.toolbarAddSymbolsToCard.setNavigationIcon(R.drawable.ic_baseline_close_24)
        binding.toolbarAddSymbolsToCard.setNavigationOnClickListener { findNavController().popBackStack() }



        val listOfCardForSymbolGuide = ListOfCards.loadListOfSymbolGuide()
        val recyclerViewSymbolsInAddingCad = binding.recyclerAddSymbolsToCard
        recyclerViewSymbolsInAddingCad.layoutManager =
            LinearLayoutManager(view.context, RecyclerView.VERTICAL, false)
        val adapter = RecyclerViewAdapterSymbolForWashing(
            listOfCardForSymbolGuide,
            TypeOfRecyclerView.ADDSYMBOLFRAGMENT
        )
        binding.recyclerAddSymbolsToCard.adapter = adapter

        val menuHost: MenuHost = binding.toolbarAddSymbolsToCard
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_add_symbol, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.add_button -> {

                        val selectedItems = adapter.data.map { it.symbolsByCategory }.flatten()
                            .filter { it.selected }.toMutableList()


                        //viewModel.setListOfAddedSymbols(selectedItems)

//                        val result = selectedItems
//                        // Use the Kotlin extension in the fragment-ktx artifact
//                        setFragmentResult("requestKey", bundleOf("bundleKey" to result))
                        findNavController().popBackStack()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

    }

}