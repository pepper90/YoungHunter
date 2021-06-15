package com.jpdevzone.younghunter

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.jpdevzone.younghunter.databinding.ActivityDashboardBinding
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import java.util.*


class Dashboard : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding
    private var dpHeight: Int? = null
    private var dialogTitle: String? = null
    private var dialogString: String? = null
    private val startTimeInMillisMini: Long = 1500000
    private val startTimeInMillisMaxi: Long = 5400000
    private var myIntent: Intent? = null
    private var mySharedPreferences: SharedPreferences? = null
    private lateinit var mAdView : AdView

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
            finish()
        }

        //Sets first button click
        binding.loadExam.setOnClickListener {
            mySharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES_EXAM, MODE_PRIVATE)
            if (mySharedPreferences?.getString(Constants.QLIST,null) != null) {
                dialogTitle = getString(R.string.examYoungHunter)
                val position = mySharedPreferences?.getInt(Constants.CURRENT_POSITION,1)
                val timer = mySharedPreferences?.getLong(Constants.TIMER,startTimeInMillisMaxi)
                dialogString = getString(R.string.continueTest104, position,toTime(timer!!))
                myIntent = Intent(this, QuizQuestionsExam::class.java)
                dashboardDialog()
            } else {
                val intent = Intent(this, QuizQuestionsExam::class.java)
                startActivity(intent)
                finish()
            }
        }

        //Sets second button click
        binding.animals.setOnClickListener {
            mySharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES_ANIMALS, MODE_PRIVATE)
            if (mySharedPreferences?.getString(Constants.QLIST,null) != null) {
                dialogTitle = getString(R.string.animals)
                val position = mySharedPreferences?.getInt(Constants.CURRENT_POSITION,1)
                val timer = mySharedPreferences?.getLong(Constants.TIMER,startTimeInMillisMini)
                dialogString = getString(R.string.continueTest30, position,toTime(timer!!))
                myIntent = Intent(this, QuizQuestionsAnimals::class.java)
                dashboardDialog()
            } else {
                val intent = Intent(this, QuizQuestionsAnimals::class.java)
                startActivity(intent)
                finish()

            }
        }

        //Sets third button click
       binding.law.setOnClickListener {
           mySharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES_LAW, MODE_PRIVATE)
           if (mySharedPreferences?.getString(Constants.QLIST,null) != null) {
               dialogTitle = getString(R.string.law)
               val position = mySharedPreferences?.getInt(Constants.CURRENT_POSITION,1)
               val timer = mySharedPreferences?.getLong(Constants.TIMER,startTimeInMillisMini)
               dialogString = getString(R.string.continueTest30, position,toTime(timer!!))
               myIntent = Intent(this, QuizQuestionsHuntingLaw::class.java)
               dashboardDialog()
           } else {
               val intent = Intent(this, QuizQuestionsHuntingLaw::class.java)
               startActivity(intent)
               finish()
           }
        }

        //Sets fourth button click
        binding.gameManagement.setOnClickListener {
            mySharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES_GM, MODE_PRIVATE)
            if (mySharedPreferences?.getString(Constants.QLIST,null) != null) {
                dialogTitle = getString(R.string.gameManagement)
                val position = mySharedPreferences?.getInt(Constants.CURRENT_POSITION,1)
                val timer = mySharedPreferences?.getLong(Constants.TIMER,startTimeInMillisMini)
                dialogString = getString(R.string.continueTest30, position,toTime(timer!!))
                myIntent = Intent(this, QuizQuestionsGameManagement::class.java)
                dashboardDialog()
            } else {
                val intent = Intent(this, QuizQuestionsGameManagement::class.java)
                startActivity(intent)
                finish()
            }
        }

        //Sets fifth button click
        binding.huntingMethods.setOnClickListener {
            mySharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES_HUNTING, MODE_PRIVATE)
            if (mySharedPreferences?.getString(Constants.QLIST,null) != null) {
                dialogTitle = getString(R.string.huntingMethods)
                val position = mySharedPreferences?.getInt(Constants.CURRENT_POSITION,1)
                val timer = mySharedPreferences?.getLong(Constants.TIMER,startTimeInMillisMini)
                dialogString = getString(R.string.continueTest30, position,toTime(timer!!))
                myIntent = Intent(this, QuizQuestionsHuntingMethods::class.java)
                dashboardDialog()
            } else {
                val intent = Intent(this, QuizQuestionsHuntingMethods::class.java)
                startActivity(intent)
                finish()
            }
        }

        //Sets sixth button click
        binding.guns.setOnClickListener {
            mySharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES_GUNS, MODE_PRIVATE)
            if (mySharedPreferences?.getString(Constants.QLIST,null) != null) {
                dialogTitle = getString(R.string.guns)
                val position = mySharedPreferences?.getInt(Constants.CURRENT_POSITION,1)
                val timer = mySharedPreferences?.getLong(Constants.TIMER,startTimeInMillisMini)
                dialogString = getString(R.string.continueTest30, position,toTime(timer!!))
                myIntent = Intent(this, QuizQuestionsGuns::class.java)
                dashboardDialog()
            } else {
                val intent = Intent(this, QuizQuestionsGuns::class.java)
                startActivity(intent)
                finish()
            }
        }

        //Sets seventh button click
       binding.dogs.setOnClickListener {
           mySharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES_DOGS, MODE_PRIVATE)
           if (mySharedPreferences?.getString(Constants.QLIST,null) != null) {
               dialogTitle = getString(R.string.dogs)
               val position = mySharedPreferences?.getInt(Constants.CURRENT_POSITION,1)
               val timer = mySharedPreferences?.getLong(Constants.TIMER,startTimeInMillisMini)
               dialogString = getString(R.string.continueTest30, position,toTime(timer!!))
               myIntent = Intent(this, QuizQuestionsDogs::class.java)
               dashboardDialog()
           } else {
               val intent = Intent(this, QuizQuestionsDogs::class.java)
               startActivity(intent)
               finish()
           }
        }

        //Sets eighth button click
        binding.illnesses.setOnClickListener {
            mySharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES_VIRUSES, MODE_PRIVATE)
            if (mySharedPreferences?.getString(Constants.QLIST,null) != null) {
                dialogTitle = getString(R.string.illnesses)
                val position = mySharedPreferences?.getInt(Constants.CURRENT_POSITION,1)
                val timer = mySharedPreferences?.getLong(Constants.TIMER,startTimeInMillisMini)
                dialogString = getString(R.string.continueTest30, position,toTime(timer!!))
                myIntent = Intent(this, QuizQuestionsViruses::class.java)
                dashboardDialog()
            } else {
                val intent = Intent(this, QuizQuestionsViruses::class.java)
                startActivity(intent)
                finish()
            }
        }

        //Activates adds
        MobileAds.initialize(this) {}
        mAdView = binding.homeAd
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
    }

    private fun dashboardDialog() {
        val dialog = AlertDialog.Builder(this@Dashboard, R.style.DialogSlideAnim)
        val dialogLayout = layoutInflater.inflate(R.layout.dialog_dashboard, null)
        dialog.setView(dialogLayout)
        val alertDialog = dialog.create()

        val title = dialogLayout.findViewById<TextView>(R.id.tv_dialogTitle)
        title.text = dialogTitle



        val continueTest = dialogLayout.findViewById<TextView>(R.id.tv_continueTest)
        val ss1 = SpannableString(dialogString)
        ss1.setSpan(RelativeSizeSpan(1.3f),0,13, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        ss1.setSpan(StyleSpan(Typeface.BOLD),0,13,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        continueTest.text = ss1

        continueTest.setOnClickListener{
            alertDialog.dismiss()
            val intent = myIntent
            startActivity(intent)
            finish()
        }

        val startNewTest = dialogLayout.findViewById<TextView>(R.id.tv_startNewTest)
        val ss2 = SpannableString(startNewTest.text)
        ss2.setSpan(RelativeSizeSpan(1.3f),0,16, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        ss2.setSpan(StyleSpan(Typeface.BOLD),0,16,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        startNewTest.text = ss2

        startNewTest.setOnClickListener {
            alertDialog.dismiss()
            val sharedPreferences = mySharedPreferences!!
            val editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()
            val intent = myIntent
            startActivity(intent)
            finish()
        }

        val dismiss = dialogLayout.findViewById<ImageView>(R.id.iv_dismiss)
        dismiss.setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.show()
    }

    private fun toTime(timer: Long) : String {
        val minutes: Int = ((timer / 1000) / 60).toInt()
        val seconds: Int = ((timer / 1000) % 60).toInt()
        return (String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds))
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