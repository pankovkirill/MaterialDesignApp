package com.example.materialdesignapp.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

private const val PHOTO_FRAGMENT = 0
private const val WEATHER_FRAGMENT = 1

class ViewPagerAdapter(private val fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager) {

    private val fragments = arrayOf(PhotoOfMarsFragment(), WeatherOfMarsFragment())

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> fragments[PHOTO_FRAGMENT]
            1 -> fragments[WEATHER_FRAGMENT]
            else -> fragments[PHOTO_FRAGMENT]
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Фото"
            1 -> "Погода"
            else -> "Фото"
        }
    }

    override fun getCount(): Int {
        return fragments.size
    }
}