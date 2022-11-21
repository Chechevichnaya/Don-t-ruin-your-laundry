package com.blabla.dontruinyourlaundry.fragments

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blabla.dontruinyourlaundry.BuildConfig
import com.blabla.dontruinyourlaundry.R
import com.blabla.dontruinyourlaundry.RoomStuff.Card
import com.blabla.dontruinyourlaundry.RoomStuff.CardsApplication
import com.blabla.dontruinyourlaundry.adapters.RecyclerViewAdapterSymbolAndMeaning
import com.blabla.dontruinyourlaundry.data.*
import com.blabla.dontruinyourlaundry.databinding.FragmentAddNewCardBinding
import com.blabla.dontruinyourlaundry.entity.TypeOfRecyclerView
import com.blabla.dontruinyourlaundry.viewModels.AddedCardsFactory
import com.blabla.dontruinyourlaundry.viewModels.AddedCardsViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import java.io.File
import java.util.*


class AddNewCardFragment : Fragment() {

    private val args: AddNewCardFragmentArgs by navArgs()

    //private val TAG = this::class.java.simpleName

    private var latestTmpUri: Uri? = null
    private var imageUri: String? = null

//    lateinit var card: Card
//    private var newCard: Card? = null


    private lateinit var binding: FragmentAddNewCardBinding
    private val viewModel: AddedCardsViewModel by viewModels {
        AddedCardsFactory((activity?.application as CardsApplication).dataBase.cardsDao)
    }

//    private val cropActivityResultContract = object : ActivityResultContract<Any?, Uri?>() {
//        override fun createIntent(context: Context, input: Any?): Intent {
//            TODO()
//        }
//
//        override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
//            TODO("Not yet implemented")
//        }
//
//    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("test", " onCreateView()")
        getListOfSymbolsAndAddToViewModel()
        binding = FragmentAddNewCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("test", " onViewCreated()")

        binding.backround.setOnClickListener {
            view.findNavController().navigate(R.id.action_addNewCard_to_addSymbolToCard)
        }

        val cardId = args.itemId
        //check is it completely new card or editing existing card
        if (cardId > 0) {
            showCardInfo(cardId)
        }

        //creating a new folder, if it is not created yet, where the last chosen photo will be kept for DB
        val folderForImagesInDB = createNewFolderInFiles()

        //tracking updating uri to change photo in imageView
        observeUriToSetPhoto()

        observeListOfSymbols()

        //set upper menu
        setTitleInUpperMenu()



        binding.toolbarAddCard.navigationIcon =
            view.context.getDrawable(R.drawable.ic_baseline_close_24)
        //go back on the first fragment without adding info in database
        binding.toolbarAddCard.setNavigationOnClickListener {
            findNavController().popBackStack()
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
                        val newCard = saveInfo(folderForImagesInDB)
                        if (cardId > 0) {
                            updateItem(newCard)
                            true
                        } else {
//                            val newCard = saveInfo(folderForImagesInDB)
//                            if (viewModel.uri.value != null) {
//                                //creating file in new folder with unique name
//                                val fileForImages =
//                                    File.createTempFile("photoforDB", ".jpg", folderForImagesInDB)
//                                        .apply {
//                                            createNewFile()
//                                        }
//                                fileForImages.outputStream().use { stream ->
//                                    requireActivity().contentResolver.openInputStream(viewModel.uri.value!!)
//                                        ?.copyTo(stream)
//                                }
//                                imageUri = fileForImages.toUri().toString()
//                            }
//                            //collecting info for card
//                            val nameOfCloth =
//                                binding.nameOfCloth.text.toString().trim()
//                                    .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
//                            val category = args.currentCategory
//                            viewModel.deleteInListSymbolAdd()
//                            val listOfSymbols = viewModel.listOfSymbols.value?.let { list ->
//                                ListOfSymbolsForDataBase(
//                                    list.toList()
//                                )
//                            }
//                            if (category != null) {
//                                val card = Card(
//                                    id = 0,
//                                    name = nameOfCloth,
//                                    picture = imageUri,
//                                    listOfSymbols = listOfSymbols!!,
//                                    category = category
//                                )

                            //adding info of card to database
                            viewModel.addNewCard(newCard)

                            findNavController().popBackStack()
                            true
                        }
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        val photo = binding.itemImage
        photo.setOnClickListener {
            showDialog()
        }
    }

    private fun observeListOfSymbols() {
        //set adapter for recyclerview with added symbols
        binding.addedSymbolsRecyclerView.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        //get list of added symbols. There is always at least one symbol (add symbol)
        viewModel.listOfSymbols.observe(viewLifecycleOwner) { symbols ->
            binding.addedSymbolsRecyclerView.adapter =
                RecyclerViewAdapterSymbolAndMeaning(symbols, TypeOfRecyclerView.ADDSYMBOLFRAGMENT)
        }
    }

    private fun showDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.ask_about_how_to_add_photo))

            .setPositiveButton(getString(R.string.from_gallery)) { _, _ ->
                selectImageFromGallery()
            }
            .setNeutralButton(getString(R.string.from_camera)) { _, _ ->
                askPermissions()
            }
            .show()
    }

    private fun setTitleInUpperMenu() {
        val edit = getString(R.string.edit_fragment)
        val add = getString(R.string.add_card_fragment)
        binding.toolbarAddCard.title = when (args.title) {
            edit -> edit
            add -> add
            else -> ""
        }
    }

    private fun observeUriToSetPhoto() {
        val uriObserver = Observer<Uri> { newUri ->
            binding.textOnImage.text = ""
            Glide.with(binding.itemImage.context)
                .load(newUri)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .centerCrop()
                .into(binding.itemImage)
        }
        viewModel.uri.observe(viewLifecycleOwner, uriObserver)
    }

    private fun createNewFolderInFiles(): File? {
        val folderForImagesInDB = context?.getDir("images_for_DB", Context.MODE_PRIVATE)
        if (!folderForImagesInDB?.exists()!!) {
            folderForImagesInDB.mkdirs()
        }
        return folderForImagesInDB
    }

    private fun showCardInfo(cardId: Long) {
        var card: Card
        viewModel.getCard(cardId).observe(viewLifecycleOwner) { selectedCard ->
            card = selectedCard
            bind(card)
        }
    }

    private fun updateItem(card: Card?) {
        card?.let { viewModel.updateCard(card) }
        val action = AddNewCardFragmentDirections.actionAddNewCardToClothingCardsFragment()
        findNavController().navigate(action)
    }

    private fun saveInfo(file: File?): Card? {
        if (viewModel.uri.value != null) {

            //creating file in new folder with unique name
            val fileForImages =
                createFileWithUniqueName(file)

            //copy Image to File
            fileForImages?.let {
                copyImageToFile(it)
                imageUri = fileForImages.toUri().toString()
            }
        }

        var newCard: Card? = null
        //collecting info for card
        newCard = fillCard(newCard)
        return newCard
    }

    private fun fillCard(newCard: Card?): Card? {
        var newCard1 = newCard
        val nameOfCloth =
            binding.nameOfCloth.text.toString().trim()
                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
        val category = args.currentCategory
        viewModel.deleteInListSymbolAdd()
        val listOfSymbols = viewModel.listOfSymbols.value?.let { list ->
            ListOfSymbolsForDataBase(
                list.toList()
            )
        }

        if (category != null) {
            newCard1 = Card(
                id = 0,
                name = nameOfCloth,
                picture = imageUri,
                listOfSymbols = listOfSymbols!!,
                category = category
            )
        }
        return newCard1
    }

    private fun copyImageToFile(fileForImages: File) {
        fileForImages.outputStream().use { stream ->
            requireActivity().contentResolver.openInputStream(viewModel.uri.value!!)
                ?.copyTo(stream)
        }
    }

    private fun createFileWithUniqueName(file: File?): File? {
        val fileForImages =
            File.createTempFile("photoforDB", ".jpg", file)
                .apply {
                    createNewFile()
                }
        return fileForImages
    }


    private fun askPermissions() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                takeImageMY()
            }
            ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.CAMERA
            ) -> {
                val builder = AlertDialog.Builder(requireContext())
                builder.apply {
                    setMessage("Приложению требуется доступ к камере, чтобы сделать фото")
                    setPositiveButton("Ok") { _, _ ->
                        requestCameraPermission.launch(
                            Manifest.permission.CAMERA
                        )
                    }
                    create()
                    show()
                }
            }
            else -> {
                requestCameraPermission.launch(
                    Manifest.permission.CAMERA
                )
            }
        }
    }

    private val requestCameraPermission =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Log.i("Permission: ", "Granted")
                takeImageMY()
            } else {
                Log.i("Permission: ", "Denied")
            }
        }

    private val takeImageResultMY =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
            if (isSuccess) {
                latestTmpUri?.let { uriFile ->
                    viewModel.updateUri(uriFile)
                    Log.d("text", "uriUpdated ${viewModel.uri.value}")
                }

            }
        }

    private fun makeFileMY(): Uri {
        //creating file with name, that is not unique, so new file will be overwritten
        val file1 = File(requireActivity().filesDir, "Palma.jpg")
        if (!file1.exists()) {
            file1.createNewFile()
        }
        return FileProvider.getUriForFile(
            requireActivity().applicationContext,
            "${BuildConfig.APPLICATION_ID}.provider",
            file1
        )
    }

    private fun takeImageMY() {
        lifecycleScope.launchWhenStarted {
            makeFileMY().let { uri ->
                latestTmpUri = uri

                // latestTmpUri - content uri
                takeImageResultMY.launch(latestTmpUri)
            }
        }
    }


    private val selectImageFromGalleryResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uriGallery: Uri? ->
            val fileForImage = File(requireActivity().filesDir, "Palma.jpg")
            if (!fileForImage.exists()) {
                fileForImage.createNewFile()
            }
            fileForImage.outputStream().use { stream ->
                requireActivity().contentResolver.openInputStream(uriGallery!!)?.copyTo(stream)
            }
            viewModel.updateUri(fileForImage.toUri())
        }

    private fun selectImageFromGallery() {
        lifecycleScope.launchWhenStarted {
            selectImageFromGalleryResult.launch("image/*")
        }
    }

    private fun bind(card: Card) {
        binding.apply {
            nameOfCloth.setText(card.name, TextView.BufferType.SPANNABLE)
            addedSymbolsRecyclerView.layoutManager =
                LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            val list = card.listOfSymbols.listOfSymbols
            binding.addedSymbolsRecyclerView.adapter =
                RecyclerViewAdapterSymbolAndMeaning(list, TypeOfRecyclerView.ADDSYMBOLFRAGMENT)
        }
        if (card.picture != null) {
            binding.textOnImage.text = ""
            Glide.with(binding.itemImage.context)
                .load(card.picture.toUri())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(binding.itemImage)
        }
    }

    private fun addLastSymbolToCardList(list: List<SymbolForWashing>): List<SymbolForWashing> {
        val listNew = list.toMutableList()
        listNew.addAll(ListOfCards.loadListWithOneLastSymbol())
        return listNew
    }

    //get list from ChooseSymbolsToCard
    private fun getListOfSymbolsAndAddToViewModel() {
        val navController = findNavController()
        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<List<SymbolForWashing>>(
            "key"
        )?.observe(viewLifecycleOwner) {
            viewModel.addSelectedSymbols(it)
        }
    }
}










