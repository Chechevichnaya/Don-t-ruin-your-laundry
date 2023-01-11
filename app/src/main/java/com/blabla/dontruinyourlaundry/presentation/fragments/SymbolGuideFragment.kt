package com.blabla.dontruinyourlaundry.presentation.fragments

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.window.layout.WindowMetricsCalculator
import com.blabla.dontruinyourlaundry.R
import com.blabla.dontruinyourlaundry.presentation.adapters.MULTURecyclerViewAdapterAllSymbols
import com.blabla.dontruinyourlaundry.databinding.FragmentSimbolGuideBinding
import com.blabla.dontruinyourlaundry.presentation.viewModels.SymbolGuideViewModel
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel

class SymbolGuideFragment : Fragment() {

    private lateinit var binding: FragmentSimbolGuideBinding

    private val viewModel: SymbolGuideViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSimbolGuideBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpperMenu(view)
        attachAdapterToRecyclerView()
    }

    private fun attachAdapterToRecyclerView() {
        val recyclerViewSymbolGuide = binding.recyclerViewSymbolGuide

        val adapter = MULTURecyclerViewAdapterAllSymbols(requireActivity()) { clickedItem ->
            viewModel.onClicked(clickedItem, requireContext())
        }
        recyclerViewSymbolGuide.layoutManager =
            FlexboxLayoutManager(context, FlexDirection.ROW, FlexWrap.WRAP)







        recyclerViewSymbolGuide.adapter = adapter
        viewModel.symbolsInGuide.observe(viewLifecycleOwner) { items ->
            adapter.submitList(items)
        }
    }

    private fun setUpperMenu(view: View) = binding.toolbarSymbolGuide.apply {
        title = requireContext().getString(R.string.symbol_guide)
        navigationIcon =
            view.context.getDrawable(R.drawable.ic_arrow_back)
        navigationIcon?.setTint(requireContext().resources.getColor(R.color.icon_text))
        setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_symbol_guide, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}

