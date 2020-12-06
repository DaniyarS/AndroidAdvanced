package com.daniyars.translatorapp.ui.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.daniyars.translatorapp.R
import com.daniyars.translatorapp.local.LocalPreferences
import com.daniyars.translatorapp.ui.activities.SplashActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_one.*
import kotlinx.android.synthetic.main.user_list_items.totalTimeSpentTextView
import kotlinx.android.synthetic.main.user_list_items.totalWordsTextView

class OneFragment : Fragment(R.layout.fragment_one) {
    private var firebaseAuth: FirebaseAuth? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPreferences = LocalPreferences(context)

        totalWordsTextView.text = sharedPreferences.getTotalWords()
        totalTimeSpentTextView.text = sharedPreferences.getTotalTime()

        signOutButton.setOnClickListener {
            firebaseAuth?.signOut()
            sharedPreferences.setLogState(false)
            val intent = Intent(context, SplashActivity::class.java)
            startActivity(intent)
        }
    }
}