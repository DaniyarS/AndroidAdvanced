package com.daniyars.translator.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.daniyars.translator.R
import com.daniyars.translator.ui.mainNavFragments.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNav.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
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
                setFragment(VideoFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navDashboard -> {
                setFragment(DashboardFragment())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun setFragment(fragment: Fragment) {
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.mainContainer, fragment)
        fragmentTransaction.commit()
    }
}