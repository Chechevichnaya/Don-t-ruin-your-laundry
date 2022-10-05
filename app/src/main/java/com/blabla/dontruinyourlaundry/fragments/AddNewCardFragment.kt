package com.blabla.dontruinyourlaundry.fragments

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blabla.dontruinyourlaundry.BuildConfig
import com.blabla.dontruinyourlaundry.R
import com.blabla.dontruinyourlaundry.RoomStuff.Card
import com.blabla.dontruinyourlaundry.RoomStuff.CardsApplication
import com.blabla.dontruinyourlaundry.adapters.RecyclerViewAdapterButton
import com.blabla.dontruinyourlaundry.data.*
import com.blabla.dontruinyourlaundry.databinding.FragmentAddNewCardBinding
import com.bumptech.glide.Glide
import com.google.android.gms.cast.framework.media.ImagePicker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File


class AddNewCardFragment : Fragment() {

    private val TAG = this::class.java.simpleName

    private var latestTmpUri: Uri? = null


    private lateinit var binding: FragmentAddNewCardBinding
    private val viewModel: AddedCardsViewModel by viewModels {
        AddedCardsFactory((activity?.application as CardsApplication).dataBase.cardsDao)
    }

    //val viewModel: MyViewModel by viewModels { MyViewModelFactory(getApplication(), "my awesome param") }
    private val cropActivityResultContract = object : ActivityResultContract<Any?, Uri?>() {
        override fun createIntent(context: Context, input: Any?): Intent {
            TODO()
        }

        override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
            TODO("Not yet implemented")
        }

    }


//
//    private fun getTmpFileUri(): Uri {
//        val tmpFile =
//            File.createTempFile("tmp_image_file", ".jpg", requireActivity().cacheDir).apply {
//                createNewFile()
//                deleteOnExit()
//            }
//        return FileProvider.getUriForFile(
//            requireActivity().applicationContext,
//            "${BuildConfig.APPLICATION_ID}.provider",
//            tmpFile
//        )
//    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("test", " onCreateView()")
        //get list from ChooseSymbolsToCard
        val navController = findNavController()
        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<List<SymbolForWashing>>(
            "key"
        )?.observe(viewLifecycleOwner) {
            viewModel.addSelectedSymbols(it)
            Log.d(
                "test",
                "list of symbols in addnewcard: ${viewModel.listOfSymbols.value.toString()}"
            )
        }
        binding = FragmentAddNewCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("test", " onViewCreated()")


//        setFragmentResultListener("requestKey") { requestKey, bundle ->
        // selectedSymbols - list of symbols that user chose
//            val selectedSymbols = bundle.getParcelableArrayList<SymbolForWashing>("bundleKey")
//                ?.toList()
//    }
//            if (selectedSymbols != null) {
//                viewModel.addSelectedSymbols(selectedSymbols)
//                Log.d("test", viewModel.listOfSymbols.value.toString())
//            }


        //set adapter for recyclerview with added symbols
        binding.addedSymbolsRecyclerView.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        //get list of added symbols. There is always at least one symbol (add symbol)
        viewModel.listOfSymbols.observe(viewLifecycleOwner) { symbols ->

            binding.addedSymbolsRecyclerView.adapter = RecyclerViewAdapterButton(symbols)

            Log.d("test", "dataForAdapter: $symbols")
        }

        //set upper menu
        binding.toolbarAddCard.title = "Добавить новую вещь"
        binding.toolbarAddCard.navigationIcon =
            view.context.getDrawable(R.drawable.ic_baseline_close_24)
        //go back on the first fragment without adding info in database
        binding.toolbarAddCard.setNavigationOnClickListener {
            findNavController().popBackStack()
            //viewModel.deleteSelectedSymbols()
            Log.d(
                "test",
                "delete selected items. oldlist:${viewModel.listOfSymbols.value.toString()}"
            )
        }

        //set menu item
        val menuHost: MenuHost = binding.toolbarAddCard
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_add_card, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.save_button -> {
                        val nameOfCloth = binding.nameOfCloth.text.toString()
//                        val picture
//                        val category
//                        val listOfSymbols
//                        val card = Card(nameOfCloth, )

                        //viewModel.addNewCard(card)

                        findNavController().popBackStack()

                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        val photo = binding.itemImage
        photo.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setMessage(getString(R.string.ask_about_how_to_add_photo))
                .setPositiveButton(getString(R.string.from_gallery)) { _, _ ->
                    selectImageFromGallery()
                }
                .setNegativeButton(getString(R.string.from_camera)) { _, _ -> takeImageMY() }
                .show()
        }
//        val galleryImage = registerForActivityResult(
//            ActivityResultContracts.GetContent()
//        ) { photo.setImageURI(it) }
//        photo.setOnClickListener {
//            binding.textOnImage.text = ""
//            galleryImage.launch("image/*")
//        }
    }

//    override fun onDestroy() {
//        super.onDestroy()
//        Log.d("test", "onDestroy()")
//    }
//
//    override fun onDetach() {
//        super.onDetach()
//        Log.d("test", " onDetach()")
//    }
//
//    override fun onStop() {
//        super.onStop()
//        Log.d("test", " onStop()")
//    }
//
//    override fun onPause() {
//        super.onPause()
//        Log.d("test", " onPause()")
//    }


    private val takeImageResultMY = registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
        if (isSuccess) {
            latestTmpUri?.let { uri ->
                Log.d("text", "takeImageResultMY() ${uri.toString()}")

                binding.textOnImage.text = ""
                Glide.with(binding.itemImage.context)
                    .load(uri)
                    .centerCrop()
                    .into(binding.itemImage)
            }

        }
    }

    private fun makeFileMY(): Uri{
        val file = File.createTempFile("chosed_photo", ".jpg", requireActivity().filesDir )
        return FileProvider.getUriForFile(
            requireActivity().applicationContext,
            "${BuildConfig.APPLICATION_ID}.provider",
            file
        )
    }

    private fun takeImageMY() {
        lifecycleScope.launchWhenStarted {
            makeFileMY().let { uri ->
                latestTmpUri = uri
                Log.d("text", "takeImageMY() ${latestTmpUri.toString()}" )
                takeImageResultMY.launch(uri)
            }
        }
    }

    val selectImageFromGalleryResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let { binding.itemImage.setImageURI(uri) }
        }

    private fun selectImageFromGallery() = selectImageFromGalleryResult.launch("image/*")


}






