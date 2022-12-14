package com.blabla.dontruinyourlaundry.presentation.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blabla.dontruinyourlaundry.R
import com.blabla.dontruinyourlaundry.presentation.adapters.CardsListAdapter
//import com.blabla.dontruinyourlaundry.presentation.viewModels.SearchFactory
import com.blabla.dontruinyourlaundry.presentation.viewModels.SearchByNameViewModel
import com.blabla.dontruinyourlaundry.databinding.FragmentSearchBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchByNameFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding

//    private val viewModel: SearchViewModel by viewModels {
//        SearchFactory((activity?.application as AppApplication).dataBase.cardsDao)
//    }
private val viewModel: SearchByNameViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //set upper menu
        binding.toolbarSearch.navigationIcon =
            view.context.getDrawable(R.drawable.ic_arrow_back)

        //go back on the first fragment without adding info in database
        binding.toolbarSearch.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        //set adapter for recyclerview with added symbols
        binding.recyclerViewAddedCards.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        val adapter = CardsListAdapter { card ->
            val action =
                SearchByNameFragmentDirections.actionSearchFragmentToCardDetailFragment(card.cardId)
            this.findNavController().navigate(action)
        }

        binding.recyclerViewAddedCards.adapter = adapter
        viewModel.getAllNames().observe(viewLifecycleOwner) { names ->
            val popUpMenuAdapter =
                ArrayAdapter(requireContext(), R.layout.array_adapter_item, names.toTypedArray())
            binding.whatToSearch.setAdapter(popUpMenuAdapter)
            binding.whatToSearch.dropDownVerticalOffset = 0
            binding.whatToSearch.setOnItemClickListener { parent, view, position, id ->
                performSearch(adapter)
            }
        }

        binding.whatToSearch.setOnEditorActionListener { v, actionId, event ->
            when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    performSearch(adapter)
                    true
                }
                else -> false
            }
        }
    }

    private fun performSearch(adapter: CardsListAdapter) {
        hideKeyboard(binding.whatToSearch)
        val searchByName = binding.whatToSearch.text.toString().trim()
        viewModel.getCardsByName(searchByName).observe(viewLifecycleOwner) { cards ->
            if (cards.isNotEmpty()) {
                adapter.submitList(cards)
            } else {
                Toast.makeText(
                    requireContext(),
                    "???????????? ???? ??????????????",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun hideKeyboard(view: View) {
        view.clearFocus()
        val imm = view.context?.getSystemService(Context.INPUT_METHOD_SERVICE)
                as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }


}
