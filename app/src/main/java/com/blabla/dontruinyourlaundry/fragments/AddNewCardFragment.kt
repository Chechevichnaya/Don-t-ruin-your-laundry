package com.blabla.dontruinyourlaundry.fragments

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
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
import com.blabla.dontruinyourlaundry.BuildConfig
import com.blabla.dontruinyourlaundry.R
import com.blabla.dontruinyourlaundry.adapters.MULTIRecyclerViewAdapterSymbolAndMeaning
import com.blabla.dontruinyourlaundry.data.ListOfSymbols
import com.blabla.dontruinyourlaundry.data.ListOfSymbolsForDataBase
import com.blabla.dontruinyourlaundry.data.SymbolForWashingDBO
import com.blabla.dontruinyourlaundry.databinding.FragmentAddNewCardBinding
import com.blabla.dontruinyourlaundry.entity.SymbolGuide
import com.blabla.dontruinyourlaundry.entity.TypeOfRecyclerView
import com.blabla.dontruinyourlaundry.roomStuff.Card
import com.blabla.dontruinyourlaundry.roomStuff.CardsApplication
import com.blabla.dontruinyourlaundry.viewModels.AddedCardsViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import java.io.File
import java.util.*


class AddNewCardFragment : Fragment() {

    private val args: AddNewCardFragmentArgs by navArgs()

    //private val TAG = this::class.java.simpleName

    private var latestTmpUri: Uri? = null
    private var imageUri: String? = null

    private lateinit var binding: FragmentAddNewCardBinding
    private val viewModel: AddedCardsViewModel by viewModels {
        AddedCardsViewModel.AddedCardsFactory(
            (activity?.application as CardsApplication)
                .dataBase.cardsDao
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddNewCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.addCardForEditing(args.itemId, requireContext())

        clickOnAddMoreSymbols(view)
        setUpperMenu(view)
        getListOfSymbolsFromChosingFragmentAndAddToViewModel()

        //creating a new folder, if it is not created yet,
        // where the last chosen photo will be kept for DB
        val folderForImagesInDB = createNewFolderInFiles()

        clickOnPhoto()

        observeCardInfo()

        //set menu item
        menuItemSave(folderForImagesInDB)
    }

    override fun onPause() {
        super.onPause()
        viewModel.setName(getNameOfCloth())
    }

    private fun observeCardInfo() {
        observeUriToSetPhoto()
        observeListOfSymbols()
        observeName()
    }

    private fun observeName() {
        viewModel.nameOfCloth.observe(viewLifecycleOwner) { name ->
            binding.nameOfCloth.setText(name)
        }
    }

    private fun clickOnAddMoreSymbols(view: View) {
        binding.addMoreSymbolButton.setOnClickListener {
            val listOfSelectedSymbols = ListOfSymbols(viewModel.listOfSymbols.value.orEmpty())
            val action = AddNewCardFragmentDirections.actionAddNewCardToAddSymbolToCard(
                selectedItems = listOfSelectedSymbols
            )
            Log.d("CHECK", "clickOnAddMoreSymbols selected $listOfSelectedSymbols")
            view.findNavController().navigate(action)
        }
    }

    private fun clickOnPhoto() {
        val photo = binding.itemImage
        photo.setOnClickListener {
            showDialog()
        }
    }

    private fun menuItemSave(folderForImagesInDB: File?) {
        val menuHost: MenuHost = binding.toolbarAddCard
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_add_card, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.save_button -> {
                        if (dataForCardCorrect()) {
                            if (viewModel.card.value != null) {
                                Log.d(
                                    "CHECK",
                                    "list after click add - ${viewModel.listOfSymbols.value}"
                                )
                                saveCardChanges(folderForImagesInDB)
                                findNavController().popBackStack(
                                    R.id.kindsOfThingsForLaundry,
                                    false
                                )
                                true
                            } else {
                                val card = saveInfoInCard(folderForImagesInDB)
                                viewModel.addNewCard(card)
                                findNavController().popBackStack()
                                true
                            }
                        } else false

                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun saveCardChanges(file: File?) {
        if (viewModel.uri.value != null) {
            copyImageToFileForDB(file)
        }

        viewModel.saveCardChanges(
            name = getNameOfCloth(),
            picture = imageUri,
            listOfSymbols = getListOfSymbolFroDB()!!,
        )
    }


    private fun setUpperMenu(view: View) {
        //set upper menu
        setTitleInUpperMenu()

        binding.toolbarAddCard.navigationIcon =
            view.context.getDrawable(R.drawable.ic_baseline_close_24)
        //go back on the first fragment without adding info in database
        binding.toolbarAddCard.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_addNewCard_to_kindsOfThingsForLaundry)
        }
    }

    private fun observeListOfSymbols() {

        val recyclerView = binding.addedSymbolsRecyclerView
        val adapter = MULTIRecyclerViewAdapterSymbolAndMeaning({ clickedItem ->
            viewModel.showDialog(
                clickedItem,
                requireContext()
            )
        }, TypeOfRecyclerView.ADD_SYMBOL_FRAGMENT)
        recyclerView.layoutManager = FlexboxLayoutManager(context, FlexDirection.ROW, FlexWrap.WRAP)
        recyclerView.adapter = adapter

        viewModel.listOfSymbols.observe(viewLifecycleOwner) { items ->
            adapter.submitList(items)
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
            if (viewModel.uri.value != null) {
                binding.textOnImage.text = ""
            }
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

    private fun saveInfoInCard(file: File?): Card? {
        if (viewModel.uri.value != null) {
            copyImageToFileForDB(file)
        }
        var newCard: Card? = null
        newCard = collectInfoForCard(newCard)
        return newCard


    }

    private fun copyImageToFileForDB(file: File?) {
        //creating file in new folder with unique name
        val fileForImages =
            createFileWithUniqueName(file)

        //copy Image to File
        fileForImages?.let {
            copyImageToFile(it)
            imageUri = fileForImages.toUri().toString()
        }
    }

    private fun dataForCardCorrect(): Boolean {
        var message = ""
        if (getNameOfCloth().isEmpty() && getListOfSymbolFroDB()?.listOfSymbols.isNullOrEmpty()) {
            message = "Заполни поле \"Название вещи\" и добавь нужные символы для ухода за вещами"
        } else if (getNameOfCloth().isEmpty()) {
            message = "Заполни поле \"Название вещи\""
        } else if (getListOfSymbolFroDB()?.listOfSymbols.isNullOrEmpty()) {
            message = "Добавь нужные символы для ухода за вещами"
        }
        return if (message != "") {
            val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
            val dialog: AlertDialog = builder.setMessage(message)
                .setPositiveButton("Ok") { _, _ -> }
                .create()
            dialog.show()
            dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setTextColor(requireContext().resources.getColor(R.color.lilac_700))
            false
        } else true

    }


    private fun collectInfoForCard(newCard: Card?): Card? {
        var newCard = newCard
        viewModel.setName(getNameOfCloth())
        args.currentCategory?.let { viewModel.setCategory(it) }
        val categoryForDB = viewModel.category.value!!.toCategoryDBO(requireContext())
        newCard =
            categoryForDB?.let { category ->
                viewModel.nameOfCloth.value?.let { name ->
                    Card(
                        id = 0,
                        name = name,
                        picture = imageUri,
                        listOfSymbols = getListOfSymbolFroDB()!!,
                        category = category
                    )
                }
            }
        return newCard
    }

    private fun getListOfSymbolFroDB(): ListOfSymbolsForDataBase? {
        val listOfSymbols = viewModel.listOfSymbols.value?.let { list ->
            val listDBO = list.map { item -> item.toSymbolForWashingDBO(context) }
            ListOfSymbolsForDataBase(listDBO as List<SymbolForWashingDBO>)
        }
        return listOfSymbols
    }

    private fun getNameOfCloth(): String {
        return binding.nameOfCloth.text.toString().trim()
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
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
                takeImageMY()
            }
        }

    private val takeImageResultMY =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
            if (isSuccess) {
                latestTmpUri?.let { uriFile ->
                    viewModel.updateUri(uriFile)
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


    //get list from ChooseSymbolsToCard
    private fun getListOfSymbolsFromChosingFragmentAndAddToViewModel() {
        val navController = findNavController()
        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<List<SymbolGuide.SymbolForWashing>>(
            "key"
        )?.observe(viewLifecycleOwner) { it ->
            Log.d("CHECK", "it - $it")
            viewModel.addSelectedSymbols(it)
        }
    }
}










