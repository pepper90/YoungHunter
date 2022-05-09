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
import com.jpdevzone.younghunter.databinding.FragmentDashboardBinding
import com.jpdevzone.younghunter.utils.setBackground

class DashboardFragment : Fragment() {
    private lateinit var binding : FragmentDashboardBinding
    private lateinit var viewModel: DashboardViewModel

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
            navigateToSavedQuiz(
                DashboardData(
                    R.string.animals,
                    R.string.time_mini_test,
                    R.drawable.ic_animals,
                    "animals"
                )
            )
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

        return binding.root
    }

//    binding.animals.setOnClickListener {
//        viewModel.progressAnimals()
//        if (viewModel.progress.value != null) {
//            dashboardDialog(R.string.animals)
//        } else {
//            navigate(
//                DashboardData(
//                    R.string.animals,
//                    R.string.time_mini_test,
//                    R.drawable.ic_animals,
//                    "animals"
//                )
//            )
//        }
//    }

    private fun dashboardDialog(title: Int) {
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
        }

        startNewTest.setOnClickListener {
            alertDialog.dismiss()
        }

        dismiss.setOnClickListener {
            viewModel.clearProgress()
            alertDialog.dismiss()
        }

        alertDialog.setCancelable(false)
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

    private fun navigateToSavedQuiz(data: DashboardData) {
        this.findNavController().navigate(
            DashboardFragmentDirections
                .actionDashboardFragmentToSavedQuizFragment(
                    data
                )
        )
    }

}