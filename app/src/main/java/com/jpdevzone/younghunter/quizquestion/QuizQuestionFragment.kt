package com.jpdevzone.younghunter.quizquestion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.jpdevzone.younghunter.R
import com.jpdevzone.younghunter.databinding.FragmentQuizQuestionBinding
import com.jpdevzone.younghunter.utils.setBackground


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
            showQuitDialog()
        }

        binding.reloadIv.setOnClickListener {
            showReloadDialog()
        }

        return binding.root
    }

    // Loads next question
    private fun loadQuestion() {
        viewModel.position.observe(viewLifecycleOwner) { position ->
            viewModel.loadOrFinish(position)
        }
    }

    // Navigate to FinishQuizFragment
    private fun finishQuiz() {
        viewModel.navigateToFinish.observe(viewLifecycleOwner) {
            if (it == true) {
                this.findNavController().navigate(
                    QuizQuestionFragmentDirections.actionQuizQuestionFragmentToFinishQuizFragment(
                        viewModel.totalAnswers.value!!,
                        viewModel.progressBarMax.value!!,
                        viewModel.elapsedTime.value!!
                    )
                )
                viewModel.doneNavigating()
            }
        }
    }

    // Reload QuizQuestionFragment
    private fun reload() {
        this.findNavController().navigate(
            QuizQuestionFragmentDirections.actionQuizQuestionFragmentSelf(args.dashData)
        )
    }

    // Navigate to DashboardFragment
    private fun navigateBack() {
        this.findNavController().navigate(
            QuizQuestionFragmentDirections.actionQuizQuestionFragmentToDashboardFragment()
        )
    }

    // Show Reload dialog
    private fun showReloadDialog() {
        viewModel.pauseTimer()
        val dialog = AlertDialog.Builder(requireContext())
        val dialogLayout = layoutInflater.inflate(R.layout.dialog_reload, null)
        dialog.setView(dialogLayout)
        val alertDialog = dialog.create()

        val yes = dialogLayout.findViewById<TextView>(R.id.yes_btn)
        yes.setOnClickListener {
            alertDialog.dismiss()
            reload()
        }

        val no = dialogLayout.findViewById<TextView>(R.id.no_btn)
        no.setOnClickListener {
            alertDialog.dismiss()
            viewModel.resumeTimer()
        }

        val dismiss = dialogLayout.findViewById<ImageView>(R.id.dismiss_dialog_iv)
        dismiss.setOnClickListener {
            alertDialog.dismiss()
            viewModel.resumeTimer()
        }

        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    private fun showQuitDialog() {
        viewModel.pauseTimer()
        val dialog = AlertDialog.Builder(requireContext())
        val dialogLayout = layoutInflater.inflate(R.layout.dialog_quit, null)
        dialog.setView(dialogLayout)
        val alertDialog = dialog.create()


        val yes = dialogLayout.findViewById<TextView>(R.id.yes_btn)
        yes.setOnClickListener {
            viewModel.saveProgress(args.dashData.topic)
            alertDialog.dismiss()
            navigateBack()
        }

        val no = dialogLayout.findViewById<TextView>(R.id.no_btn)
        no.setOnClickListener {
            viewModel.clearProgress(args.dashData.topic)
            alertDialog.dismiss()
            navigateBack()
        }

        val dismiss = dialogLayout.findViewById<ImageView>(R.id.dismiss_dialog_iv)
        dismiss.setOnClickListener {
            alertDialog.dismiss()
            viewModel.resumeTimer()
        }

        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    override fun onPause() {
        super.onPause()
        viewModel.pauseTimer()
    }

    override fun onResume() {
        super.onResume()
        viewModel.resumeTimer()
    }
}