package com.blabla.dontruinyourlaundry.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        setRecyclerViewTextParameters()

    }

    private fun setRecyclerViewTextParameters() {
        val recyclerView = binding.textParameters
        val adapter = RecyclerViewAdapterSearchParameter {}
        recyclerView.layoutManager =
            FlexboxLayoutManager(context, FlexDirection.ROW, FlexWrap.WRAP)
        recyclerView.adapter = adapter
        viewModel.getListSearchItemsWithSelectedItems().observe(viewLifecycleOwner) { items ->
            adapter.submitList(items)
        }

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