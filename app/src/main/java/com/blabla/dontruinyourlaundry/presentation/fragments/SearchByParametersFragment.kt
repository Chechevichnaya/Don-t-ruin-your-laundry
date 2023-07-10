package com.blabla.dontruinyourlaundry.presentation.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.fragment.DialogFragmentNavigator
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import com.blabla.dontruinyourlaundry.R
import com.blabla.dontruinyourlaundry.databinding.FragmentSearchByParametersBinding
import com.blabla.dontruinyourlaundry.domain.entity.SearchScreenItem
import com.blabla.dontruinyourlaundry.presentation.adapters.RecyclerViewAdapterSearchParameter
import com.blabla.dontruinyourlaundry.presentation.viewModels.SearchByParametersViewModel
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel


class SearchByParametersFragment : Fragment() {
    lateinit var binding: FragmentSearchByParametersBinding
    private val viewModel: SearchByParametersViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchByParametersBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolBar()

        val menuHost: MenuHost = binding.toolbarSearch
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_search_by_parameters, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.add_button -> {
                        if (viewModel.checkIfItemsSelected()) {
                           viewModel.handleSelectedParameters(requireContext()) {
                               navigateToSearchByParametersResultFragment()
                           }
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
        val observerForRecyclerView = Observer { items: List<SearchScreenItem> ->
            adapter.submitList(items)
        }
        viewModel.searchItems.observe(viewLifecycleOwner, observerForRecyclerView)
    }

    private fun navigateToSearchByParametersResultFragment() {
        val listOfParameters = viewModel.listOfSearchParametersEnum.toTypedArray()
            val action =
                SearchByParametersFragmentDirections.actionSearchByParametersFragmentToSearchByParametersResultFragment(
                    listOfParameters
                )
            navigate(action)
    }

    private fun Fragment.navigate(directions: NavDirections) {
        val controller = findNavController()
        val currentDestination =
            (controller.currentDestination as? FragmentNavigator.Destination)?.className
                ?: (controller.currentDestination as? DialogFragmentNavigator.Destination)?.className
        if (currentDestination == this.javaClass.name) {
            controller.navigate(directions)
        }
    }

    private fun setToolBar() = binding.toolbarSearch.apply {
        title =
            requireContext().getString(R.string.search_by_parameters_title)
        navigationIcon =
            ResourcesCompat.getDrawable(requireContext().resources, R.drawable.ic_arrow_back, null)
        navigationIcon?.setTint(
            ResourcesCompat.getColor(
                requireContext().resources,
                R.color.icon_text,
                null
            )
        )
        setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }
    private fun showDialogNothingSelected() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        val dialog: AlertDialog =
            builder.setMessage(requireContext().getString(R.string.no_search_terms))
                .setPositiveButton(requireContext().getString(R.string.ok_button)) { _, _ -> }
                .create()
        dialog.show()
        dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            .setTextColor(
                ResourcesCompat.getColor(
                    requireContext().resources,
                    R.color.buttons_positive_negative,
                    null
                )
            )
    }
}
