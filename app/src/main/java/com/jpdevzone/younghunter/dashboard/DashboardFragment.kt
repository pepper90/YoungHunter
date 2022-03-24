package com.jpdevzone.younghunter.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.jpdevzone.younghunter.*
import com.jpdevzone.younghunter.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding : FragmentDashboardBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_dashboard, container, false)

        binding.dashboardBackground.setImageResource(setBackground)

        binding.dashboardHelp.setOnClickListener {
            this.findNavController().navigate(
                DashboardFragmentDirections.actionDashboardFragmentToHelpFragment()
            )
        }

        binding.loadExam.setOnClickListener {
            this.findNavController().navigate(
                DashboardFragmentDirections
                    .actionDashboardFragmentToQuizQuestionFragment(
                        DashboardData(
                            R.string.examYoungHunter,
                            R.string.time_exam,
                            R.drawable.ic_exam,
                            "exam"
                        )
                    )
            )
        }

        binding.animals.setOnClickListener {
            this.findNavController().navigate(
                DashboardFragmentDirections
                    .actionDashboardFragmentToQuizQuestionFragment(
                        DashboardData(
                            R.string.animals,
                            R.string.time_mini_test,
                            R.drawable.ic_animals,
                            "animals"
                        )
                    )
            )
        }

        binding.law.setOnClickListener {
            this.findNavController().navigate(
                DashboardFragmentDirections
                    .actionDashboardFragmentToQuizQuestionFragment(
                        DashboardData(
                            R.string.law,
                            R.string.time_mini_test,
                            R.drawable.ic_law,
                            "law"
                        )
                    )
            )
        }

        binding.gameManagement.setOnClickListener {
            this.findNavController().navigate(
                DashboardFragmentDirections
                    .actionDashboardFragmentToQuizQuestionFragment(
                        DashboardData(
                            R.string.gameManagement,
                            R.string.time_mini_test,
                            R.drawable.ic_animalcare,
                            "gameManagement"
                        )
                    )
            )
        }

        binding.huntingMethods.setOnClickListener {
            this.findNavController().navigate(
                DashboardFragmentDirections
                    .actionDashboardFragmentToQuizQuestionFragment(
                        DashboardData(
                            R.string.huntingMethods,
                            R.string.time_mini_test,
                            R.drawable.ic_hunting,
                            "huntingMethods"
                        )
                    )
            )
        }

        binding.guns.setOnClickListener {
            this.findNavController().navigate(
                DashboardFragmentDirections
                    .actionDashboardFragmentToQuizQuestionFragment(
                        DashboardData(
                            R.string.guns,
                            R.string.time_mini_test,
                            R.drawable.ic_guns,
                            "guns"
                        )
                    )
            )
        }

        binding.dogs.setOnClickListener {
            this.findNavController().navigate(
                DashboardFragmentDirections
                    .actionDashboardFragmentToQuizQuestionFragment(
                        DashboardData(
                            R.string.dogs,
                            R.string.time_mini_test,
                            R.drawable.ic_dogs,
                            "dogs"
                        )
                    )
            )
        }

        binding.viruses.setOnClickListener {
            this.findNavController().navigate(
                DashboardFragmentDirections
                    .actionDashboardFragmentToQuizQuestionFragment(
                        DashboardData(
                            R.string.viruses,
                            R.string.time_mini_test,
                            R.drawable.ic_virus,
                            "viruses"
                        )
                    )
            )
        }

        return binding.root
    }

}