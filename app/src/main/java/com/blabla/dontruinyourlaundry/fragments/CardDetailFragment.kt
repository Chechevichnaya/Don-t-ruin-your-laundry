package com.blabla.dontruinyourlaundry.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.net.toUri
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blabla.dontruinyourlaundry.R
import com.blabla.dontruinyourlaundry.RoomStuff.Card
import com.blabla.dontruinyourlaundry.RoomStuff.CardsApplication
import com.blabla.dontruinyourlaundry.adapters.RecyclerViewAdapterSymbolAndMeaning
import com.blabla.dontruinyourlaundry.data.*
import com.blabla.dontruinyourlaundry.databinding.FragmentCardDetailBinding
import com.blabla.dontruinyourlaundry.entity.TypeOfRecyclerView
import com.blabla.dontruinyourlaundry.viewModels.CardDetailFactory
import com.blabla.dontruinyourlaundry.viewModels.CardDetailViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy


class CardDetailFragment : Fragment() {

    private lateinit var binding: FragmentCardDetailBinding

    lateinit var card: Card

    private val navigationArgs: CardDetailFragmentArgs by navArgs()

    private val viewModel: CardDetailViewModel by viewModels {
        CardDetailFactory((activity?.application as CardsApplication).dataBase.cardsDao)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCardDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = navigationArgs.id
        viewModel.getCard(id).observe(this.viewLifecycleOwner) { selectedCard ->
            card = selectedCard
            bind(card)
        }


        //set upper menu
        binding.toolbarCardDetail.title = "Подробнее о карточке"
        binding.toolbarCardDetail.navigationIcon =
            view.context.getDrawable(R.drawable.ic_arrow_back)
        //go back on the first fragment without adding info in database
        binding.toolbarCardDetail.setNavigationOnClickListener {
            findNavController().popBackStack()
        }


        // set menu item
        val menuHost: MenuHost = binding.toolbarCardDetail
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_card_detail, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.delete_button -> {
                        showConfirmationDialog()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun bind(card: Card) {
        if (card.picture == null) {
            binding.itemImage.setImageResource(ImageByCategory().getImageByCategory(card.category))
        } else {
            Glide.with(binding.itemImage.context)
                .load(card.picture.toUri())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(binding.itemImage)
        }
        binding.apply {
            nameOfCloth.text = card.name
            binding.addedSymbolsRecyclerView.layoutManager =
                LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            val list = card.listOfSymbols.listOfSymbols
            binding.addedSymbolsRecyclerView.adapter =
                RecyclerViewAdapterSymbolAndMeaning(list, TypeOfRecyclerView.CARDDETAILFRAGMENT)
            editItem.setOnClickListener { editCard() }
        }
    }

    private fun showConfirmationDialog() {
        AlertDialog.Builder(requireContext())
            .setMessage(getString(R.string.delete_question))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.no)) { _, _ -> }
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                deleteCard()
            }
            .show()
    }

    private fun deleteCard() {
        viewModel.deleteCard(card)
        findNavController().popBackStack()
    }

    private fun editCard() {
        val action =
            CardDetailFragmentDirections.actionCardDetailFragmentToAddNewCard(
                title = getString(R.string.edit_fragment),
                itemId = card.id
            )
        findNavController().navigate(action)
    }
}

