package com.example.younghunter

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.younghunter.databinding.ActivityDashboardBinding


class Dashboard : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding
    private var dpHeight: Int? = null

    //Declare background photos
    private val backgrounds = arrayOf(R.drawable.backimg_one, R.drawable.backimg_two, R.drawable.backimg_three, R.drawable.backimg_four, R.drawable.backimg_five, R.drawable.backimg_six, R.drawable.backimg_seven, R.drawable.backimg_eight, R.drawable.backimg_nine, R.drawable.backimg_ten)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //Gets display metrics
        val displayMetrics = resources.displayMetrics
        dpHeight = displayMetrics.heightPixels


        //Changes randomly background
        binding.dashBackground.layoutParams.height = dpHeight as Int
        binding.dashBackground.setImageResource(backgrounds.random())

        //Sets Help button
        binding.helpIcon.setOnClickListener{
            val intent = Intent(this,Help::class.java)
            startActivity(intent)
        }

        //Sets first button click
        binding.loadExam.setOnClickListener {
            val intent = Intent(this,QuizQuestionsExam::class.java)
            startActivity(intent)
            finish()
        }

        //Sets second button click
        binding.animals.setOnClickListener {
            val intent = Intent(this,QuizQuestionsAnimals::class.java)
            startActivity(intent)
            finish()
        }

        //Sets third button click
       binding.law.setOnClickListener {
            val intent = Intent(this,QuizQuestionsHuntingLaw::class.java)
            startActivity(intent)
            finish()
        }

        //Sets fourth button click
        binding.gameManagement.setOnClickListener {
            val intent = Intent(this,QuizQuestionsGameManagement::class.java)
            startActivity(intent)
            finish()
        }

        //Sets fifth button click
        binding.huntingMethods.setOnClickListener {
            val intent = Intent(this,QuizQuestionsHuntingMethods::class.java)
            startActivity(intent)
            finish()
        }

        //Sets sixth button click
        binding.guns.setOnClickListener {
            val intent = Intent(this,QuizQuestionsGuns::class.java)
            startActivity(intent)
            finish()
        }

        //Sets seventh button click
       binding.dogs.setOnClickListener {
            val intent = Intent(this,QuizQuestionsDogs::class.java)
            startActivity(intent)
            finish()
        }

        //Sets eighth button click
        binding.illnesses.setOnClickListener {
            val intent = Intent(this,QuizQuestionsViruses::class.java)
            startActivity(intent)
            finish()
        }

    }

    private var counter = 0
    override fun onBackPressed() {
        counter++
        if (counter==1) {
            Toast.makeText(this, R.string.toast, Toast.LENGTH_SHORT).show()
        }else {
            super.onBackPressed()
        }
    }
}