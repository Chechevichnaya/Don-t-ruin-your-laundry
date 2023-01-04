package com.blabla.dontruinyourlaundry.presentation.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blabla.dontruinyourlaundry.R
import com.blabla.dontruinyourlaundry.presentation.adapters.CardsListAdapter
import com.blabla.dontruinyourlaundry.databinding.FragmentSearchByParametersResultBinding
import com.blabla.dontruinyourlaundry.presentation.adapters.RecyclerViewAdapterSearchParameter
import com.blabla.dontruinyourlaundry.presentation.viewModels.SearchByParametersResultViewModel
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchByParametersResultFragment : Fragment() {

    lateinit var binding: FragmentSearchByParametersResultBinding
    private val args: SearchByParametersResultFragmentArgs by navArgs()

    private val viewModel: SearchByParametersResultViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchByParametersResultBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolBar(view)

        viewModel.setListOfSelectedParameters(args.listOfParameters.toMutableList())
        setAdapterToCards()
        //setRecyclerViewTextParameters()
        setMenuItem()


    }

    private fun setMenuItem() {
        //set menu item
        val menuHost: MenuHost = binding.toolbarSearch
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_search_by_parameter_result, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.condition -> {
                        showDialog()
//                        findNavController()
//                            .navigate(R.id.action_searchByParametersResultFragment_to_knowAboutConditionOfSearching)
                        true
                    }
                    else -> false
                }
            }


        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

//    private fun setRecyclerViewTextParameters() {
//        val recyclerView =
//        val adapter = RecyclerViewAdapterSearchParameter {}
//        recyclerView.layoutManager =
//            FlexboxLayoutManager(context, FlexDirection.ROW, FlexWrap.WRAP)
//        recyclerView.adapter = adapter
//        viewModel.getListSearchItemsWithSelectedItems().observe(viewLifecycleOwner) { items ->
//            adapter.submitList(items)
//        }
//
//    }

    private fun showDialog() {
        // val dialog = AlertDialog.Builder(this)
        //    val view = layoutInflater.inflate(R.layout.d_manage_people, null)
        //    dialog.setView(view)
        //
        //    view.manage_people_title.text = "Manage People"
        //
        //    val adapter = ManagePeopleAdapter(result)
        //    view.people_list.adapter = adapter
        //    view.people_list.layoutManager = LinearLayoutManager(this)
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
//        val dialog = AlertDialog.Builder(requireContext())
        val view = layoutInflater.inflate(R.layout.search_by_parameter_dialog, null)
//        val dialog: AlertDialog.Builder = builder.setView(view)
//        dialog.setView(view)
        val adapter = RecyclerViewAdapterSearchParameter {}
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view_dialog)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = FlexboxLayoutManager(context, FlexDirection.ROW, FlexWrap.WRAP)
        viewModel.getListSearchItemsWithSelectedItems().observe(viewLifecycleOwner) { items ->
            adapter.submitList(items)
        }
//        dialog
//            .setTitle("Условия поиска")
        val dialog: AlertDialog = builder.setView(view)
            .setPositiveButton("Ok") { _, _ ->  }
            .create()
        dialog.show()

//        dialog.getButton(AlertDialog.BUTTON_POSITIVE)
//            .setTextColor(requireContext().resources.getColor(R.color.lilac_700))

//        val dialog: AlertDialog = builder
//            .setTitle("Условия поиска")
//            .setView(R.layout.search_by_parameter_dialog)
//            .setPositiveButton("Ok") { _, _ ->  }
//            .create()
//        dialog.show()
        dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            .setTextColor(requireContext().resources.getColor(R.color.lilac_700))
    }

    private fun setAdapterToCards() {
        val adapter = CardsListAdapter { card ->
            val action =
                SearchByParametersResultFragmentDirections.actionSearchByParametersResultFragmentToCardDetailFragment(
                    card.cardId
                )
            this.findNavController().navigate(action)
        }
        binding.recyclerSearchByParameters.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.recyclerSearchByParameters.adapter = adapter
        viewModel.getListOfCards().observe(viewLifecycleOwner) { cards ->
            adapter.submitList(cards)
        }
    }

    private fun setToolBar(view: View) {
        binding.toolbarSearch.title = "Поиск по параметрам"
        binding.toolbarSearch.navigationIcon =
            view.context.getDrawable(R.drawable.ic_arrow_back)



        binding.toolbarSearch.setNavigationOnClickListener {
            findNavController().popBackStack()
            findNavController().popBackStack()
        }
    }


}