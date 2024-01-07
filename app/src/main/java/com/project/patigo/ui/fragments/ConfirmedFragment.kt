package com.project.patigo.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.project.patigo.R
import com.project.patigo.data.entity.Carer
import com.project.patigo.data.entity.Pet

import com.project.patigo.databinding.FragmentConfirmedBinding
import kotlin.random.Random


class ConfirmedFragment : Fragment() {

    private var _binding: FragmentConfirmedBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentConfirmedBinding.inflate(inflater, container, false)
        val view = binding.root
        val bundle: ConfirmedFragmentArgs by navArgs()
        val carerValue = bundle.carerArg
        val petValue = bundle.petArg
        init(petValue,carerValue)

        binding.tvReject.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "Talep Reddedildi.",
                Toast.LENGTH_SHORT
            ).show()
            Navigation.findNavController(requireView())
                .navigate(ConfirmedFragmentDirections.actionConfirmedFragmentToHomeFragment())

        }
        binding.tvApprove.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "Talep Kabul Edildi.",
                Toast.LENGTH_SHORT
            ).show()
            Navigation.findNavController(requireView())
                .navigate(ConfirmedFragmentDirections.actionConfirmedFragmentToHomeFragment())
        }

        return view
    }

    private fun init(petValue: Pet, carerValue:Carer) {
        with(binding){
            Glide.with(requireContext()).load(carerValue.carerProfilePict)
                .into(advertPictureImageView);

            carerAgeTextView.text =
                if (carerValue.carerAge.toString().isEmpty()) "" else "(${carerValue.carerAge})"
            carerName.text = buildString {
                append(carerValue.carerName)
                append(" ")
                append(carerValue.carerSurname)
            }
            priceTextView.text= buildString {
                append(Random.nextInt(300, 1001).toString())
                append(" ₺")

            }
            carerStar.text = "4.5"
            carerProvince.text = carerValue.carerProvince
            carerIban.text = carerValue.carerIban
            carerPhone.text = carerValue.carerPhoneNumber

            Glide.with(requireContext()).load(petValue.petPicture).into(petImageView);
            petGenderImage.setImageResource(if (petValue.petGender) R.drawable.male_ic else R.drawable.female_ic)
            petTypeTextView.text = petValue.petType
            petNameTextView.text = petValue.petName
            petWeightTextView.text = petValue.petWeight.toString()
            petAgeTextView.text = petValue.petAge.toString()
        }
    }
}

