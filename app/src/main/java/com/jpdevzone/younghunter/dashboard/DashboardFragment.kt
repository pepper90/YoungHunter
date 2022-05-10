package com.jpdevzone.younghunter.dashboard

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
import com.jpdevzone.younghunter.database.models.Progress
import com.jpdevzone.younghunter.databinding.FragmentDashboardBinding
import com.jpdevzone.younghunter.utils.setBackground

class DashboardFragment : Fragment() {
    private lateinit var binding : FragmentDashboardBinding
    private lateinit var viewModel: DashboardViewModel
    private lateinit var dashboardData: DashboardData

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
            viewModel.progressExam()
            dashboardData = DashboardData(
                                R.string.examYoungHunter,
                                R.string.time_exam,
                                R.drawable.ic_exam,
                                "exam"
                            )
            conditionalNavigation(
                viewModel.progress.value,
                dashboardData,
                R.string.examYoungHunter
            )
        }

        binding.animals.setOnClickListener {
            viewModel.progressAnimals()
            dashboardData = DashboardData(
                                R.string.animals,
                                R.string.time_mini_test,
                                R.drawable.ic_animals,
                                "animals"
                            )
            conditionalNavigation(
                viewModel.progress.value,
                dashboardData,
                R.string.animals)
        }

        binding.law.setOnClickListener {
            viewModel.progressLaw()
            dashboardData = DashboardData(
                                R.string.law,
                                R.string.time_mini_test,
                                R.drawable.ic_law,
                                "law"
                            )
            conditionalNavigation(
                viewModel.progress.value,
                dashboardData,
                R.string.law
            )
        }

        binding.gameManagement.setOnClickListener {
            viewModel.progressGameManagement()
            dashboardData = DashboardData(
                                R.string.gameManagement,
                                R.string.time_mini_test,
                                R.drawable.ic_animalcare,
                                "gameManagement"
                            )
            conditionalNavigation(
                viewModel.progress.value,
                dashboardData,
                R.string.gameManagement
            )
        }

        binding.huntingMethods.setOnClickListener {
            viewModel.progressHuntingMethods()
            dashboardData = DashboardData(
                                R.string.huntingMethods,
                                R.string.time_mini_test,
                                R.drawable.ic_hunting,
                                "huntingMethods"
                            )
            conditionalNavigation(
                viewModel.progress.value,
                dashboardData,
                R.string.huntingMethods
            )
        }

        binding.guns.setOnClickListener {
            viewModel.progressGuns()
            dashboardData = DashboardData(
                                R.string.guns,
                                R.string.time_mini_test,
                                R.drawable.ic_guns,
                                "guns"
                            )
            conditionalNavigation(
                viewModel.progress.value,
                dashboardData,
                R.string.guns
            )
        }

        binding.dogs.setOnClickListener {
            viewModel.progressDogs()
            dashboardData = DashboardData(
                                R.string.dogs,
                                R.string.time_mini_test,
                                R.drawable.ic_dogs,
                                "dogs"
                            )
            conditionalNavigation(
                viewModel.progress.value,
                dashboardData,
                R.string.dogs
            )
        }

        binding.viruses.setOnClickListener {
            viewModel.progressViruses()
            dashboardData = DashboardData(
                                R.string.viruses,
                                R.string.time_mini_test,
                                R.drawable.ic_virus,
                                "viruses"
                            )
            conditionalNavigation(
                viewModel.progress.value,
                dashboardData,
                R.string.viruses
            )
        }

        return binding.root
    }

    private fun conditionalNavigation(progress: Progress?, data: DashboardData, title: Int) {
        if (progress != null) {
            dashboardDialog(title, data)
        } else {
            navigateToNewQuiz(data)
        }
    }

    private fun dashboardDialog(title: Int, data: DashboardData) {
        val dialog = AlertDialog.Builder(requireContext(), R.style.DialogSlideAnim)
        val dialogLayout = layoutInflater.inflate(R.layout.dialog_dashboard, null)
        dialog.setView(dialogLayout)
        val alertDialog = dialog.create()

        val category = dialogLayout.findViewById<TextView>(R.id.title_tv)
        val startNewTest = dialogLayout.findViewById<TextView>(R.id.new_test_tv)
        val continueTest = dialogLayout.findViewById<TextView>(R.id.old_test_tv)
        val dismiss = dialogLayout.findViewById<ImageView>(R.id.dismiss_iv)

        category.text = getString(title)

        continueTest.setOnClickListener{
            alertDialog.dismiss()
            navigateToSavedQuiz(data)
        }

        startNewTest.setOnClickListener {
            alertDialog.dismiss()
            viewModel.clearProgressValue()
            viewModel.clearProgressFromDb(data.topic)
            navigateToNewQuiz(data)
        }

        dismiss.setOnClickListener {
            viewModel.clearProgressValue()
            alertDialog.dismiss()
        }

        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    private fun navigateToNewQuiz(data: DashboardData) {
        this.findNavController().navigate(
            DashboardFragmentDirections
                .actionDashboardFragmentToQuizQuestionFragment(
                    data
                )
        )
    }

    private fun navigateToSavedQuiz(data: DashboardData) {
        this.findNavController().navigate(
            DashboardFragmentDirections
                .actionDashboardFragmentToSavedQuizFragment(
                    data
                )
        )
    }

}