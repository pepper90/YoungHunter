package com.jpdevzone.younghunter.quizquestion

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.format.DateUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.jpdevzone.younghunter.R
import com.jpdevzone.younghunter.database.QuestionsDatabase
import com.jpdevzone.younghunter.databinding.FragmentQuizQuestionBinding
import com.jpdevzone.younghunter.setBackground


class QuizQuestionFragment : Fragment(), View.OnClickListener {
    private lateinit var binding : FragmentQuizQuestionBinding
    private lateinit var viewModel: QuizQuestionViewModel
    private lateinit var viewModelFactory: QuizQuestionViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_quiz_question, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = QuestionsDatabase.getInstance(application).questionsDatabaseDao
        viewModelFactory = QuizQuestionViewModelFactory(dataSource, application)
        viewModel =
            ViewModelProvider(
                this, viewModelFactory)[QuizQuestionViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        //Sets random background
        binding.quizQuestionBackground.setImageResource(setBackground)

        binding.arrowBackIv.setOnClickListener {
            this.findNavController().navigate(
                QuizQuestionFragmentDirections.actionQuizQuestionFragmentToDashboardFragment()
            )
        }

        val args = QuizQuestionFragmentArgs.fromBundle(requireArguments())
        binding.categoryTv.text = getString(args.title)
        binding.categoryTimeTv.text = getString(args.subtitle)
        binding.categoryIv.setImageResource(args.icon)

        binding.progressBar.max = viewModel.setProgressBarMax(args.topic)

        viewModel.timer(args.topic)
        viewModel.getQuestions(args.topic)

        viewModel.currentTime.observe(viewLifecycleOwner, { newTime ->
            binding.timer.text = DateUtils.formatElapsedTime(newTime)

        })

        viewModel.position.observe(viewLifecycleOwner, {
            binding.progressBar.progress = it
            binding.progressTv.text = getString(
                R.string.position,
                it,
                binding.progressBar.max
            )
        })

        viewModel.questionsList.observe(viewLifecycleOwner, {
            binding.questionTv.text = it[viewModel.position.value?.minus(1)!!].question
            binding.optionOneTv.text = it[viewModel.position.value?.minus(1)!!].optionOne
            binding.optionTwoTv.text = it[viewModel.position.value?.minus(1)!!].optionTwo
            binding.optionThreeTv.text = it[viewModel.position.value?.minus(1)!!].optionThree
        })

        binding.optionOneTv.setOnClickListener(this)
        binding.optionTwoTv.setOnClickListener(this)
        binding.optionThreeTv.setOnClickListener(this)
        binding.nextTv.setOnClickListener(this)

        return binding.root
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.nextTv -> viewModel.onNext()
        }
    }

//    private fun defaultOptionsView(context: Context){
//        val options = ArrayList<TextView>()
//        options.add(0,binding.optionOneTv)
//        options.add(1,binding.optionTwoTv)
//        options.add(2,binding.optionThreeTv)
//
//        for (option in options){
//            option.setTextColor(Color.parseColor("#ffffff"))
//            option.typeface = Typeface.DEFAULT
//            option.background = ContextCompat.getDrawable(context, R.drawable.border)
//        }
//
//    }

}