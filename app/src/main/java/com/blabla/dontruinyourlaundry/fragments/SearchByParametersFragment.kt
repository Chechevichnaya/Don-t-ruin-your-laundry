package com.blabla.dontruinyourlaundry.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.net.toUri
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blabla.dontruinyourlaundry.R
import com.blabla.dontruinyourlaundry.RoomStuff.Card
import com.blabla.dontruinyourlaundry.adapters.RecyclerViewAdapterSearchByParameters
import com.blabla.dontruinyourlaundry.data.DataForSearchByParameters
import com.blabla.dontruinyourlaundry.data.ListOfSymbolsForDataBase
import com.blabla.dontruinyourlaundry.databinding.FragmentSearchByParametersBinding
import java.io.File
import java.util.*


class SearchByParametersFragment : Fragment() {
    lateinit var binding: FragmentSearchByParametersBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchByParametersBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //set upper menu
        binding.toolbarSearch.title = "Поиск по параметрам"
        binding.toolbarSearch.navigationIcon =
            view.context.getDrawable(R.drawable.ic_arrow_back)

        //go back on the first fragment without adding info in database
        binding.toolbarSearch.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        //set menu item
        val menuHost: MenuHost = binding.toolbarSearch
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_search_by_parameters, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.add_button -> {
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        val listOfCardsForSearching = DataForSearchByParameters.getData()
        val recyclerView = binding.recyclerSearchByParameters
        recyclerView.layoutManager = LinearLayoutManager(view.context, RecyclerView.VERTICAL, false)
        recyclerView.adapter = RecyclerViewAdapterSearchByParameters(listOfCardsForSearching)
    }





}
