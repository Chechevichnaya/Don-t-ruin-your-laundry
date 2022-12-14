package com.blabla.dontruinyourlaundry.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blabla.dontruinyourlaundry.R
import com.blabla.dontruinyourlaundry.adapters.CardsListAdapter
import com.blabla.dontruinyourlaundry.roomStuff.CardsApplication
import com.blabla.dontruinyourlaundry.adapters.RecyclerViewAdapterSearchParameter
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

        setToolBar(view)

        //set menu item
        val menuHost: MenuHost = binding.toolbarSearch
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_search_by_parameters, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.add_button -> {
                        if (viewModel.itemsSelected()) {
                            viewModel.processSelectedItems()
                            navigateToSearchByParametersResultFragment()
                        } else {
                            showDialogNothingSelected()
                        }
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
        }


    }

    private fun navigateToSearchByParametersResultFragment() {
        val listOfCategories = viewModel.listOfCategories.value.orEmpty()
            .map { it.toCategory(requireContext()) }.toTypedArray()
        val action =
            SearchByParametersFragmentDirections.actionSearchByParametersFragmentToSearchByParametersResultFragment(
                listOfCategories
            )
        findNavController().navigate(action)
    }

    private fun setToolBar(view: View) {
        binding.toolbarSearch.title = "Поиск по параметрам"
        binding.toolbarSearch.navigationIcon =
            view.context.getDrawable(R.drawable.ic_arrow_back)

        binding.toolbarSearch.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }


    private fun showDialogNothingSelected() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        val dialog: AlertDialog = builder.setMessage("Параметры для поиска не выбраны!")
            .setPositiveButton("Ok") { _, _ -> }
            .create()
        dialog.show()
        dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            .setTextColor(requireContext().resources.getColor(R.color.lilac_700))
    }
}
