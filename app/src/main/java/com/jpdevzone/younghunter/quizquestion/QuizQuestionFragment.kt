package com.jpdevzone.younghunter.quizquestion

import android.os.Bundle
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
    private lateinit var args: QuizQuestionFragmentArgs

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_quiz_question, container, false)
        viewModel = ViewModelProvider(
            this,
            QuizQuestionViewModelFactory(requireActivity().application))[QuizQuestionViewModel::class.java]

        binding.vm = viewModel

        binding.lifecycleOwner = viewLifecycleOwner

        // Sets random background
        binding.quizQuestionBackground.setImageResource(setBackground)

        // Gets arguments from Bundle and bind them to xml
        args = QuizQuestionFragmentArgs.fromBundle(requireArguments())
        binding.dashData = args.dashData

        // Setup quiz environment based on topic from safeArgs
        viewModel.setupEnvironment(args.dashData.topic)

        // Loads next question
        // Triggers navigation to FinishQuizFragment
        loadQuestion()

        // Sets navigation to FinishQuizFragment
        finishQuiz()

        // Sets back navigation
        binding.arrowBackIv.setOnClickListener {
            navigateBack()
        }

        return binding.root
    }

    // Loads next question
    private fun loadQuestion() {
        viewModel.position.observe(viewLifecycleOwner) { position ->
            val range = viewModel.range.value
            if (position <= range!!.size) {
                viewModel.loadQuestion(range[position.minus(1)])
            } else {
                viewModel.navigateToFinish()
            }
        }
    }

    // Navigate to FinishQuizFragment
    private fun finishQuiz() {
        viewModel.navigateToFinish.observe(viewLifecycleOwner) {
            if (it == true) {
                this.findNavController().navigate(
                    QuizQuestionFragmentDirections.actionQuizQuestionFragmentToFinishQuizFragment()
                )
                viewModel.doneNavigating()
            }
        }
    }

    // Navigate to DashboardFragment
    private fun navigateBack() {
        this.findNavController().navigate(
            QuizQuestionFragmentDirections.actionQuizQuestionFragmentToDashboardFragment()
        )
    }
}