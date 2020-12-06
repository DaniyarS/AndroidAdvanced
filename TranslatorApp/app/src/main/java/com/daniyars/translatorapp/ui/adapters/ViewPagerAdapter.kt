package com.daniyars.translatorapp.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.daniyars.translatorapp.ui.dashboard.OneFragment
import com.daniyars.translatorapp.ui.dashboard.TwoFragment

class ViewPagerAdapter(fragmentManager: FragmentManager?) : FragmentStatePagerAdapter(fragmentManager!!, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val fragments = mutableListOf<Fragment>()

    init {
        fragments.add(OneFragment())
        fragments.add(TwoFragment())
    }

    override fun getItem(position: Int): Fragment =
        fragments[position]

    override fun getCount(): Int = fragments.size

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> {
                return "Жеке статистика"
            }
            1 -> {
                return "Барлық қолданушылар"
            }
        }
        return position.toString()
    }
}