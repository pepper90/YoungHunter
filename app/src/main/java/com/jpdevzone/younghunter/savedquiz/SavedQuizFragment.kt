package com.jpdevzone.younghunter.savedquiz

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.jpdevzone.younghunter.R
import com.jpdevzone.younghunter.databinding.FragmentSavedQuizBinding
import com.jpdevzone.younghunter.utils.setBackground
import es.dmoral.toasty.Toasty

class SavedQuizFragment : Fragment() {

    private lateinit var binding : FragmentSavedQuizBinding
    private lateinit var viewModel: SavedQuizViewModel
    private lateinit var args: SavedQuizFragmentArgs
    private var mInterstitialAd: InterstitialAd? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_saved_quiz, container, false)

        viewModel = ViewModelProvider(
            this,
            SavedQuizViewModelFactory(requireActivity().application)
        )[SavedQuizViewModel::class.java]

        binding.vm = viewModel

        binding.lifecycleOwner = viewLifecycleOwner

        loadAd()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Sets random background
        binding.quizQuestionBackground.setImageResource(setBackground)

        // Gets arguments from Bundle and bind them to xml
        args = SavedQuizFragmentArgs.fromBundle(requireArguments())
        binding.dashData = args.dashData

        // Setup quiz environment based on topic from safeArgs
        viewModel.setupEnvironment(args.dashData.topic)

        // Loads next question
        // Triggers navigation to FinishQuizFragment
        loadQuestion()

        // Sets navigation to FinishQuizFragment
        finishQuiz()

        // Sets back navigation
        binding.arrowBackIv.setOnClickListener {
            showQuitDialog()
        }

        // Sets reload button
        binding.reloadIv.setOnClickListener {
            showReloadDialog()
        }

        // Handles onBackPressed
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            showQuitDialog()
        }
    }

    // Loads next question
    private fun loadQuestion() {
        viewModel.position.observe(viewLifecycleOwner) { position ->
            if (position != null) {
                viewModel.loadOrFinish(position)
            }
        }
    }

    // Navigate to FinishQuizFragment
    private fun finishQuiz() {
        viewModel.navigateToFinish.observe(viewLifecycleOwner) {
            if (it == true) {
                // show ad
                showInterstitial()
                this.findNavController().navigate(
                    SavedQuizFragmentDirections.actionSavedQuizFragmentToFinishQuizFragment(
                        viewModel.totalAnswers.value!!,
                        viewModel.progressBarMax.value!!,
                        viewModel.elapsedTime.value!!
                    )
                )
                viewModel.clearProgress(args.dashData.topic)
                viewModel.doneNavigating()
            }
        }
    }

    // Reload QuizQuestionFragment
    private fun reload() {
        viewModel.clearProgress(args.dashData.topic)
        this.findNavController().navigate(
            SavedQuizFragmentDirections.actionSavedQuizFragmentToQuizQuestionFragment(args.dashData)
        )
    }

    // Navigate to DashboardFragment
    private fun navigateBack() {
        this.findNavController().navigate(
            SavedQuizFragmentDirections.actionSavedQuizFragmentToDashboardFragment()
        )
    }

    // Show Reload dialog
    private fun showReloadDialog() {
        viewModel.pauseTimer()
        val dialog = AlertDialog.Builder(requireContext())
        val dialogLayout = layoutInflater.inflate(R.layout.dialog_reload, null)
        dialog.setView(dialogLayout)
        val alertDialog = dialog.create()

        // redirects to QuizQuestionFragment & loads a fresh test
        val yes = dialogLayout.findViewById<TextView>(R.id.yes_btn)
        yes.setOnClickListener {
            alertDialog.dismiss()
            reload()
        }

        // cancels dialog and resumes timer
        val no = dialogLayout.findViewById<TextView>(R.id.no_btn)
        no.setOnClickListener {
            alertDialog.dismiss()
            viewModel.resumeTimer()
        }

        // cancels dialog and resumes timer
        val dismiss = dialogLayout.findViewById<ImageView>(R.id.dismiss_dialog_iv)
        dismiss.setOnClickListener {
            alertDialog.dismiss()
            viewModel.resumeTimer()
        }

        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    // Shows Quit dialog
    private fun showQuitDialog() {
        viewModel.pauseTimer()
        val dialog = AlertDialog.Builder(requireContext())
        val dialogLayout = layoutInflater.inflate(R.layout.dialog_quit, null)
        dialog.setView(dialogLayout)
        val alertDialog = dialog.create()

        // saves data to DB & navigates back to dashboard
        val yes = dialogLayout.findViewById<TextView>(R.id.yes_btn)
        yes.setOnClickListener {
            viewModel.saveProgress(args.dashData.topic)
            alertDialog.dismiss()
            navigateBack()
        }

        // clear data from DB & navigates back to dashboard
        val no = dialogLayout.findViewById<TextView>(R.id.no_btn)
        no.setOnClickListener {
            viewModel.clearProgress(args.dashData.topic)
            alertDialog.dismiss()
            navigateBack()
        }

        // cancels dialog and resumes timer
        val dismiss = dialogLayout.findViewById<ImageView>(R.id.dismiss_dialog_iv)
        dismiss.setOnClickListener {
            alertDialog.dismiss()
            viewModel.resumeTimer()
        }

        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    // Load Interstitial ad
    private fun loadAd() {
        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(requireContext(),"ca-app-pub-3940256099942544/1033173712", adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d("AdMob", adError.message)
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                Log.d("AdMob", "Ad was loaded.")
                mInterstitialAd = interstitialAd
            }
        })
    }

    // Triggers interstitial ad
    private fun showInterstitial() {
        if (mInterstitialAd != null) {
            mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    Log.d("AdMob", "Ad was dismissed.")
                    mInterstitialAd = null
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    Log.d("AdMob", "Ad failed to show.")
                    mInterstitialAd = null
                }

                override fun onAdShowedFullScreenContent() {
                    Log.d("AdMob", "Ad showed fullscreen content.")
                    // Called when ad is dismissed.
                    mInterstitialAd = null
                }
            }
            mInterstitialAd?.show(requireActivity())
        } else {
            Toasty.custom(requireContext(), R.string.noAdLoaded,R.drawable.ic_no_ads,R.color.black,
                Toast.LENGTH_LONG,true, true).show()
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.pauseTimer()
    }

    override fun onResume() {
        super.onResume()
        viewModel.resumeTimer()
    }
}