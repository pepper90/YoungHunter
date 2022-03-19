package com.jpdevzone.younghunter.help

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.jpdevzone.younghunter.R
import com.jpdevzone.younghunter.databinding.FragmentHelpBinding
import com.jpdevzone.younghunter.setBackground

class HelpFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding : FragmentHelpBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_help, container, false)
        binding.helpBackground.setImageResource(setBackground)

        binding.arrowBackIv.setOnClickListener {
            this.findNavController().navigate(
                HelpFragmentDirections.actionHelpFragmentToDashboardFragment()
            )
        }
        return binding.root
    }
}