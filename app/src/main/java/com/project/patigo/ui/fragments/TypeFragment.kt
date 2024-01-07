package com.project.patigo.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.project.patigo.R
import com.project.patigo.databinding.FragmentSplashBinding
import com.project.patigo.databinding.FragmentTypeBinding


class TypeFragment : Fragment() {

    private var _binding: FragmentTypeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentTypeBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.petCarerButton.setOnClickListener {
            Toast.makeText(requireContext(),"En yakın zamanda hizmetinizde :)",Toast.LENGTH_SHORT).show()
        }

        binding.petOwnerButton.setOnClickListener {
            Navigation.findNavController(requireView())
                .navigate(TypeFragmentDirections.actionTypeFragmentToLoginFragment())
        }

        return view
    }


}