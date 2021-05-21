package com.example.younghunter

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.younghunter.databinding.ActivityQuizQuestionsBinding
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback


class QuizQuestionsAnimals : AppCompatActivity(), View.OnClickListener {
    @SuppressLint("SetTextI18n")
    private lateinit var binding: ActivityQuizQuestionsBinding
    private var mInterstitialAd: InterstitialAd? = null

    //Declare background photos
    private val backgrounds = arrayOf(R.drawable.backimg_one, R.drawable.backimg_two, R.drawable.backimg_three, R.drawable.backimg_four, R.drawable.backimg_five, R.drawable.backimg_six, R.drawable.backimg_seven, R.drawable.backimg_eight, R.drawable.backimg_nine, R.drawable.backimg_ten)

    private var mCurrentPosition:Int = 1
    private var mQuestionsList:ArrayList<Question>? = null
    private var mSelectedOptionPosition:Int = 0
    private var mCorrectAnswers:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizQuestionsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //Changes randomly background
        binding.quizquestions.setBackgroundResource(backgrounds.random())

        //Sets back button
        binding.backButton.setOnClickListener {
            if (mCurrentPosition == 1) {
                val intent = Intent(this, Dashboard::class.java)
                startActivity(intent)
                finish()
            } else {
                alertDialogFunction()
            }
        }

        //Changes category text
        binding.tvHeader.setText(R.string.animals)

        //Changes category icon
        binding.tvHeader.setCompoundDrawablesWithIntrinsicBounds(R.drawable.animals, 0, 0, 0)

        //Changes progressbar max questions number
        binding.progressBar.max = 30

        //Loads questions list
        mQuestionsList = Constants.getQuestionsAnimals().shuffled().take(30) as ArrayList<Question>

        setQuestionAnimals()

        binding.tvOptionOne.setOnClickListener(this)
        binding.tvOptionTwo.setOnClickListener(this)
        binding.tvOptionThree.setOnClickListener(this)
        binding.next.setOnClickListener(null)

        //Sets adds
        MobileAds.initialize(this) {}
        createPersonalizedAdd()
    }

    //This function assigns the questions ---------------------------------------------------------
    @SuppressLint("SetTextI18n")
    private fun setQuestionAnimals(){

        val question: Question? = mQuestionsList!![mCurrentPosition - 1]

        defaultOptionsView()

        if(mCurrentPosition == mQuestionsList!!.size){
            binding.next.setText(R.string.mark)
        }else{
            binding.next.setText(R.string.mark)
        }

        //Sets progress bar
        binding.progressBar.progress = mCurrentPosition
        binding.tvProgress.text = "$mCurrentPosition" + "/" + binding.progressBar.max

        //Loads question & options from list
        binding.tvQuestion.text = question!!.question
        binding.tvOptionOne.text = question.optionOne
        binding.tvOptionTwo.text = question.optionTwo
        binding.tvOptionThree.text = question.optionThree
    }

    //This function assigns the default view -------------------------------------------------------
    private fun defaultOptionsView(){
        val options = ArrayList<TextView>()
        options.add(0,binding.tvOptionOne)
        options.add(1,binding.tvOptionTwo)
        options.add(2,binding.tvOptionThree)

        for (option in options){
            option.setTextColor(Color.parseColor("#ffffff"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(this, R.drawable.border)
        }

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_option_one -> {
                selectedOptionView(binding.tvOptionOne, selectedOptionNumber = 1)
                binding.next.setOnClickListener(this)
            }
            R.id.tv_option_two -> {
                selectedOptionView(binding.tvOptionTwo, selectedOptionNumber = 2)
                binding.next.setOnClickListener(this)
            }
            R.id.tv_option_three -> {
                selectedOptionView(binding.tvOptionThree, selectedOptionNumber = 3)
                binding.next.setOnClickListener(this)
            }
            R.id.next -> {

                if (mSelectedOptionPosition == 0){
                    mCurrentPosition++
                    binding.tvOptionOne.setOnClickListener(this)
                    binding.tvOptionTwo.setOnClickListener(this)
                    binding.tvOptionThree.setOnClickListener(this)
                    binding.next.setOnClickListener(null)


                    when {
                        mCurrentPosition <= mQuestionsList!!.size ->{setQuestionAnimals()}
                        else -> {
                            if (mInterstitialAd != null) {
                                mInterstitialAd?.show(this)
                            } else {
                            val intent = Intent(this, FinishQuiz::class.java)
                            intent.putExtra(Constants.CORRECT_ANSWERS, mCorrectAnswers)
                            intent.putExtra(Constants.TOTAL_QUESTIONS, mQuestionsList!!.size)
                            startActivity(intent)
                            finish()
                            }
                        }
                    }
                } else {
                    val question = mQuestionsList?.get(mCurrentPosition-1)
                    if(question!!.correctAnswer != mSelectedOptionPosition){
                        answerView(mSelectedOptionPosition, R.drawable.wrong_option)
                    }else{
                        mCorrectAnswers++
                    }
                    answerView(question.correctAnswer, R.drawable.correct_option)

                    if(mCurrentPosition == mQuestionsList!!.size){
                        binding.next.setText(R.string.finish)
                    }else {
                        binding.next.setText(R.string.nextQue)
                    }
                    mSelectedOptionPosition = 0
                    binding.tvOptionOne.setOnClickListener(null)
                    binding.tvOptionTwo.setOnClickListener(null)
                    binding.tvOptionThree.setOnClickListener(null)


                }
            }
        }
    }

    private fun answerView (answer: Int, drawableView: Int) {
        when(answer){
            1 -> {binding.tvOptionOne.background = ContextCompat.getDrawable(this,drawableView)}
            2 -> {binding.tvOptionTwo.background = ContextCompat.getDrawable(this,drawableView)}
            3 -> {binding.tvOptionThree.background = ContextCompat.getDrawable(this,drawableView)}
        }
    }

    //This function sets the selected option -------------------------------------------------------
    private fun selectedOptionView(tv: TextView, selectedOptionNumber: Int){
        defaultOptionsView()
        mSelectedOptionPosition = selectedOptionNumber
        tv.setTextColor(Color.parseColor("#424242"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(this, R.drawable.selected_option)
    }

    private fun alertDialogFunction() {
        val dialog = AlertDialog.Builder(this@QuizQuestionsAnimals)
        val dialogLayout = layoutInflater.inflate(R.layout.dialog, null)
        dialog.setView(dialogLayout)
        val alertDialog = dialog.create()


        val exit = dialogLayout.findViewById<TextView>(R.id.tv_left)
        exit.setOnClickListener {
            val intent = Intent(this@QuizQuestionsAnimals, Dashboard::class.java)
            startActivity(intent)
            finish()
        }

        val back = dialogLayout.findViewById<TextView>(R.id.tv_right)
        back.setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.show()
        alertDialog.setCancelable(false)
    }

    override fun onBackPressed() {
        if (mCurrentPosition == 1) {
            val intent = Intent(this, Dashboard::class.java)
            startActivity(intent)
            finish()
        } else {
            alertDialogFunction()
        }
    }

    private fun createPersonalizedAdd() {
        val adRequest = AdRequest.Builder().build()
        createInterstitialAdd(adRequest)
    }

    private fun createInterstitialAdd(adRequest: AdRequest) {
        InterstitialAd.load(this,"ca-app-pub-7588987461083278/1798478193", adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                mInterstitialAd = interstitialAd

                mInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        val intent = Intent(this@QuizQuestionsAnimals, FinishQuiz::class.java)
                        intent.putExtra(Constants.CORRECT_ANSWERS, mCorrectAnswers)
                        intent.putExtra(Constants.TOTAL_QUESTIONS, mQuestionsList!!.size)
                        startActivity(intent)
                        finish()
                    }

                    override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                        val intent = Intent(this@QuizQuestionsAnimals, FinishQuiz::class.java)
                        intent.putExtra(Constants.CORRECT_ANSWERS, mCorrectAnswers)
                        intent.putExtra(Constants.TOTAL_QUESTIONS, mQuestionsList!!.size)
                        startActivity(intent)
                        finish()
                    }

                    override fun onAdShowedFullScreenContent() {
                        mInterstitialAd = null
                    }
                }
            }
        })
    }

}