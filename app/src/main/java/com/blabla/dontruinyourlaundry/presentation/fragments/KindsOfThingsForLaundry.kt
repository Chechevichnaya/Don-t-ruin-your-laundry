package com.blabla.dontruinyourlaundry.presentation.fragments

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.blabla.dontruinyourlaundry.R
import com.blabla.dontruinyourlaundry.databinding.FragmentKindsOfThingsForLaundryBinding
import com.blabla.dontruinyourlaundry.domain.entity.CategoryEnum
import com.blabla.dontruinyourlaundry.presentation.viewModels.ClothingCardsViewModel
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel


class KindsOfThingsForLaundry : Fragment() {

    private lateinit var binding: FragmentKindsOfThingsForLaundryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentKindsOfThingsForLaundryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //menu
        binding.myToolbar.title = "Don't ruin your laundry"
        val menuHost: MenuHost = binding.myToolbar
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_main, menu)

            }


            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.simbol_guide -> {
                        findNavController().navigate(R.id.action_kindsOfThingsForLaundry_to_simbolGuide)
                        true
                    }
                    R.id.filter -> {
                        findNavController().navigate(R.id.action_kindsOfThingsForLaundry_to_searchDialog)
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        //Round button
        binding.fab.setOnClickListener {
            goToAddNewCardFragment(view)
        }

        binding.pager.adapter = LandryPagerAdapter(this)
        TabLayoutMediator(binding.tabs, binding.pager) { tab, position ->
            tab.text = context?.let {context -> CategoryEnum.values()[position].getName(context) }
        }.attach()
    }

    private fun goToAddNewCardFragment(view: View) {
        val currentCategory = getCurrentCategory()
        val action =
            KindsOfThingsForLaundryDirections.actionKindsOfThingsForLaundryToAddNewCard(
                title = getString(R.string.add_card_fragment),
                currentCategory = context?.let { context -> currentCategory.toCategory(context) }
            )
        view.findNavController().navigate(action)
    }

    private fun getCurrentCategory(): CategoryEnum {
        val currentCategory = when (binding.pager.currentItem) {
            0 -> CategoryEnum.CLOTH
            1 -> CategoryEnum.BAD_SHEETS
            2 -> CategoryEnum.BATH
            3 -> CategoryEnum.KITCHEN
            4 -> CategoryEnum.REST
            else -> CategoryEnum.CLOTH
        }
        return currentCategory
    }
}

class LandryPagerAdapter(val fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = CategoryEnum.values().size

    override fun createFragment(position: Int): Fragment {

        val fragment = ClothingCardsFragment()
        fragment.arguments = Bundle().apply {
            putSerializable(
                //key
                ClothingCardsFragment.CATEGORY_NAME,
                //value
                CategoryEnum.values()[position]
            )
        }
        return fragment
    }
}

