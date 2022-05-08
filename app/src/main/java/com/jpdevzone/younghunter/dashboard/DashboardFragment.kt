package com.jpdevzone.younghunter.dashboard

import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
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
import com.jpdevzone.younghunter.*
import com.jpdevzone.younghunter.database.models.Progress
import com.jpdevzone.younghunter.databinding.FragmentDashboardBinding
import com.jpdevzone.younghunter.databinding.FragmentQuizQuestionBinding
import com.jpdevzone.younghunter.quizquestion.QuizQuestionViewModel
import com.jpdevzone.younghunter.quizquestion.QuizQuestionViewModelFactory
import com.jpdevzone.younghunter.utils.setBackground

class DashboardFragment : Fragment() {
    private lateinit var binding : FragmentDashboardBinding
    private lateinit var viewModel: DashboardViewModel
    private var dashboardData: DashboardData? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false)

        viewModel = ViewModelProvider(
            this,
            DashboardViewModelFactory(requireActivity().application)
        )[DashboardViewModel::class.java]

        binding.lifecycleOwner = viewLifecycleOwner

        binding.dashboardBackground.setImageResource(setBackground)

        binding.dashboardHelp.setOnClickListener {
            this.findNavController().navigate(
                DashboardFragmentDirections.actionDashboardFragmentToHelpFragment()
            )
        }

        binding.loadExam.setOnClickListener {
            navigate(
                DashboardData(
                    R.string.examYoungHunter,
                    R.string.time_exam,
                    R.drawable.ic_exam,
                    "exam"
                )
            )
        }

        binding.animals.setOnClickListener {
//            navigate(
//                DashboardData(
//                    R.string.animals,
//                    R.string.time_mini_test,
//                    R.drawable.ic_animals,
//                    "animals"
//                )
//            )
            dashboardDialog()
        }

        binding.law.setOnClickListener {
            navigate(
                DashboardData(
                    R.string.law,
                    R.string.time_mini_test,
                    R.drawable.ic_law,
                    "law"
                )
            )
        }

        binding.gameManagement.setOnClickListener {
            navigate(
                DashboardData(
                    R.string.gameManagement,
                    R.string.time_mini_test,
                    R.drawable.ic_animalcare,
                    "gameManagement"
                )
            )
        }

        binding.huntingMethods.setOnClickListener {
            navigate(
                DashboardData(
                    R.string.huntingMethods,
                    R.string.time_mini_test,
                    R.drawable.ic_hunting,
                    "huntingMethods"
                )
            )
        }

        binding.guns.setOnClickListener {
            navigate(
                DashboardData(
                    R.string.guns,
                    R.string.time_mini_test,
                    R.drawable.ic_guns,
                    "guns"
                )
            )
        }

        binding.dogs.setOnClickListener {
            navigate(
                DashboardData(
                    R.string.dogs,
                    R.string.time_mini_test,
                    R.drawable.ic_dogs,
                    "dogs"
                )
            )
        }

        binding.viruses.setOnClickListener {
            navigate(
                DashboardData(
                    R.string.viruses,
                    R.string.time_mini_test,
                    R.drawable.ic_virus,
                    "viruses"
                )
            )
        }

        observeForDialog(dashboardData)

        return binding.root
    }

    private fun observeForDialog(data: DashboardData?) {
        viewModel.showDialog.observe(viewLifecycleOwner) {
            if (it == true) {
                dashboardDialog()
            }
            viewModel.resetDialog()
        }

        viewModel.navigateToQuiz.observe(viewLifecycleOwner) {
            if (it == true) {
                navigate(data!!)
            }
            viewModel.onQuizNavigationComplete()
        }
    }

    private fun dashboardDialog() {
        val dialog = AlertDialog.Builder(requireContext(), R.style.DialogSlideAnim)
        val dialogLayout = layoutInflater.inflate(R.layout.dialog_dashboard, null)
        dialog.setView(dialogLayout)
        val alertDialog = dialog.create()

        val category = dialogLayout.findViewById<TextView>(R.id.title_tv)
        val startNewTest = dialogLayout.findViewById<TextView>(R.id.new_test_tv)
        val continueTest = dialogLayout.findViewById<TextView>(R.id.old_test_tv)
        val dismiss = dialogLayout.findViewById<ImageView>(R.id.dismiss_iv)

        category.text = ""

        continueTest.setOnClickListener{
            alertDialog.dismiss()
        }

        startNewTest.setOnClickListener {
            alertDialog.dismiss()
        }

        dismiss.setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.show()
    }

    private fun navigate(data: DashboardData) {
        this.findNavController().navigate(
            DashboardFragmentDirections
                .actionDashboardFragmentToQuizQuestionFragment(
                    data
                )
        )
    }

}