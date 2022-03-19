package com.jpdevzone.younghunter.quizquestion

import android.os.Bundle
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.jpdevzone.younghunter.R
import com.jpdevzone.younghunter.databinding.FragmentQuizQuestionBinding
import com.jpdevzone.younghunter.setBackground


class QuizQuestionFragment : Fragment() {
    private lateinit var binding : FragmentQuizQuestionBinding
    private lateinit var viewModel: QuizQuestionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_quiz_question, container, false)
        viewModel = ViewModelProvider(
            this,
            QuizQuestionViewModelFactory(requireActivity().application))[QuizQuestionViewModel::class.java]

        binding.viewModel = viewModel

        binding.lifecycleOwner = this

        // Sets random background
        binding.quizQuestionBackground.setImageResource(setBackground)

        // Gets arguments from Bundle and bind them to xml
        val args = QuizQuestionFragmentArgs.fromBundle(requireArguments()).dashData
        binding.dashData = args

        // Launch timer
        launchTimer(args.topic)

        // Sets back navigation
        binding.arrowBackIv.setOnClickListener {
            navigateBack()
        }

        viewModel.position.observe(viewLifecycleOwner) { position ->
            viewModel.getQuestion(args.ids[position.minus(1)])
        }

        return binding.root
    }

    // Initiates the timer and observes current time with Live Data
    private fun launchTimer(topic: String) {
        viewModel.timer(topic)
        viewModel.currentTime.observe(viewLifecycleOwner) { newTime ->
            binding.timer.text = DateUtils.formatElapsedTime(newTime)
        }
    }

    // Sets navigation action
    private fun navigateBack() {
        this.findNavController().navigate(
            QuizQuestionFragmentDirections.actionQuizQuestionFragmentToDashboardFragment()
        )
    }
}