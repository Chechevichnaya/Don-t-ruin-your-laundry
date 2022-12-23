package com.blabla.dontruinyourlaundry.presentation.fragments

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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.blabla.dontruinyourlaundry.BuildConfig
import com.blabla.dontruinyourlaundry.R
import com.blabla.dontruinyourlaundry.presentation.adapters.MULTIRecyclerViewAdapterSymbolAndMeaning
import com.blabla.dontruinyourlaundry.domain.entity.ListOfSymbols
import com.blabla.dontruinyourlaundry.domain.entity.SymbolForWashingDBO
import com.blabla.dontruinyourlaundry.databinding.FragmentAddNewCardBinding
import com.blabla.dontruinyourlaundry.domain.entity.SymbolGuide
import com.blabla.dontruinyourlaundry.domain.entity.TypeOfRecyclerView
import com.blabla.dontruinyourlaundry.presentation.viewModels.AddDataToCardViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import java.io.File
import java.util.*
import org.koin.androidx.viewmodel.ext.android.viewModel

const val FROM_GALLERY = 0
const val WITH_CAMERA = 1

class AddDataToCardFragment : Fragment() {

    private val args: AddDataToCardFragmentArgs by navArgs()

    //private val TAG = this::class.java.simpleName

    private var latestTmpUri: Uri? = null
    private var imageUri: String? = null

    private lateinit var binding: FragmentAddNewCardBinding

    private val viewModel: AddDataToCardViewModel by viewModel()

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
        args.currentCategory?.let { viewModel.setCategory(it) }

        clickOnAddMoreSymbols(view)
        setToolBar(view)
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

    fun addInfoToDataBase(){

    }

    private fun observeName() {
        viewModel.nameOfCloth.observe(viewLifecycleOwner) { name ->
            binding.nameOfCloth.setText(name)
        }
    }

    private fun clickOnAddMoreSymbols(view: View) {
        binding.addMoreSymbolButton.setOnClickListener {
            val listOfSelectedSymbols = ListOfSymbols(viewModel.listOfSymbols.value.orEmpty())
            val action = AddDataToCardFragmentDirections.actionAddNewCardToAddSymbolToCard(
                selectedItems = listOfSelectedSymbols
            )
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
                                saveCardChanges(folderForImagesInDB)
                                findNavController().popBackStack(
                                    R.id.kindsOfThingsForLaundry,
                                    false
                                )
                                true
                            } else {
                                Log.d("CHECK", "I am inside!")
                                addInfoToDataBase(folderForImagesInDB)
//                                findNavController().popBackStack()
                                true
                            }
                        } else false

                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun addInfoToDataBase(file: File?) {
        Log.d("CHECK", "I am more than inside")
        if (viewModel.uri.value != null) {
            copyImageToFileForDB(file)
        }
        viewModel.addInfoToDataBase(getNameOfCloth(), imageUri, getListOfSymbolForDB()!! as List<SymbolForWashingDBO> )
    }

    private fun saveCardChanges(file: File?) {
        if (viewModel.uri.value != null) {
            copyImageToFileForDB(file)
        }

        viewModel.saveCardChanges(
            name = getNameOfCloth(),
            picture = imageUri,
            symbols = getListOfSymbolForDB()!! as List<SymbolForWashingDBO>
        )
    }


    private fun setToolBar(view: View) {
        setTitleInToolBar()
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
        val array = arrayOf("Из галереи", "С помощью камеры")
        var checkedItem = -1
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.ask_about_how_to_add_photo))
            .setSingleChoiceItems(array, checkedItem)
            { _, which -> checkedItem = which }
            .setPositiveButton("Ok") { _, _ ->
                if (checkedItem != -1) {
                    when (checkedItem) {
                        FROM_GALLERY -> {
                            selectImageFromGallery()
                        }
                        WITH_CAMERA -> {
                            askPermissions()
                        }
                    }
                }
            }

            .setNeutralButton("Отмена") { _, _ -> }
        val dialog = builder.create()
        dialog.show()

        val colorButton = resources.getColor(R.color.lilac_700)
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(colorButton)
        dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(colorButton)
    }



    private fun setTitleInToolBar() {
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

//    private fun saveInfoInCard(file: File?): Card? {
//        if (viewModel.uri.value != null) {
//            copyImageToFileForDB(file)
//        }
//        viewModel.createNewCard(
//            getNameOfCloth(), imageUri
//        )
//
//        var newCard: Card? = null
//        newCard = collectInfoForCard(newCard)
//        return newCard
//    }

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
        val checkIfNameEmpty = getNameOfCloth().isEmpty()
        val checkIfSymbolsEmpty = viewModel.listOfSymbols.value.isNullOrEmpty()
        if (checkIfNameEmpty && checkIfSymbolsEmpty)
//            getListOfSymbolFroDB()?.listOfSymbols.isNullOrEmpty())
        {
            message = "Заполни поле \"Название вещи\" и добавь нужные символы для ухода за вещами"
        } else if (checkIfNameEmpty) {
            message = "Заполни поле \"Название вещи\""
        } else if (checkIfSymbolsEmpty) {
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


//    private fun collectInfoForCard(newCard: Card?): Card? {
//        var newCard = newCard
//        viewModel.setName(getNameOfCloth())
//        args.currentCategory?.let { viewModel.setCategory(it) }
//        val categoryForDB = viewModel.category.value!!.toCategoryDBO(requireContext())
//        newCard =
//            categoryForDB?.let { category ->
//                viewModel.nameOfCloth.value?.let { name ->
//                    Card(
//                        cardId = 0,
//                        name = name,
//                        picture = imageUri,
//                        listOfSymbols = getListOfSymbolForDB()!!,
//                        category = category
//                    )
//                }
//            }
//        return newCard
//    }

    private fun getListOfSymbolForDB(): List<SymbolForWashingDBO?>? {
        val listOfSymbols = viewModel.listOfSymbols.value?.let { list ->
            val listDBO = list.map { item -> item.toSymbolForWashingDBO(context) }
            listDBO
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


    private fun getListOfSymbolsFromChosingFragmentAndAddToViewModel() {
        val navController = findNavController()
        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<List<SymbolGuide.SymbolForWashing>>(
            "key"
        )?.observe(viewLifecycleOwner) { it ->
            viewModel.addSelectedSymbols(it)
        }
    }
}










