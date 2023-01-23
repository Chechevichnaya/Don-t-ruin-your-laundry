package com.blabla.dontruinyourlaundry.presentation.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blabla.dontruinyourlaundry.R
import com.blabla.dontruinyourlaundry.databinding.FragmentSearchByParametersResultBinding
import com.blabla.dontruinyourlaundry.presentation.adapters.CardsListAdapter
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
    ): View {
        binding = FragmentSearchByParametersResultBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolBar()

        viewModel.setListOfSelectedParameters(args.listOfParameters.toMutableList())
        setAdapterToCards()
        setMenuItem()


    }

    private fun setMenuItem() {
        val menuHost: MenuHost = binding.toolbarSearch
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_search_by_parameter_result, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.condition -> {
                        showDialog()
//
                        true
                    }
                    else -> false
                }
            }


        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun showDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext(), R.style.AlertDialogTheme)
        val view = layoutInflater.inflate(R.layout.search_by_parameter_dialog, null)
        val adapter = RecyclerViewAdapterSearchParameter {}
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view_dialog)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = FlexboxLayoutManager(context, FlexDirection.ROW, FlexWrap.WRAP)
        viewModel.getListSearchItemsWithSelectedItems().observe(viewLifecycleOwner) { items ->
            adapter.submitList(items)
        }
        val dialog: AlertDialog = builder.setView(view)
            .setPositiveButton(requireContext().getString(R.string.ok_button)) { _, _ -> }
            .create()
        dialog.show()
        dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            .setTextColor(ResourcesCompat.getColor(requireContext().resources,R.color.buttons_positive_negative, null ))
        dialog.window?.setBackgroundDrawable(ResourcesCompat.getDrawable(requireContext().resources,R.drawable.color_for_alertdialog, null ))
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

    private fun setToolBar() {
        binding.toolbarSearch.title = requireContext().getString(R.string.search_result)
        binding.toolbarSearch.navigationIcon =
            ResourcesCompat.getDrawable(requireContext().resources,R.drawable.ic_arrow_back, null )
        binding.toolbarSearch.setNavigationOnClickListener {
            findNavController().popBackStack()
            findNavController().popBackStack()
        }
    }


}