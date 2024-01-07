package com.project.patigo.ui.fragments

import android.graphics.BitmapFactory
import android.graphics.PorterDuff
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.patigo.R
import com.project.patigo.data.entity.Pet
import com.project.patigo.data.firebase.FirebaseFirestoreResult
import com.project.patigo.databinding.FragmentPetBinding
import com.project.patigo.ui.adapters.PetRecyclerViewAdapter
import com.project.patigo.ui.viewmodels.PetFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PetFragment : Fragment() {

    private var _binding: FragmentPetBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: PetFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: PetFragmentViewModel by viewModels()
        viewModel = tempViewModel
    }

    override fun onResume() {
        viewModel.getPets()
        super.onResume()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentPetBinding.inflate(inflater, container, false)
        val view = binding.root
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.insert_pet_ic)
        val drawable = BitmapDrawable(resources, bitmap)
        binding.insertPetFabButton.icon = drawable
        binding.insertPetFabButton.iconTint = null
        binding.insertPetFabButton.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_petFragment_to_insertPetFragment)
        }
        binding.petRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        binding.progressBar.visibility=View.VISIBLE
        viewModel.resultPets.observe(viewLifecycleOwner) { result ->
            when (result) {
                is FirebaseFirestoreResult.Success<*> -> {
                    val petList = result.data

                    if (petList is List<*>) {
                        val filteredList = petList.filterIsInstance<Pet>()
                        binding.petRecyclerView.adapter =
                            PetRecyclerViewAdapter(filteredList, requireContext(),viewModel)
                    }
                    binding.progressBar.visibility = View.GONE
                }

                is FirebaseFirestoreResult.Failure -> {
                    Log.e("Hata:", result.error)

                    binding.progressBar.visibility = View.GONE
                }
            }
        }

        return view
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}