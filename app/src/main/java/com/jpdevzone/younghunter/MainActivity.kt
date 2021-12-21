package com.jpdevzone.younghunter

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowInsets
import android.view.WindowManager
import com.jpdevzone.younghunter.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var dpHeight: Int? = null
    private var dpWidth: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        //Gets display metrics
        val displayMetrics = resources.displayMetrics
        dpWidth = displayMetrics.widthPixels
        dpHeight = displayMetrics.heightPixels

        binding.splashScreen.layoutParams.height = dpHeight as Int
        binding.splashScreen.layoutParams.width = dpWidth as Int

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@MainActivity, FragmentContainer::class.java)
            startActivity(intent)
            finish()
        }, 5000 )
    }
}