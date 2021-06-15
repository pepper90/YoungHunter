package com.jpdevzone.younghunter

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jpdevzone.younghunter.databinding.ActivityFinishQuizBinding

class FinishQuiz : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    private lateinit var binding: ActivityFinishQuizBinding

    //Declare background photos
    private val backgrounds = arrayOf(R.drawable.backimg_one, R.drawable.backimg_two, R.drawable.backimg_three, R.drawable.backimg_four, R.drawable.backimg_five, R.drawable.backimg_six, R.drawable.backimg_seven, R.drawable.backimg_eight, R.drawable.backimg_nine, R.drawable.backimg_ten)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishQuizBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //Changes randomly background
        binding.finishQuiz.setBackgroundResource(backgrounds.random())

        val totalQuestions = intent.getIntExtra(Constants.TOTAL_QUESTIONS, 0)
        val correctAnswers = intent.getIntExtra(Constants.CORRECT_ANSWERS, 0)
        val correctAnswersExam = intent.getIntExtra(Constants.CORRECT_ANSWERS_EXAM, 0)

        when (totalQuestions) {
            30 -> {
                if (correctAnswers < 25){
                    binding.imgSuccess.setImageResource(R.drawable.dissatisfied)
                    binding.tvCongrats.setText(R.string.failure)
                    binding.tvCongrats.setBackgroundResource(R.drawable.result_failure_top)
                    binding.tvScore.text = getString(R.string.fail1, correctAnswers, totalQuestions)
                    binding.tvScore.setBackgroundResource(R.drawable.result_failure_bottom)
                } else {
                    binding.imgSuccess.setImageResource(R.drawable.satisfied)
                    binding.tvCongrats.setText(R.string.success)
                    binding.tvCongrats.setBackgroundResource(R.drawable.result_success_top)
                    binding.tvScore.text = getString(R.string.success1, correctAnswers, totalQuestions)
                    binding.tvScore.setBackgroundResource(R.drawable.result_success_bottom)
                }
            }
            104 -> {
                if (correctAnswersExam < 80){
                    binding.imgSuccess.setImageResource(R.drawable.dissatisfied)
                    binding.tvCongrats.setText(R.string.failure)
                    binding.tvCongrats.setBackgroundResource(R.drawable.result_failure_top)
                    binding.tvScore.text = getString(R.string.fail2, correctAnswersExam, totalQuestions)
                    binding.tvScore.setBackgroundResource(R.drawable.result_failure_bottom)
                } else {
                    binding.imgSuccess.setImageResource(R.drawable.satisfied)
                    binding.tvCongrats.setText(R.string.success)
                    binding.tvCongrats.setBackgroundResource(R.drawable.result_success_top)
                    binding.tvScore.text = getString(R.string.success2, correctAnswersExam, totalQuestions)
                    binding.tvScore.setBackgroundResource(R.drawable.result_success_bottom)
                }
            }
        }

        binding.toDashboard.setOnClickListener {
            startActivity(Intent(this, Dashboard::class.java))
            finish()
        }
    }

    override fun onBackPressed() {
            val intent = Intent(this, Dashboard::class.java)
            startActivity(intent)
            finish()
    }

}



