package com.jpdevzone.younghunter.finishquiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.jpdevzone.younghunter.R
import com.jpdevzone.younghunter.databinding.FragmentFinishQuizBinding
import com.jpdevzone.younghunter.databinding.FragmentHelpBinding
import com.jpdevzone.younghunter.quizquestion.QuizQuestionFragmentDirections
import com.jpdevzone.younghunter.utils.setBackground

class FinishQuizFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding : FragmentFinishQuizBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_finish_quiz, container, false)
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