package com.project.patigo.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.project.patigo.HomeActivity
import com.project.patigo.R
import com.project.patigo.databinding.FragmentSplashBinding
import com.project.patigo.ui.viewmodels.SplashFragmentViewModel
import com.project.patigo.utils.LocalDataManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: SplashFragmentViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: SplashFragmentViewModel by viewModels()
        viewModel = tempViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel.currentUser()

        viewModel.firebaseUser.observe(viewLifecycleOwner) { firebaseUser ->

            Handler(Looper.getMainLooper()).postDelayed({
                if (firebaseUser != null) {
                    startActivity(Intent(requireContext(),HomeActivity::class.java))
                    requireActivity().finish()
                } else {
                    val result =
                        LocalDataManager.getSharedPreference(requireContext(), "goBoarding", true)
                    if (result) {
                        Navigation.findNavController(requireView())
                            .navigate(SplashFragmentDirections.actionSplashFragmentToBoardingFragment())
                    } else {
                        Navigation.findNavController(requireView())
                            .navigate(SplashFragmentDirections.actionSplashFragmentToTypeFragment2())
                    }

                }

            }, 4000)
        }


        return view
    }
}