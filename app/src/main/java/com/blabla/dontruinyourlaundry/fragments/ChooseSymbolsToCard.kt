package com.blabla.dontruinyourlaundry.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blabla.dontruinyourlaundry.R
import com.blabla.dontruinyourlaundry.adapters.RecyclerViewAdapterSymbolForWashing
import com.blabla.dontruinyourlaundry.viewModels.ChooseSymbolsViewModel
import com.blabla.dontruinyourlaundry.data.ListOfCards
import com.blabla.dontruinyourlaundry.databinding.FragmentAddSymbolToCardBinding
import com.blabla.dontruinyourlaundry.entity.TypeOfRecyclerView



class ChooseSymbolsToCard : Fragment() {
    private val TAG = this::class.java.simpleName
    private lateinit var binding: FragmentAddSymbolToCardBinding
    private val viewModel: ChooseSymbolsViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddSymbolToCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //set navigation menu
        binding.toolbarAddSymbolsToCard.title = "Выбери символы"
        binding.toolbarAddSymbolsToCard.setNavigationIcon(R.drawable.ic_baseline_close_24)

        //go back without changing
        binding.toolbarAddSymbolsToCard.setNavigationOnClickListener {
            findNavController().popBackStack() }


        //get list of symbols for laundry guide
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
                            .filter { it.selected }.toList()

                        Log.d("test", "data that pass from chhoseSymbolsFragment ${selectedItems}")

                        viewModel.setSelectedSymbols(selectedItems)
                        val navController = findNavController()
                        navController.previousBackStackEntry?.savedStateHandle?.set("key", selectedItems)
                        findNavController().popBackStack()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

    }


}
