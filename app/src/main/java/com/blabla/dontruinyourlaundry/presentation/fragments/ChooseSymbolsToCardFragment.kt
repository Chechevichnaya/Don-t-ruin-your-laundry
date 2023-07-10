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
import com.blabla.dontruinyourlaundry.R
import com.blabla.dontruinyourlaundry.presentation.adapters.RecyclerViewAdapterAllSymbols
import com.blabla.dontruinyourlaundry.databinding.FragmentAddSymbolToCardBinding
import com.blabla.dontruinyourlaundry.domain.entity.SELECTED_SYMBOL_KEY
import com.blabla.dontruinyourlaundry.domain.entity.SymbolGuideItem
import com.blabla.dontruinyourlaundry.presentation.viewModels.ChooseSymbolsViewModel
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel


class ChooseSymbolsToCardFragment : Fragment() {
    private lateinit var binding: FragmentAddSymbolToCardBinding

    private val viewModel: ChooseSymbolsViewModel by viewModel()
    private val args: ChooseSymbolsToCardFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddSymbolToCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpperMenu()
        val selectedItems = args.selectedItems.list

        attachAdapterToRecyclerView(selectedItems)

        val menuHost: MenuHost = binding.toolbarAddSymbolsToCard
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_add_symbol, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.add_button -> {
                        val selectedSymbols = viewModel.getSelectedItems()
                        if (selectedSymbols.isNotEmpty()) {
                            val navController = findNavController()
                            navController.previousBackStackEntry?.savedStateHandle?.set(
                                SELECTED_SYMBOL_KEY,
                                selectedSymbols
                            )
                            findNavController().popBackStack()
                        } else {
                            showDialogNothingSelected()
                        }
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun showDialogNothingSelected() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        val dialog: AlertDialog = builder.setMessage(requireContext().getString(R.string.nothing_selected))
            .setPositiveButton(requireContext().getString(R.string.ok_button)) { _, _ -> }
            .create()

        dialog.show()
        dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            .setTextColor(ResourcesCompat.getColor(requireContext().resources,R.color.lilac_700, null ))

    }

    private fun attachAdapterToRecyclerView(selectedItems: List<SymbolGuideItem.SymbolForWashing>) {
        val recyclerViewSymbolsInAddingCad = binding.recyclerAddSymbolsToCard
        viewModel.setSelectedSymbols(selectedItems)

        val adapter = RecyclerViewAdapterAllSymbols(requireActivity()) { clickedItem ->
            viewModel.clickItem(clickedItem)
        }
        recyclerViewSymbolsInAddingCad.layoutManager =
            FlexboxLayoutManager(context, FlexDirection.ROW, FlexWrap.WRAP)
        binding.recyclerAddSymbolsToCard.adapter = adapter
        viewModel.itemsInSymbolGuide.observe(viewLifecycleOwner) { items ->
            adapter.submitList(items)
        }

    }

    private fun setUpperMenu() {
        binding.toolbarAddSymbolsToCard.title = requireContext().getString(R.string.symbol_choosing)
        binding.toolbarAddSymbolsToCard.setNavigationIcon(R.drawable.ic_baseline_close_24)
        binding.toolbarAddSymbolsToCard.navigationIcon?.setTint(ResourcesCompat.getColor(requireContext().resources,R.color.icon_text, null ))
        binding.toolbarAddSymbolsToCard.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }
}
