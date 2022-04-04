package com.jpdevzone.younghunter.finishquiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.jpdevzone.younghunter.R
import com.jpdevzone.younghunter.databinding.FragmentFinishQuizBinding
import com.jpdevzone.younghunter.utils.setBackground

class FinishQuizFragment : Fragment() {
    private lateinit var binding : FragmentFinishQuizBinding
    private lateinit var viewModel: FinishQuizViewModel
    private lateinit var args: FinishQuizFragmentArgs

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding  = DataBindingUtil.inflate(inflater,R.layout.fragment_finish_quiz, container, false)

        args = FinishQuizFragmentArgs.fromBundle(requireArguments())

        viewModel = ViewModelProvider(
            this,
            FinishQuizViewModelFactory(
                args.result,
                args.time,
                args.max
            )
        )[FinishQuizViewModel::class.java]

        binding.vm = viewModel

        binding.lifecycleOwner = viewLifecycleOwner

        // Sets random background
        binding.finishQuizBackground.setImageResource(setBackground)

        // Sets back navigation
        binding.arrowBackIv.setOnClickListener {
            navigateBack()
        }

        return binding.root
    }

    // Navigate to DashboardFragment
    private fun navigateBack() {
        this.findNavController().navigate(
            FinishQuizFragmentDirections.actionFinishQuizFragmentToDashboardFragment()
        )
    }
}