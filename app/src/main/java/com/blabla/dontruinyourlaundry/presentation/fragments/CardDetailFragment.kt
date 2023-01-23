package com.blabla.dontruinyourlaundry.presentation.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.core.net.toUri
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.blabla.dontruinyourlaundry.R
import com.blabla.dontruinyourlaundry.presentation.adapters.MULTIRecyclerViewAdapterSymbolAndMeaning
import com.blabla.dontruinyourlaundry.data.dataBase.Card
import com.blabla.dontruinyourlaundry.databinding.FragmentCardDetailBinding
import com.blabla.dontruinyourlaundry.domain.entity.CategoryEnum
import com.blabla.dontruinyourlaundry.domain.entity.TypeOfRecyclerView
import com.blabla.dontruinyourlaundry.presentation.viewModels.CardDetailViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel


class CardDetailFragment : Fragment() {

    private lateinit var binding: FragmentCardDetailBinding

    lateinit var card: Card

    private val navigationArgs: CardDetailFragmentArgs by navArgs()

    private val viewModel: CardDetailViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCardDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getCardById(navigationArgs.id).observe(this.viewLifecycleOwner) { selectedCard ->
            viewModel.addListOfSymbolsToViewModel(selectedCard)
            card = selectedCard
            bind(card)
        }

        setToolBar()

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

    private fun setToolBar() {
        binding.toolbarCardDetail.apply {
            title = getString(R.string.title_fragment_card_detail)
            navigationIcon =
                ResourcesCompat.getDrawable(
                    requireContext().resources,
                    R.drawable.ic_arrow_back,
                    null
                )
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
    }


    private fun bind(card: Card) {
        if (card.picture == null) {
            val imageRes = CategoryEnum.values().find { it == card.category }?.getResIcon()
            if (imageRes != null) {
                binding.itemImage.apply {
                    setImageResource(imageRes)
                    setColorFilter(
                        ResourcesCompat.getColor(
                            requireContext().resources,
                            R.color.icon_of_category_in_card,
                            null
                        )
                    )
                }
            }
        } else {
            Glide.with(binding.itemImage.context)
                .load(card.picture.toUri())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .transform(CenterCrop(), RoundedCorners(24))
                .into(binding.itemImage)
        }
        binding.apply {
            nameOfCloth.text = card.name
            val recyclerView = binding.addedSymbolsRecyclerView
            val adapter = MULTIRecyclerViewAdapterSymbolAndMeaning(
                {},
                TypeOfRecyclerView.CARD_DETAIL_FRAGMENT
            )
            recyclerView.layoutManager =
                FlexboxLayoutManager(context, FlexDirection.ROW, FlexWrap.WRAP)
            recyclerView.adapter = adapter
            viewModel.addListOfSymbolsToViewModel(card)
            viewModel.listOfSymbols.observe(viewLifecycleOwner)
            { items -> adapter.submitList(items) }
            editItem.setOnClickListener { editCard() }
        }
    }

    private fun showConfirmationDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        val dialog: AlertDialog = builder
            .setMessage(getString(R.string.delete_question))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.no)) { _, _ -> }
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                viewModel.deleteInfo((card.cardId)) { findNavController().popBackStack() }
            }
            .create()
        dialog.show()
        val colorButton = ResourcesCompat.getColor(
            requireContext().resources,
            R.color.buttons_positive_negative,
            null
        )

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(colorButton)
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(colorButton)
    }


    private fun editCard() {
        val action =
            CardDetailFragmentDirections.actionCardDetailFragmentToAddNewCard(
                title = getString(R.string.edit_fragment),
                itemId = card.cardId
            )
        findNavController().navigate(action)
    }
}

