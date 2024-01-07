package com.project.patigo.ui.fragments


import android.os.Bundle
import android.text.Editable
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.project.patigo.R
import com.project.patigo.data.entity.Carer
import com.project.patigo.data.entity.Pet
import com.project.patigo.data.entity.User
import com.project.patigo.data.firebase.FirebaseFirestoreResult
import com.project.patigo.databinding.FragmentDetailBinding
import com.project.patigo.databinding.FragmentErrorBottomSheetBinding
import com.project.patigo.ui.adapters.CommentRecyclerViewAdapter
import com.project.patigo.ui.adapters.PetAdapter
import com.project.patigo.ui.adapters.ServiceRecyclerViewAdapter
import com.project.patigo.ui.viewmodels.DetailFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle


@AndroidEntryPoint
class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: DetailFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: DetailFragmentViewModel by viewModels()
        viewModel = tempViewModel
    }

    override fun onResume() {
        binding.advertInfoEditText.text.clear()
        binding.petAutoCompleteTextView.text.clear()
        super.onResume()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        Log.e("TAG2","onCreateView")
        viewModel.getPets()

        val bundle: DetailFragmentArgs by navArgs()
        val carerValue = bundle.carer

        var petList: List<Pet> = listOf()
        var selectedPet: Pet? = null
        init(carerValue)
        viewModel.resultPets.observe(viewLifecycleOwner) { result ->
            when (result) {
                is FirebaseFirestoreResult.Success<*> -> {
                    val data = result.data

                    if (data is List<*>) {
                        petList = data.filterIsInstance<Pet>()
                        val petNames = petList.map { it.petName }
                        val petAdapter =
                            PetAdapter(
                                requireContext(),
                                petNames
                            )
                        binding.petAutoCompleteTextView.setAdapter(petAdapter)
                    }
                }

                is FirebaseFirestoreResult.Failure -> {
                    Log.e("Hata:", result.error)

                }
            }

        }
        binding.petAutoCompleteTextView.setOnItemClickListener { adapterView, view, position, id ->
            selectedPet = petList[position]
        }

        binding.expandButton.setOnClickListener {
            if (binding.commentRecyclerView.visibility == View.VISIBLE) {

                TransitionManager.beginDelayedTransition(
                    binding.commentRecyclerView,
                    AutoTransition()
                )
                binding.commentRecyclerView.visibility = View.GONE
                //arrow.setImageResource(R.drawable.ic_baseline_expand_more_24)
            } else {
                TransitionManager.beginDelayedTransition(
                    binding.commentRecyclerView,
                    AutoTransition()
                )
                binding.commentRecyclerView.visibility = View.VISIBLE
                //arrow.setImageResource(R.drawable.ic_baseline_expand_less_24)
            }
        }


        binding.requestButton.setOnClickListener {
            when (viewModel.currentUser) {
                is FirebaseFirestoreResult.Success<*> -> {
                    val data = (viewModel.currentUser as FirebaseFirestoreResult.Success<User>).data
                    if (data.userName.isNullOrBlank() || data.userSurname.isNullOrBlank() || data.userAddress.isNullOrBlank() || data.userPhoneNumber.isNullOrBlank()) {
                        Toast.makeText(
                            requireContext(),
                            "Lütfen hakkınızdaki gerekli bilgileri profil kısmından doldurunuz.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }else{
                        if (selectedPet == null || binding.advertInfoEditText.text.isNullOrBlank()) {
                            showErrorBottomSheetDialog(
                                R.drawable.outline_info_24,
                                "Lütfen dost bilginizi ve talep hakkında açıklamanızı boş bırakmayınız."
                            )
                        } else {

                            lifecycleScope.launch {
                                showErrorBottomSheetDialog(
                                    R.drawable.success_gif_im,
                                    "Talebiniz, ilgili bakıcıya başarıyla iletildi.En yakın zamanda size dönüş sağlanıcaktır."
                                )
                                delay(6000)
                                Toast.makeText(
                                    requireContext(),
                                    "Talebiniz, bakıcı tarafından onaylandı.İlgili sayfaya yönlendiriliyorsunuz.",
                                    Toast.LENGTH_SHORT
                                ).show()
                                delay(3000)
                                val direction =
                                    DetailFragmentDirections.actionDetailFragmentToConfirmedFragment(
                                        selectedPet!!, carerValue
                                    )
                                Navigation.findNavController(it).navigate(direction)

                            }
                        }
                    }

                }

                is FirebaseFirestoreResult.Failure -> {
                    Log.e("Hata:", (viewModel.currentUser as FirebaseFirestoreResult.Failure).error)

                }

                else -> {}
            }


        }


        return view
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


    private fun init(carerValue: Carer) {
        Glide.with(requireContext()).load(carerValue.carerProfilePict)
            .into(binding.detailImageView)
        binding.carerAgeTextView.text =
            if (carerValue.carerAge.toString().isEmpty()) "" else "(${carerValue.carerAge})"
        binding.carerInfoTextView.text = carerValue.carerInfo
        binding.carerNameTextView.text = buildString {
            append(carerValue.carerName)
            append(" ")
            append(carerValue.carerSurname)
        }
        binding.detailBackButton.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }
        binding.detailLocationTextView.text = carerValue.carerProvince
        binding.detailStarTextView.text = "4.5"
        val serviceflexboxLayoutManager = FlexboxLayoutManager(requireContext())
        serviceflexboxLayoutManager.apply {
            flexDirection = FlexDirection.ROW
            flexWrap = FlexWrap.WRAP
        }
        binding.serviceRecyclerView.layoutManager = serviceflexboxLayoutManager
        binding.serviceRecyclerView.adapter =
            ServiceRecyclerViewAdapter(requireContext(), carerValue.carerServices)

        val dayflexboxLayoutManager = FlexboxLayoutManager(requireContext())
        dayflexboxLayoutManager.apply {
            flexDirection = FlexDirection.ROW
            flexWrap = FlexWrap.WRAP
        }
        binding.dayRecyclerView.layoutManager = dayflexboxLayoutManager
        binding.dayRecyclerView.adapter =
            ServiceRecyclerViewAdapter(requireContext(), carerValue.carerAvailableDay)

        binding.commentRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.commentRecyclerView.adapter =
            CommentRecyclerViewAdapter(requireContext(), carerValue.carerReviewList)
    }

}