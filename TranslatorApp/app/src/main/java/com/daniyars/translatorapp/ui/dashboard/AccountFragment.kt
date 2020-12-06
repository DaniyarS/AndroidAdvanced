package com.daniyars.translatorapp.ui.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.FragmentTransaction
import com.daniyars.translatorapp.R
import com.daniyars.translatorapp.ui.mainNavFragments.DashboardFragment
import kotlinx.android.synthetic.main.fragment_account.*

class AccountFragment : Fragment(R.layout.fragment_account) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentManager = fragmentManager

        signInButton.setOnClickListener {
            val fragmentTransaction: FragmentTransaction? = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.mainContainer, DashboardFragment())
            fragmentTransaction?.commit()
        }
    }
}