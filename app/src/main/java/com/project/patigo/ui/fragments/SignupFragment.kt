package com.project.patigo.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.project.patigo.HomeActivity
import com.project.patigo.R
import com.project.patigo.databinding.FragmentErrorBottomSheetBinding
import com.project.patigo.databinding.FragmentSignupBinding
import com.project.patigo.ui.viewmodels.SignupFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupFragment : Fragment() {
    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: SignupFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: SignupFragmentViewModel by viewModels()
        viewModel = tempViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.signupButton.setOnClickListener {
            checkValidation(R.drawable.outline_info_24, "Lütfen gerekli alanları doldurunuz") {
                with(binding){
                    viewModel.createNewUser(
                        signupEmailEditText.text.toString().trim(),
                        signupPasswordEditText.text.toString().trim()
                    )
                }

            }
        }

        binding.loginButton.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }

        viewModel.firebaseUser.observe(viewLifecycleOwner) { firebaseUser ->
            if (firebaseUser != null) {
                val intent = Intent(requireContext(), HomeActivity::class.java)
                startActivity(intent)
            }
        }

        viewModel.exception.observe(viewLifecycleOwner) { error ->
            showBottomSheetDialog(R.drawable.outline_info_24, error.toString())
        }
        return view
    }

    private fun checkValidation(resInt: Int, info: String, onSuccessful: () -> Unit) {
        with(binding){
            if (signupEmailEditText.text.isNullOrBlank() || signupPasswordEditText.text.isNullOrBlank() ||signupPasswordConfirmEditText.text.isNullOrBlank()) {
                showBottomSheetDialog(resInt, info)
                with(binding) {
                    if (signupEmailEditText.text.isNullOrBlank()) signupEmailEditText.error =
                        "E-mail boş bırakılamaz."
                    if (signupPasswordEditText.text.isNullOrBlank()) signupPasswordEditText.error =
                        "Şifre boş bırakılamaz."
                    if (signupPasswordConfirmEditText.text.isNullOrBlank()) signupPasswordConfirmEditText.error =
                        "Şifre Onaylama boş bırakılamaz."
                }
            } else {
                if (signupPasswordEditText.text.trim() != signupPasswordConfirmEditText.text.trim()){
                    showBottomSheetDialog(resInt, "Şifreler eşleşmiyor. Lütfen aynı şifreyi girin.")
                }else{
                    onSuccessful()
                }
            }
        }


    }

    private fun showBottomSheetDialog(resInt: Int, info: String) {
        val dialog = BottomSheetDialog(requireContext())
        val bottomSheetBinding = FragmentErrorBottomSheetBinding.inflate(
            LayoutInflater.from(requireContext()),
            null,
            false
        )

        bottomSheetBinding.errorImage.setImageResource(resInt)
        bottomSheetBinding.errorDescription.text = info
        dialog.setCancelable(true)
        dialog.setContentView(bottomSheetBinding.root)
        dialog.show()
    }


}