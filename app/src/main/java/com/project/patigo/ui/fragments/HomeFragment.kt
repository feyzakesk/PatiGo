package com.project.patigo.ui.fragments

import AdvertRecyclerViewAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.project.patigo.data.entity.Carer
import com.project.patigo.data.entity.Comment
import com.project.patigo.data.entity.Pet
import com.project.patigo.data.firebase.FirebaseFirestoreResult
import com.project.patigo.databinding.FragmentHomeBinding
import com.project.patigo.ui.adapters.PetAdapter
import com.project.patigo.ui.viewmodels.HomeFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: HomeFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: HomeFragmentViewModel by viewModels()
        viewModel = tempViewModel
    }

    override fun onResume() {
        viewModel.getCarers()
        super.onResume()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.advertRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.progressBar2.visibility=View.VISIBLE
        viewModel.resultCarers.observe(viewLifecycleOwner){result->

            when (result) {
                is FirebaseFirestoreResult.Success<*> -> {
                    val data = result.data
                    if (data is List<*>) {
                        val filteredList = data.filterIsInstance<Carer>()
                        binding.advertRecyclerView.adapter =
                            AdvertRecyclerViewAdapter(requireContext(), filteredList)
                        binding.progressBar2.visibility = View.GONE
                    }
                }
                is FirebaseFirestoreResult.Failure -> {
                    Log.e("Hata:", result.error)
                    binding.progressBar2.visibility = View.GONE
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