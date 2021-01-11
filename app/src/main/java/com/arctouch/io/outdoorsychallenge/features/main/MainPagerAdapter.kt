package com.arctouch.io.outdoorsychallenge.features.main

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.arctouch.io.outdoorsychallenge.features.favorites.FavoritesFragment
import com.arctouch.io.outdoorsychallenge.features.qrcoderesult.QrCodeResultFragment
import com.arctouch.io.outdoorsychallenge.features.searchrv.SearchRvFragment

class MainPagerAdapter(
    fragmentManager: FragmentManager,
    private val titles: List<String>
) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getCount() = 3

    override fun getItem(position: Int) =
        when (position) {
            0 -> SearchRvFragment.newInstance()
            1 -> FavoritesFragment.newInstance()
            2 -> QrCodeResultFragment.newInstance()
            else -> throw IllegalArgumentException()
        }

    override fun getPageTitle(position: Int): CharSequence =
        titles.getOrNull(position) ?: throw IllegalArgumentException()
}
