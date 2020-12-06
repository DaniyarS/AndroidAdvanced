package com.daniyars.translatorapp.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.daniyars.translatorapp.R
import com.daniyars.translatorapp.local.LocalPreferences
import com.daniyars.translatorapp.ui.dashboard.AccountFragment
import com.daniyars.translatorapp.ui.mainNavFragments.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var themePreferences: LocalPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        themePreferences = LocalPreferences(this)
        val themeState = themePreferences.getThemeState()
        if (themeState == "dark") {
            setTheme(R.style.NightTheme)
            Log.d("SetTheme", "set theme works")
        } else {
            setTheme(R.style.DayTheme)
            Log.d("SetTheme", "set theme works")
        }
        setContentView(R.layout.activity_main)
        bottomNav.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        if (themePreferences.getIntent() == "main") {
            setFragment(KirLatFragment())
        } else {
            setFragment(KazRusEngFragment())
        }
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {
        when (it.itemId) {
            R.id.navKazRusEng -> {
                setFragment(KazRusEngFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navFavorites -> {
                setFragment(FavoritesFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navKirLat -> {
                setFragment(KirLatFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navVideo -> {
                setFragment(VideoAudioFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navDashboard -> {
                if (themePreferences.getLogState() == true) {
                    setFragment(DashboardFragment())
                } else {
                    setFragment(AccountFragment())
                }
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onStop() {
        val end = Calendar.getInstance().timeInMillis
        val sharedPreferences = LocalPreferences(this)
        sharedPreferences.setTotalEndTime(end.toString())
        Log.d("endTime", end.toString())
        super.onStop()
    }

    private fun setFragment(fragment: Fragment) {
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.mainContainer, fragment)
        fragmentTransaction.commit()
    }
}