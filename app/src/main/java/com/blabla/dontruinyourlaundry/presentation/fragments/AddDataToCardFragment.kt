package com.blabla.dontruinyourlaundry.presentation.fragments

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.content.res.ResourcesCompat
import androidx.core.net.toUri
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.blabla.dontruinyourlaundry.BuildConfig
import com.blabla.dontruinyourlaundry.R
import com.blabla.dontruinyourlaundry.presentation.adapters.RecyclerViewAdapterSymbolWithMeaning
import com.blabla.dontruinyourlaundry.databinding.FragmentAddNewCardBinding
import com.blabla.dontruinyourlaundry.domain.entity.*
import com.blabla.dontruinyourlaundry.presentation.viewModels.AddDataToCardViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
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

    private var latestTmpUri: Uri? = null
    private var imageUri: String? = null

    private lateinit var binding: FragmentAddNewCardBinding

    private val viewModel: AddDataToCardViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddNewCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.addCardForEditing(args.itemId, requireContext())
        args.currentCategory?.let { viewModel.setCategory(it) }

        addInfoButton()

        setToolbar()
        getAllSymbolsFromChoosingFragmentAndAddToViewModel()

        val folderForImagesInDB = createNewFolderInFiles()

        clickOnPhoto()

        observeCardInfo()

        menuItemSave(folderForImagesInDB)

    }

    private fun addInfoButton() {
        binding.addInfo.setOnClickListener {
            when (binding.addInfo.text) {
                requireContext().getString(R.string.add_more_symbols) -> clickOnAddMoreSymbols()
                requireContext().getString(R.string.add_laundry_info) -> clickOnAddMoreSymbols()
            }
        }
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
            binding.nameOfCloth.editText?.setText(name)
        }
    }

    private fun clickOnAddMoreSymbols() {
        val listOfSelectedSymbols =
            ListOfSymbols(viewModel.getListSymbolForWashing())
        val action = AddDataToCardFragmentDirections.actionAddNewCardToAddSymbolToCard(
            selectedItems = listOfSelectedSymbols
        )
        findNavController().navigate(action)
    }

    private fun clickOnPhoto() {
        binding.itemImage.setOnClickListener {
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
                        if (checkDataForCardIsCorrect()) {
                            handleValidatedData(folderForImagesInDB)
                            true
                        } else false
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun handleValidatedData(folderForImagesInDB: File?) {
        if (viewModel.card.value != null) {
            saveCardChanges(folderForImagesInDB) {
                findNavController().popBackStack(
                    R.id.kindsOfThingsForLaundry,
                    false
                )
            }
        } else {
            addInfoOfNewCardToDataBase(folderForImagesInDB) {
                findNavController().popBackStack()
            }
        }
    }

    private fun addInfoOfNewCardToDataBase(file: File?, doOnComplete: () -> Unit) {
        if (viewModel.uri.value != null) {
            copyImageToFileForDB(file)
        }
        if (getListOfSymbolForDB().isNotEmpty() && getListOfSymbolForDB().contains(null).not()) {
            viewModel.addInfoOfNewCardToDataBase(
                nameOfCloth = getNameOfCloth(),
                imageUri = imageUri,
                listSymbols = getListOfSymbolForDB().filterNotNull(),
                doOnComplete = doOnComplete
            )
        }
    }

    private fun saveCardChanges(file: File?, doOnComplete: () -> Unit) {
        if (viewModel.uri.value != null) {
            copyImageToFileForDB(file)
        }
        if (getListOfSymbolForDB().isEmpty().not()) {
            viewModel.saveCardChanges(
                name = getNameOfCloth(),
                picture = imageUri,
                symbols = getListOfSymbolForDB().filterNotNull(),
                doOnComplete
            )
        }
    }

    private fun setToolbar() {
        setTitleInToolBar()
        binding.toolbarAddCard.apply {
            navigationIcon =
                ResourcesCompat.getDrawable(
                    requireContext().resources,
                    R.drawable.ic_baseline_close_24,
                    null
                )
            navigationIcon?.setTint(
                ResourcesCompat.getColor(
                    requireContext().resources,
                    R.color.icon_text,
                    null
                )
            )
        }
        binding.toolbarAddCard.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_addNewCard_to_kindsOfThingsForLaundry)
        }

    }

    private fun observeListOfSymbols() {
        val recyclerView = binding.addedSymbolsRecyclerView
        val adapter = RecyclerViewAdapterSymbolWithMeaning({ clickedItem ->
            viewModel.showDialog(
                clickedItem,
                requireContext()
            )
        }, TypeOfRecyclerView.ADD_SYMBOL_FRAGMENT)
        recyclerView.layoutManager = FlexboxLayoutManager(context, FlexDirection.ROW, FlexWrap.WRAP)
        recyclerView.adapter = adapter
        viewModel.listOfSymbols.observe(viewLifecycleOwner) { items ->
            binding.addInfo.text = requireContext().getString(R.string.add_more_symbols)
            adapter.submitList(items)
        }
    }

    private fun showDialog() {
        val array = arrayOf(
            requireContext().getString(R.string.from_gallery),
            requireContext().getString(R.string.from_camera)
        )
        var checkedItem = -1
        val builder = AlertDialog.Builder(requireContext(), R.style.AlertDialogTheme)
        builder.setTitle(getString(R.string.ask_about_how_to_add_photo))
            .setSingleChoiceItems(array, checkedItem) { _, which -> checkedItem = which }
            .setPositiveButton(requireContext().getString(R.string.ok_button)) { _, _ ->
                if (checkedItem != -1) {
                    handleSelectedImageSource(checkedItem)
                }
            }

            .setNeutralButton(requireContext().getString(R.string.cancel)) { _, _ -> }
        val dialog = builder.create()
        dialog.show()

        val colorButton =
            ResourcesCompat.getColor(
                requireContext().resources,
                R.color.buttons_positive_negative,
                null
            )
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(colorButton)
        dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(colorButton)

        dialog.window?.setBackgroundDrawable(
            ResourcesCompat.getDrawable(
                requireContext().resources,
                R.drawable.color_for_alertdialog,
                null
            )
        )
    }

    private fun handleSelectedImageSource(checkedItem: Int) {
        when (checkedItem) {
            FROM_GALLERY -> selectImageFromGallery()
            WITH_CAMERA -> askPermissions()
        }
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
                binding.textOnImage.isVisible = false
            }
            Glide.with(binding.itemImage.context)
                .load(newUri)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .centerCrop()
                .transform(CenterCrop(), RoundedCorners(24))
                .into(binding.itemImage)
        }
        viewModel.uri.observe(viewLifecycleOwner, uriObserver)
    }

    private fun createNewFolderInFiles(): File {
        val folderForImagesInDB = context?.getDir("images_for_DB", Context.MODE_PRIVATE)
        if (!folderForImagesInDB?.exists()!!) {
            folderForImagesInDB.mkdirs()
        }
        return folderForImagesInDB
    }

    private fun copyImageToFileForDB(file: File?) {
        val fileForImages =
            createFileWithUniqueName(file)
        fileForImages?.let {
            copyImageToFile(it)
            imageUri = fileForImages.toUri().toString()
        }
    }

    private fun checkDataForCardIsCorrect(): Boolean {
        var message = ""
        val checkIfNameEmpty = getNameOfCloth().isEmpty()
        val checkIfSymbolsEmpty = viewModel.listOfSymbols.value.orEmpty().isEmpty()
        if (checkIfNameEmpty && checkIfSymbolsEmpty) {
            message = requireContext().getString(R.string.fill_name_and_symbols)
        } else if (checkIfNameEmpty) {
            message = requireContext().getString(R.string.fill_name)
        } else if (checkIfSymbolsEmpty) {
            message = requireContext().getString(R.string.fill_symbols)
        }
        return if (message != "") {
            val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
            val dialog: AlertDialog = builder.setMessage(message)
                .setPositiveButton(requireContext().getString(R.string.ok_button)) { _, _ -> }
                .create()
            dialog.show()
            dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setTextColor(
                    ResourcesCompat.getColor(
                        requireContext().resources,
                        R.color.lilac700_light_white_dark,
                        null
                    )
                )
            false
        } else true
    }

    private fun getListOfSymbolForDB(): List<SymbolForWashingDBO?> {
        val listOfSymbols = viewModel.getListSymbolForWashing().let { list ->
            val listDBO = list.map { item -> item.toSymbolForWashingDBO(context) }
            listDBO
        }
        return listOfSymbols
    }

    private fun getNameOfCloth(): String {
        return binding.nameOfCloth.editText?.text.toString().trim()
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
    }


    private fun copyImageToFile(fileForImages: File) {
        fileForImages.outputStream().use { stream ->
            val inputStream = requireActivity()
                .contentResolver.openInputStream(viewModel.uri.value!!) ?: return
            inputStream.copyTo(stream)
            inputStream.close()
        }
    }

    private fun createFileWithUniqueName(file: File?): File? {
        val fileForImages =
            File.createTempFile(getString(R.string.photoForDB), getString(R.string.jpg), file)
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
                takeAPhoto()
            }
            ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.CAMERA
            ) -> {
                val builder = AlertDialog.Builder(requireContext())
                builder.apply {
                    setMessage(requireContext().getString(R.string.need_access_to_camera))
                    setPositiveButton(requireContext().getString(R.string.ok_button)) { _, _ ->
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
                takeAPhoto()
            }
        }

    private val takeImageResult =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
            if (isSuccess) {
                latestTmpUri?.let { uriFile ->
                    viewModel.updateUri(uriFile)
                }

            }
        }

    private fun createFile(): Uri {
        val file = File(requireActivity().filesDir, getString(R.string.tempPhotos))
        if (!file.exists()) {
            file.createNewFile()
        }
        return FileProvider.getUriForFile(
            requireActivity().applicationContext,
            "${BuildConfig.APPLICATION_ID}.provider",
            file
        )
    }

    private fun takeAPhoto() {
        lifecycleScope.launchWhenStarted {
            createFile().let { uri ->
                latestTmpUri = uri
                takeImageResult.launch(latestTmpUri)
            }
        }
    }


    private val selectImageFromGalleryResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uriGallery: Uri? ->
            val fileForImage = File(requireActivity().filesDir, getString(R.string.tempPhotos))
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


    private fun getAllSymbolsFromChoosingFragmentAndAddToViewModel() {
        val navController = findNavController()
        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<List<SymbolGuideItem.SymbolForWashing>>(
            SELECTED_SYMBOL_KEY
        )?.observe(viewLifecycleOwner) { it ->
            viewModel.addSelectedSymbols(it)
        }
    }
}










