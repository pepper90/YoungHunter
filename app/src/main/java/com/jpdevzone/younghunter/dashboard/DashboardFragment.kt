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
                            "exam",
                            exam
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
                            "animals",
                            animals.take(30)
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
                            "law",
                            law
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
                            "gameManagement",
                            gameManagement
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
                            "huntingMethods",
                            huntingMethods
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
                            "guns",
                            guns
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
                            "dogs",
                            dogs
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
                            "viruses",
                            viruses
                        )
                    )
            )
        }

        return binding.root
    }
    private val animals = IntRange(1, 522).shuffled().take(30)
    private val law = IntRange(523, 591).shuffled().take(30)
    private val gameManagement = IntRange(592, 680).shuffled().take(30)
    private val huntingMethods = IntRange(681, 812).shuffled().take(30)
    private val guns = IntRange(813, 856).shuffled().take(30)
    private val dogs = IntRange(857, 929).shuffled().take(30)
    private val viruses = IntRange(930, 960).shuffled().take(30)
    private val exam =  IntRange(1, 522).shuffled().take(47) +
                        IntRange(523, 591).shuffled().take(7) +
                        IntRange(592, 680).shuffled().take(11) +
                        IntRange(681, 812).shuffled().take(12) +
                        IntRange(813, 856).shuffled().take(5) +
                        IntRange(857, 929).shuffled().take(9) +
                        IntRange(930, 960).shuffled().take(9) +
                        IntRange(961, 972).shuffled().take(4)
}