package com.example.complexux.features.tabs

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.complexux.features.cities_list.CitiesListFragment

class PagesAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 1

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> CitiesListFragment()
            else -> throw IllegalStateException("Unbounded position: $position in ${this::class.java.simpleName}")
        }
    }
}