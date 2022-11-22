package com.blabla.dontruinyourlaundry.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blabla.dontruinyourlaundry.R
import com.blabla.dontruinyourlaundry.adapters.RecyclerViewAdapterSymbolForWashing
import com.blabla.dontruinyourlaundry.data.ListOfCards
import com.blabla.dontruinyourlaundry.databinding.FragmentSimbolGuideBinding
import com.blabla.dontruinyourlaundry.entity.TypeOfRecyclerView

class SymbolGuide : Fragment() {

    private lateinit var binding: FragmentSimbolGuideBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSimbolGuideBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbarSymbolGuide.title = "Гид по символам"
        binding.toolbarSymbolGuide.navigationIcon =
            view.context.getDrawable(R.drawable.ic_arrow_back)
        binding.toolbarSymbolGuide.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        val menuHost: MenuHost = binding.toolbarSymbolGuide
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_symbol_guide, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        val listOfCardForSymbolGuide = context?.let { ListOfCards.loadListOfSymbolGuide(it) }
        val recyclerViewSymbolGuide = binding.recyclerViewSymbolGuide
        recyclerViewSymbolGuide.layoutManager =
            LinearLayoutManager(view.context, RecyclerView.VERTICAL, false)
        recyclerViewSymbolGuide.adapter =
            listOfCardForSymbolGuide?.let { list ->
                RecyclerViewAdapterSymbolForWashing(
                    list,
                    TypeOfRecyclerView.SYMBOLGUIDEFRAGMENT
                )
            }
    }
}