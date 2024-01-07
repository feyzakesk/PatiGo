package com.project.patigo.ui.fragments

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.project.patigo.MainActivity
import com.project.patigo.R
import com.project.patigo.data.entity.User
import com.project.patigo.data.firebase.FirebaseFirestoreResult
import com.project.patigo.databinding.BottomSheetDialogBinding
import com.project.patigo.databinding.FragmentErrorBottomSheetBinding
import com.project.patigo.databinding.FragmentProfileBinding
import com.project.patigo.ui.viewmodels.ProfileFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var user: User? = null
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ProfileFragmentViewModel
    private var profilePict: Bitmap? = null
    private var imagePicker: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: ProfileFragmentViewModel by viewModels()
        viewModel = tempViewModel
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        Log.e("TAG","onCreateView")
        viewModel.getUser()
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        binding.scrollView2.visibility = View.GONE
        binding.progressBar3.visibility = View.VISIBLE
        binding.profilePictureImageView.setOnClickListener {
            showBottomSheetDialog()
        }
        imagePicker=binding.profilePictureImageView
        binding.profileUpdateButton.setOnClickListener {
            Log.e("TAG", "set onclick listener")
            checkValidation(
                R.drawable.outline_info_24,
                if (user?.userPicture != null || profilePict != null) "Lütfen gerekli yerleri doldurunuz." else "Lütfen profil fotoğrafını boş bırakmayınız"
            ) {

                user.let {
                    if (profilePict != null){
                        viewModel.uploadPicture(profilePict!!){url->
                            viewModel.updateUser(
                                user!!.userId,
                                hashMapOf(
                                    "userName" to binding.profileNameEditText.text.toString().trim(),
                                    "userSurname" to binding.profileSurnameEditText.text.toString()
                                        .trim(),
                                    "userPhoneNumber" to binding.profilePhoneNumberEditText.text.toString()
                                        .trim(),
                                    "userAddress" to binding.profileAdressEditText.text.toString()
                                        .trim(),
                                    "userPicture" to url!!
                                )
                            ) { result ->
                                when (result) {
                                    is FirebaseFirestoreResult.Success<*> -> {
                                        showErrorBottomSheetDialog(
                                            R.drawable.success_gif_im,
                                            "Güncelleme işlemi başarıyla gerçekleşti."
                                        )
                                    }

                                    is FirebaseFirestoreResult.Failure -> {
                                        showErrorBottomSheetDialog(
                                            R.drawable.failure_gif_im,
                                            "Bir hata meydana geldi: ${result.error}"
                                        )
                                    }

                                }
                                Log.e("TAG", result.toString())

                            }
                        }

                    }else{
                        viewModel.updateUser(
                            user!!.userId,
                            hashMapOf(
                                "userName" to binding.profileNameEditText.text.toString().trim(),
                                "userSurname" to binding.profileSurnameEditText.text.toString()
                                    .trim(),
                                "userPhoneNumber" to binding.profilePhoneNumberEditText.text.toString()
                                    .trim(),
                                "userAddress" to binding.profileAdressEditText.text.toString()
                                    .trim(),
                            )
                        ) { result ->
                            when (result) {
                                is FirebaseFirestoreResult.Success<*> -> {
                                    showErrorBottomSheetDialog(
                                        R.drawable.success_gif_im,
                                        "Güncelleme işlemi başarıyla gerçekleşti."
                                    )
                                }

                                is FirebaseFirestoreResult.Failure -> {
                                    showErrorBottomSheetDialog(
                                        R.drawable.failure_gif_im,
                                        "Bir hata meydana geldi: ${result.error}"
                                    )
                                }

                            }
                            Log.e("TAG", result.toString())

                        }
                    }

                }


            }


        }

        binding.signOutButton.setOnClickListener {
            viewModel.signOut { result ->
                if (result) {
                    startActivity(Intent(requireContext(), MainActivity::class.java))
                    requireActivity().finish()
                }
            }
        }



        viewModel.getUserResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is FirebaseFirestoreResult.Success<*> -> {
                    if (result.data is User)
                        user = result.data
                    with(binding) {
                        profileAdressEditText.setText(user?.userAddress)
                        profileNameEditText.setText(user?.userName)
                        profileSurnameEditText.setText(user?.userSurname)
                        profilePhoneNumberEditText.setText(user?.userPhoneNumber)
                        if (!(user?.userPicture.isNullOrEmpty())) {
                            Glide.with(requireContext()).load(user?.userPicture)
                                .into(binding.profilePictureImageView)
                        }

                    }
                }

                is FirebaseFirestoreResult.Failure -> {
                    showErrorBottomSheetDialog(
                        R.drawable.outline_info_24,
                        "Bir hata meydana geldi: ${result.error}"
                    )
                }

            }
            binding.scrollView2.visibility = View.VISIBLE
            binding.progressBar3.visibility = View.GONE


        }


        return binding.root
    }


    private fun checkValidation(resInt: Int, info: String, onSuccessful: () -> Unit) {
        if (binding.profileAdressEditText.text.isNullOrBlank() || binding.profileNameEditText.text.isNullOrBlank() || binding.profileSurnameEditText.text.isNullOrBlank() || binding.profilePhoneNumberEditText.text.isNullOrBlank()) {

            showErrorBottomSheetDialog(resInt, info)

            with(binding) {
                if (profileSurnameEditText.text.isNullOrBlank()) profileSurnameEditText.error =
                    "Soyadınızı lütfen boş bırakmayınız."
                if (profileNameEditText.text.isNullOrBlank()) profileNameEditText.error =
                    "Adınızı lütfen boş bırakmayınız."
                if (profileAdressEditText.text.isNullOrBlank()) profileAdressEditText.error =
                    "Adres bilginizi lütfen boş bırakmayınız."
                if (profilePhoneNumberEditText.text.isNullOrBlank()) profilePhoneNumberEditText.error =
                    "Telefon numarınızı lütfen boş bırakmayınız."
            }
        } else {
            onSuccessful()

        }

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
            profilePict=getBitmapFromUri(data?.data)

        }
    }

    private fun getBitmapFromUri(uri: Uri?): Bitmap? {
        uri ?: return null
        return context?.contentResolver?.openInputStream(uri)?.use { inputStream ->
            BitmapFactory.decodeStream(inputStream)
        }
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


}