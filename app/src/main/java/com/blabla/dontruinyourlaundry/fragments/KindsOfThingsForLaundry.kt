package com.blabla.dontruinyourlaundry

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.os.bundleOf
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.blabla.dontruinyourlaundry.adapters.RecyclerViewAdapterSymbolGuide
import com.blabla.dontruinyourlaundry.data.AddedCardsViewModel
import com.blabla.dontruinyourlaundry.data.CardInfo
import com.blabla.dontruinyourlaundry.databinding.FragmentKindsOfThingsForLaundryBinding
import com.blabla.dontruinyourlaundry.entity.Category
import com.blabla.dontruinyourlaundry.entity.TypeOfRecyclerView
import com.blabla.dontruinyourlaundry.fragments.ClothingCardsFragment
import com.google.android.material.tabs.TabLayoutMediator


class KindsOfThingsForLaundry : Fragment() {


    private lateinit var binding: FragmentKindsOfThingsForLaundryBinding
    private val sharedViewModel: AddedCardsViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentKindsOfThingsForLaundryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("TEST", "category - ${sharedViewModel.category.value}")
        Log.d("TEST", "symbols - ${sharedViewModel.listOfSymbols.value}")
        Log.d("TEST", "name - ${sharedViewModel.nameCloth.value}")
        Log.d("TEST", "cards - ${sharedViewModel.cardsExist.value}")
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
            val currentCategory = when (binding.pager.currentItem) {
                0 -> Category.CLOTH
                1 -> Category.BAD_SHEETS
                2 -> Category.BATH
                3 -> Category.KITCHEN
                4 -> Category.REST
                else -> Category.CLOTH
            }
            sharedViewModel.setCategory(currentCategory)
//            val result = currentCategory
//            setFragmentResult(
//                "requestKeyFromKindsOfThingsForLaundry",
//                bundleOf("KeyFromKindsOfThingsForLaundry" to result)
//            )
            view.findNavController().navigate(R.id.action_kindsOfThingsForLaundry_to_addNewCard)


        }

        binding.pager.adapter = LandryPagerAdapter(this)
        TabLayoutMediator(binding.tabs, binding.pager) { tab, position ->
            tab.text = getString(Category.values()[position].nameId)
        }.attach()


    }

}

class LandryPagerAdapter(val fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = Category.values().size

    override fun createFragment(position: Int): Fragment {

        val fragment = ClothingCardsFragment()

        fragment.arguments = Bundle().apply {
            putString(ClothingCardsFragment.CATEGORY_NAME,
            Category.values()[position].name)
        }
        return fragment
    }
}

