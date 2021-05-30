package com.example.younghunter

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.younghunter.databinding.ActivityDashboardBinding


class Dashboard : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding
    private var dpHeight: Int? = null
    private var dpWidth: Int? = null
    private var dDensity: Float? = null
    private var designHeight: Int = 1080
    private var designWidth: Int = 920



    //Declare background photos
    private val backgrounds = arrayOf(R.drawable.backimg_one, R.drawable.backimg_two, R.drawable.backimg_three, R.drawable.backimg_four, R.drawable.backimg_five, R.drawable.backimg_six, R.drawable.backimg_seven, R.drawable.backimg_eight, R.drawable.backimg_nine, R.drawable.backimg_ten)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //Gets display metrics
        val displayMetrics = resources.displayMetrics
        dpWidth = displayMetrics.widthPixels
        dpHeight = displayMetrics.heightPixels
        dDensity = displayMetrics.scaledDensity


        //Changes randomly background
        binding.dashBackground.layoutParams.height = dpHeight as Int
        binding.dashBackground.setImageResource(backgrounds.random())

        //Sets Help button
        binding.helpIcon.layoutParams.height = calcHeight(30).toInt()
        binding.helpIcon.layoutParams.width = calcWidth(30).toInt()
        binding.helpIcon.setOnClickListener{
            val intent = Intent(this,Help::class.java)
            startActivity(intent)
        }

        //Sets first button
        binding.loadExam.textSize = calcText(32f)
        binding.loadExam.setPadding(calcWidth(10).toInt(),calcHeight(6).toInt(),calcWidth(10).toInt(),calcHeight(6).toInt())
        binding.loadExam.setOnClickListener {
            val intent = Intent(this,QuizQuestionsExam::class.java)
            startActivity(intent)
            finish()
        }

        //Sets text size
        binding.miniTests.textSize = calcText(32f)
        val miniTestsLayoutParams = binding.miniTests.layoutParams as ViewGroup.MarginLayoutParams
        miniTestsLayoutParams.setMargins(calcWidth(12).toInt(),calcHeight(12).toInt(),calcWidth(12).toInt(),calcHeight(12).toInt())

        //Sets second button click
        binding.animals.textSize = calcText(32f)
        binding.animals.setPadding(calcWidth(10).toInt(),calcHeight(6).toInt(),calcWidth(10).toInt(),calcHeight(6).toInt())
        val animalsLayoutParams = binding.animals.layoutParams as ViewGroup.MarginLayoutParams
        animalsLayoutParams.setMargins(0,0,0,calcHeight(5).toInt())
        binding.animals.setOnClickListener {
            val intent = Intent(this,QuizQuestionsAnimals::class.java)
            startActivity(intent)
            finish()
        }

        //Sets third button click
        binding.law.textSize = calcText(32f)
        binding.law.setPadding(calcWidth(10).toInt(),calcHeight(6).toInt(),calcWidth(10).toInt(),calcHeight(6).toInt())
        val lawLayoutParams = binding.law.layoutParams as ViewGroup.MarginLayoutParams
        lawLayoutParams.setMargins(0,0,0,calcHeight(5).toInt())
        binding.law.setOnClickListener {
            val intent = Intent(this,QuizQuestionsHuntingLaw::class.java)
            startActivity(intent)
            finish()
        }

        //Sets fourth button click
        binding.gameManagement.textSize = calcText(32f)
        binding.gameManagement.setPadding(calcWidth(10).toInt(),calcHeight(6).toInt(),calcWidth(10).toInt(),calcHeight(6).toInt())
        val gameManagementLayoutParams = binding.gameManagement.layoutParams as ViewGroup.MarginLayoutParams
        gameManagementLayoutParams.setMargins(0,0,0,calcHeight(5).toInt())
        binding.gameManagement.setOnClickListener {
            val intent = Intent(this,QuizQuestionsGameManagement::class.java)
            startActivity(intent)
            finish()
        }

        //Sets fifth button click
        binding.huntingMethods.textSize = calcText(32f)
        binding.huntingMethods.setPadding(calcWidth(10).toInt(),calcHeight(6).toInt(),calcWidth(10).toInt(),calcHeight(6).toInt())
        val huntingMethodsLayoutParams = binding.huntingMethods.layoutParams as ViewGroup.MarginLayoutParams
        huntingMethodsLayoutParams.setMargins(0,0,0,calcHeight(5).toInt())
        binding.huntingMethods.setOnClickListener {
            val intent = Intent(this,QuizQuestionsHuntingMethods::class.java)
            startActivity(intent)
            finish()
        }

        //Sets sixth button click
        binding.guns.textSize = calcText(32f)
        binding.guns.setPadding(calcWidth(10).toInt(),calcHeight(6).toInt(),calcWidth(10).toInt(),calcHeight(6).toInt())
        val gunsLayoutParams = binding.guns.layoutParams as ViewGroup.MarginLayoutParams
        gunsLayoutParams.setMargins(0,0,0,calcHeight(5).toInt())
        binding.guns.setOnClickListener {
            val intent = Intent(this,QuizQuestionsGuns::class.java)
            startActivity(intent)
            finish()
        }

        //Sets seventh button click
        binding.dogs.textSize = calcText(32f)
        binding.dogs.setPadding(calcWidth(10).toInt(),calcHeight(6).toInt(),calcWidth(10).toInt(),calcHeight(6).toInt())
        val dogsLayoutParams = binding.dogs.layoutParams as ViewGroup.MarginLayoutParams
        dogsLayoutParams.setMargins(0,0,0,calcHeight(5).toInt())
        binding.dogs.setOnClickListener {
            val intent = Intent(this,QuizQuestionsDogs::class.java)
            startActivity(intent)
            finish()
        }

        //Sets eighth button click
        binding.illnesses.textSize = calcText(32f)
        binding.illnesses.setPadding(calcWidth(10).toInt(),calcHeight(6).toInt(),calcWidth(10).toInt(),calcHeight(6).toInt())
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

    private fun calcHeight(value: Int): Float {

        return (dpHeight!! * ((value * dDensity!!)/designHeight))
    }
    private fun calcWidth(value: Int): Float {
        return (dpWidth!! * ((value * dDensity!!)/designWidth))
    }

    private fun calcText(valueTxt: Float): Float {
        return (dpHeight!! * (valueTxt/designHeight) / dDensity!!)
    }
}