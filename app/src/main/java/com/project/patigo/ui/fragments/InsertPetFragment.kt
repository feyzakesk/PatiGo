package com.project.patigo.ui.fragments

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import coil.load
import coil.transform.CircleCropTransformation
import com.bumptech.glide.Glide
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import com.project.patigo.R
import com.project.patigo.data.entity.Pet
import com.project.patigo.data.firebase.FirebaseFirestoreResult
import com.project.patigo.databinding.BottomSheetDialogBinding
import com.project.patigo.databinding.FragmentErrorBottomSheetBinding
import com.project.patigo.databinding.FragmentHomeBinding
import com.project.patigo.databinding.FragmentInsertPetBinding
import com.project.patigo.databinding.FragmentPetBinding
import com.project.patigo.ui.viewmodels.InsertPetFragmentViewModel
import com.project.patigo.ui.viewmodels.PetFragmentViewModel
import com.project.patigo.utils.generateRandomString
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InsertPetFragment : Fragment() {

    private var _binding: FragmentInsertPetBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: InsertPetFragmentViewModel
    private var imagePicker: ImageView? = null
    private var petPict: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        val tempViewModel: InsertPetFragmentViewModel by viewModels()
        viewModel = tempViewModel
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        viewModel.currentUser()
        super.onResume()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentInsertPetBinding.inflate(inflater, container, false)
        val view = binding.root
        imagePicker=binding.petPictureImageView
        var type: String? = null
        var gender: Boolean? = null


        val listType = listOf("Köpek", "Kedi", "Kuş", "Kemirgen", "Sürüngen", "Diğer")
        val typeAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, listType)
        binding.petTypeAutoCompleteTextView.setAdapter(typeAdapter)
        binding.petTypeAutoCompleteTextView.setOnItemClickListener { adapterView, view, i, l ->
            type = adapterView.getItemAtPosition(i).toString()
        }

        val genderList = listOf("Erkek", "Dişi")
        val genderAdapter =
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                genderList
            )
        binding.petGenderAutoCompleteTextView.setAdapter(genderAdapter)
        binding.petGenderAutoCompleteTextView.setOnItemClickListener { adapterView, view, i, l ->
            val result = adapterView.getItemAtPosition(i).toString()
            gender = (result == "Erkek")
        }

        binding.petPictureImageView.setOnClickListener {
            showBottomSheetDialog()
        }

        binding.petAgeEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // İşlem yapmanıza gerek yok
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // İşlem yapmanıza gerek yok
            }

            override fun afterTextChanged(s: Editable?) {
                if (s != null && s.startsWith("0") && s.length > 1) {

                    binding.petAgeEditText.setText(s.substring(1))
                    binding.petAgeEditText.setSelection(binding.petAgeEditText.text.length) // İmleci metnin sonuna taşı
                }
            }
        })

        binding.petWeightEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // İşlem yapmanıza gerek yok
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // İşlem yapmanıza gerek yok
            }

            override fun afterTextChanged(s: Editable?) {
                if (s != null && s.startsWith("0") && s.length > 1) {

                    binding.petWeightEditText.setText(s.substring(1))
                    binding.petWeightEditText.setSelection(binding.petWeightEditText.text.length) // İmleci metnin sonuna taşı
                }
            }
        })

        binding.insertPetButton.setOnClickListener {
            Log.e("Tag", "setOnclick")
            checkValidation(
                R.drawable.outline_info_24,
            ) {
                if (petPict != null) {
                    viewModel.uploadPicture(petPict!!) { url ->
                        Log.e("Tag", "petpick")
                        viewModel.insertPet(
                            Pet(
                                petId = generateRandomString(8),
                                petName = binding.petNameEditText.text.toString().trim(),
                                petWeight = binding.petWeightEditText.text.toString().toDouble(),
                                petAge = binding.petAgeEditText.text.toString().toInt(),
                                petInfo = binding.petInfoEditText.text.toString().trim(),
                                userId = viewModel.firebaseUser.value!!.uid,
                                petType = type!!,
                                petGender = gender!!,
                                petPicture = url!!
                            )
                        ) { result ->
                            when (result) {
                                is FirebaseFirestoreResult.Success<*> -> {
                                    showErrorBottomSheetDialog(
                                        R.drawable.success_gif_im,
                                        "Dost ekleme işlemi başarıyla gerçekleşti."
                                    )
                                    Handler(Looper.getMainLooper()).postDelayed({
                                        // Bu blok 3 saniye sonra çalışacak
                                        // Şu anki Activity'i bitirerek önceki sayfaya dön

                                        findNavController().popBackStack()
                                    }, 3000)
                                }

                                is FirebaseFirestoreResult.Failure -> {
                                    showErrorBottomSheetDialog(
                                        R.drawable.failure_gif_im,
                                        "Bir hata meydana geldi: ${result.error}"
                                    )
                                }

                            }

                        }
                    }

                }


            }


        }

        return view

    }

    private fun checkValidation(resInt: Int, onSuccessful: () -> Unit) {
        if (binding.petNameEditText.text.isNullOrBlank() || binding.petWeightEditText.text.isNullOrBlank() || binding.petAgeEditText.text.isNullOrBlank() || binding.petTypeAutoCompleteTextView.text.isNullOrBlank() || binding.petGenderAutoCompleteTextView.text.isNullOrBlank() || petPict ==null) {
            with(binding) {
                if (petNameEditText.text.isNullOrBlank()) petNameEditText.error =
                    "İsim bilgisini lütfen boş bırakmayınız."
                if (petWeightEditText.text.isNullOrBlank()) petWeightEditText.error =
                    "Kilo bilgisini lütfen boş bırakmayınız."
                if (petAgeEditText.text.isNullOrBlank()) petAgeEditText.error =
                    "Yaş bilgisini lütfen boş bırakmayınız."
                if (petTypeAutoCompleteTextView.text.isNullOrBlank()) petTypeAutoCompleteTextView.error =
                    "Tür bilgisini lütfen boş bırakmayınız"
                if (petGenderAutoCompleteTextView.text.isNullOrBlank()) petGenderAutoCompleteTextView.error =
                    "Cinsiyet bilgisini lütfen boş bırakmayınız"
                if (petPict == null) {
                    showErrorBottomSheetDialog(resInt, "Lütfen dostunuzun bir fotoğrafını ekleyiniz.")
                }
            }
        } else {
            onSuccessful()

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showErrorBottomSheetDialog(resInt: Int, info: String) {
        val dialog = BottomSheetDialog(requireContext())

        val bottomSheetBinding = FragmentErrorBottomSheetBinding.inflate(
            LayoutInflater.from(requireContext()),
            null,
            false
        )

        Glide.with(requireContext()).load(resInt)
            .into(bottomSheetBinding.errorImage);
        bottomSheetBinding.errorDescription.text = info
        dialog.setCancelable(true)
        dialog.setContentView(bottomSheetBinding.root)
        dialog.show()
    }

    private fun showBottomSheetDialog() {
        val dialog = BottomSheetDialog(requireContext())
        val bottomSheetBinding = BottomSheetDialogBinding.inflate(
            LayoutInflater.from(requireContext()),
            null,
            false
        )
        bottomSheetBinding.cameraButton.setOnClickListener {
            dialog.dismiss()
            ImagePicker.with(this).cameraOnly().crop().maxResultSize(400, 400).start()
        }
        bottomSheetBinding.galleryButton.setOnClickListener {
            dialog.dismiss()
            ImagePicker.with(this).galleryOnly().galleryMimeTypes(arrayOf("image/*")).crop()
                .maxResultSize(400, 400).start()
        }

        dialog.setCancelable(true)
        dialog.setContentView(bottomSheetBinding.root)
        dialog.show()

    }




    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK && requestCode== ImagePicker.REQUEST_CODE) {
            imagePicker?.setImageURI(data?.data)
            petPict=getBitmapFromUri(data?.data)

        }
    }

    private fun getBitmapFromUri(uri: Uri?): Bitmap? {
        uri ?: return null
        return context?.contentResolver?.openInputStream(uri)?.use { inputStream ->
            BitmapFactory.decodeStream(inputStream)
        }
    }




}
