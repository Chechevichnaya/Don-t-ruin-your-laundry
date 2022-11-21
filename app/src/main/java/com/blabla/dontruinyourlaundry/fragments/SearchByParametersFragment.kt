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
import com.blabla.dontruinyourlaundry.RoomStuff.CardsApplication
import com.blabla.dontruinyourlaundry.adapters.FakeMultiholderAdapter
import com.blabla.dontruinyourlaundry.adapters.RecyclerViewAdapterSearchByParameters
import com.blabla.dontruinyourlaundry.adapters.RecyclerViewAdapterSearchParameter
import com.blabla.dontruinyourlaundry.data.*
import com.blabla.dontruinyourlaundry.databinding.FragmentSearchByParametersBinding
import com.blabla.dontruinyourlaundry.viewModels.SearchByParametersFactory
import com.blabla.dontruinyourlaundry.viewModels.SearchByParametersViewModel
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager


class SearchByParametersFragment : Fragment() {
    lateinit var binding: FragmentSearchByParametersBinding
    private val viewModel: SearchByParametersViewModel by viewModels {
        SearchByParametersFactory((activity?.application as CardsApplication).dataBase.cardsDao)
    }


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
                        getResultCards()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)


        val recyclerView = binding.recyclerSearchByParameters
        val adapter = RecyclerViewAdapterSearchParameter { clickedItem ->
            viewModel.onItemClicked(clickedItem)
        }
        recyclerView.layoutManager =
            FlexboxLayoutManager(context, FlexDirection.ROW, FlexWrap.WRAP)
        recyclerView.adapter = adapter
        viewModel.searchItems.observe(viewLifecycleOwner) { items ->
            adapter.submitList(items)
            Log.d("clicked", "observe $items")
        }

    }

    private fun getResultCards() {
        TODO("Not yet implemented")
    }
}
