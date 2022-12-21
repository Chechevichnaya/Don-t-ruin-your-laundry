package com.blabla.dontruinyourlaundry.presentation.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blabla.dontruinyourlaundry.R
import com.blabla.dontruinyourlaundry.presentation.adapters.CardsListAdapter
import com.blabla.dontruinyourlaundry.databinding.FragmentSearchByParametersResultBinding
import com.blabla.dontruinyourlaundry.data.AppApplication
import com.blabla.dontruinyourlaundry.presentation.viewModels.SearchByParametersResultViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchByParametersResultFragment : Fragment() {

    lateinit var binding: FragmentSearchByParametersResultBinding
    private val args: SearchByParametersResultFragmentArgs by navArgs()
//    private val viewModel: SearchByParametersResultViewModel by viewModels {
//        SearchByParametersResultViewModel.SearchByParametersResultFactory((activity?.application as AppApplication).dataBase.cardsDao)
//    }
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
//        val listOfCategories =
//            args.listOfCategories.toMutableList().map { it.toCategoryDBO(requireContext()) }
//        viewModel.setListOfCategories(listOfCategories as List<CategoryEnum>)
        val listOfParameters = args.listOfParameters.toMutableList()
        Log.d("RESULT", "args $listOfParameters")
        viewModel.setListOfParameters(listOfParameters)
        setAdapterToCards()

    }

    fun setAdapterToCards() {
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
            viewModel.setListCardResult(cards)
            Log.d("RESULT", "submit list $cards")
            Log.d("RESULT", "setListCardResult(cards) ${viewModel.listOfCardsResult.value}")
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