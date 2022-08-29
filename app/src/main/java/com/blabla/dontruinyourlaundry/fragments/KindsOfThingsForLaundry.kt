package com.blabla.dontruinyourlaundry

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.blabla.dontruinyourlaundry.databinding.FragmentKindsOfThingsForLaundryBinding
import com.blabla.dontruinyourlaundry.entity.Category
import com.blabla.dontruinyourlaundry.fragments.ClothingCardsFragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_kinds_of_things_for_laundry.*


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
                    R.id.help_with_app -> {
                        findNavController().navigate(R.id.action_kindsOfThingsForLaundry_to_howToUseAppDialog)
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)


        //Round button
        binding.fab.setOnClickListener {
            view.findNavController().navigate(R.id.action_kindsOfThingsForLaundry_to_addNewCard)
        }

        binding.pager.adapter = LandryPagerAdapter(this)
        TabLayoutMediator(binding.tabs, binding.pager) { tab, position ->
            tab.text = getString(Category.values().get(position).nameId)
        }.attach()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
    }
}

class LandryPagerAdapter(val fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = Category.values().size

    override fun createFragment(position: Int): Fragment {

        val fragment = ClothingCardsFragment()

        fragment.arguments = Bundle().apply {
            putInt(
                ClothingCardsFragment.CATEGORY_NAME,
                Category.values().get(position).imageResId
            )
        }
        return fragment
    }
}

