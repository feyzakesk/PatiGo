package com.project.patigo.ui.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.project.patigo.R
import com.project.patigo.databinding.FragmentBoardingBinding
import com.project.patigo.ui.adapters.BoardingViewPagerAdapter
import com.project.patigo.utils.LocalDataManager


class BoardingFragment : Fragment() {
    private var _binding: FragmentBoardingBinding? = null
    private val binding get() = _binding!!
    private var currentItem = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentBoardingBinding.inflate(inflater, container, false)
        val view = binding.root
        val fragmentList =
            listOf(BoardingFirstFragment(), BoardingSecondFragment(), BoardingThirdFragment())
        val adapter =
            BoardingViewPagerAdapter(
                requireActivity().supportFragmentManager,
                lifecycle,
                fragmentList
            )
        val viewPager = binding.boardingViewPager
        viewPager.isUserInputEnabled=false
        viewPager.adapter = adapter
        val indicator = binding.circleIndicator
        indicator.setViewPager(viewPager)



        binding.nextButton.setOnClickListener {

            if (currentItem < adapter.itemCount - 1) {
                currentItem++
                viewPager.currentItem = currentItem
            } else {
                LocalDataManager.setSharedPreference(requireContext(), "goBoarding", false)
                Navigation.findNavController(it)
                    .navigate(R.id.action_boardingFragment_to_typeFragment)
            }
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}