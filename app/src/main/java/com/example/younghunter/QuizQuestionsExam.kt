package com.example.younghunter

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.younghunter.databinding.ActivityQuizQuestionsBinding
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import java.util.*
import kotlin.collections.ArrayList


class QuizQuestionsExam : AppCompatActivity(), View.OnClickListener {
    @SuppressLint("SetTextI18n")
    private lateinit var binding: ActivityQuizQuestionsBinding
    private var mInterstitialAd: InterstitialAd? = null

    //Declare background photos
    private val backgrounds = arrayOf(R.drawable.backimg_one, R.drawable.backimg_two, R.drawable.backimg_three, R.drawable.backimg_four, R.drawable.backimg_five, R.drawable.backimg_six, R.drawable.backimg_seven, R.drawable.backimg_eight, R.drawable.backimg_nine, R.drawable.backimg_ten)

    //Declare countdown timer
    private val startTimeInMillis: Long = 5400000
    private var mTimeLeftInMillis = startTimeInMillis

    //Declare questionnaire variables
    private var mCurrentPosition:Int = 1
    private var mQuestionsList:List<Question>? = null
    private var mSelectedOptionPosition:Int = 0
    private var mCorrectAnswers:Int = 0
    private var isSelected:Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizQuestionsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        loadData()

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
        binding.tvHeader.setText(R.string.examYoungHunter)

        //Changes category icon
        binding.ivHeader.setImageResource(R.drawable.ic_exam)

        //Sets reload button
        binding.ivReload.setOnClickListener {
            if (mCurrentPosition == 1) {
                val intent = intent
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                finish()
                startActivity(intent)
                overridePendingTransition(0, 0)
            } else {
                reloadDialogFunction()
            }
        }

        //Changes progressbar max questions number
        binding.progressBar.max = 104

        //Initializes timer
        startTimer()

        //Loads questions list
        mQuestionsList = Constants.loadExam.shuffled()

        setQuestionLoadExam()

        binding.tvOptionOne.setOnClickListener(this)
        binding.tvOptionTwo.setOnClickListener(this)
        binding.tvOptionThree.setOnClickListener(this)
        binding.next.setOnClickListener(null)

        //Sets adds
        MobileAds.initialize(this) {}
        createPersonalizedAdd()
    }

    private fun saveData() {
        val sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES_EXAM, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(Constants.CURRENT_POSITION, mCurrentPosition)
        editor.putInt(Constants.CORRECT_ANSWERS_EXAM,mCorrectAnswers)
        editor.putLong(Constants.TIMER, mTimeLeftInMillis)
        editor.apply()
    }

    private fun loadData() {
        val sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES_EXAM, MODE_PRIVATE)
        mCurrentPosition = sharedPreferences.getInt(Constants.CURRENT_POSITION,1)
        mCorrectAnswers = sharedPreferences.getInt(Constants.CORRECT_ANSWERS_EXAM,0)
        mTimeLeftInMillis = sharedPreferences.getLong(Constants.TIMER, startTimeInMillis)
    }

    private fun clearData() {
        val sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES_EXAM, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    //This function sets the timer ---------------------------------------------------------
    private fun startTimer() {
        object: CountDownTimer(mTimeLeftInMillis, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                mTimeLeftInMillis = millisUntilFinished
                updateCountDownText()
            }

            override fun onFinish() {
                if (mInterstitialAd != null) {
                    mInterstitialAd?.show(this@QuizQuestionsExam)
                } else {
                    val intent = Intent(this@QuizQuestionsExam, FinishQuiz::class.java)
                    intent.putExtra(Constants.CORRECT_ANSWERS_EXAM, mCorrectAnswers)
                    intent.putExtra(Constants.TOTAL_QUESTIONS, mQuestionsList!!.size)
                    clearData()
                    startActivity(intent)
                    finish()
                }
            }
        }.start()
    }

    private fun updateCountDownText() {
        val minutes: Int = ((mTimeLeftInMillis / 1000) / 60).toInt()
        val seconds: Int = ((mTimeLeftInMillis / 1000) % 60).toInt()
        val timeLeftFormatted: String = String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds)
        binding.timer.text = timeLeftFormatted
    }

    //This function assigns the questions ---------------------------------------------------------
    @SuppressLint("SetTextI18n")
    private fun setQuestionLoadExam(){

        val question: Question = mQuestionsList!![mCurrentPosition - 1]

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
        binding.tvQuestion.text = question.question
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

                    when (isSelected) {
                        true -> mCorrectAnswers++
                    }
                    Log.i("Answers","$mCorrectAnswers")

                    when {
                        mCurrentPosition <= mQuestionsList!!.size ->{setQuestionLoadExam()}
                        else -> {
                            if (mInterstitialAd != null) {
                                mInterstitialAd?.show(this)
                            } else {
                                val intent = Intent(this, FinishQuiz::class.java)
                                intent.putExtra(Constants.CORRECT_ANSWERS_EXAM, mCorrectAnswers)
                                intent.putExtra(Constants.TOTAL_QUESTIONS, mQuestionsList!!.size)
                                clearData()
                                startActivity(intent)
                                finish()
                            }
                        }
                    }
                } else {
                    val question = mQuestionsList?.get(mCurrentPosition-1)
                    isSelected = false
                    if(question!!.correctAnswer != mSelectedOptionPosition){
                        answerView(mSelectedOptionPosition, R.drawable.wrong_option)
                    }else{
                        isSelected = true
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

    private fun reloadDialogFunction() {
        val dialog = AlertDialog.Builder(this@QuizQuestionsExam)
        val dialogLayout = layoutInflater.inflate(R.layout.dialog_reload, null)
        dialog.setView(dialogLayout)
        val alertDialog = dialog.create()

        val yes = dialogLayout.findViewById<TextView>(R.id.tv_yes)
        yes.setOnClickListener {
            alertDialog.dismiss()
            clearData()
            val intent = intent
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            finish()
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        val no = dialogLayout.findViewById<TextView>(R.id.tv_no)
        no.setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.show()
    }

    //This functions sets the dialog window
    private fun alertDialogFunction() {
        val dialog = AlertDialog.Builder(this@QuizQuestionsExam)
        val dialogLayout = layoutInflater.inflate(R.layout.dialog, null)
        dialog.setView(dialogLayout)
        val alertDialog = dialog.create()


        val exit = dialogLayout.findViewById<TextView>(R.id.tv_left)
        exit.setOnClickListener {
            alertDialog.dismiss()
            saveData()
            val intent = Intent(this@QuizQuestionsExam, Dashboard::class.java)
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

    //THis function sets the back button
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
        InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712", adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                mInterstitialAd = interstitialAd

                mInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        val intent = Intent(this@QuizQuestionsExam, FinishQuiz::class.java)
                        intent.putExtra(Constants.CORRECT_ANSWERS_EXAM, mCorrectAnswers)
                        intent.putExtra(Constants.TOTAL_QUESTIONS, mQuestionsList!!.size)
                        clearData()
                        startActivity(intent)
                        finish()
                    }

                    override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                        val intent = Intent(this@QuizQuestionsExam, FinishQuiz::class.java)
                        intent.putExtra(Constants.CORRECT_ANSWERS_EXAM, mCorrectAnswers)
                        intent.putExtra(Constants.TOTAL_QUESTIONS, mQuestionsList!!.size)
                        clearData()
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