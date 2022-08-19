package com.example.complexux.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.navigation.fragment.findNavController
import com.example.complexux.R
import com.example.complexux.features.add_cities_list.AddCitiesListFragment
import com.example.complexux.features.cities_list.CitiesListFragment
import com.example.complexux.features.tabs.TabsFragment

class NavigationFragmentFactory : FragmentFactory() {

    private var onCancelAdd: () -> Unit = {}

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            TabsFragment::class.java.name -> TabsFragment{ tabLayout ->
                onCancelAdd = { tabLayout.getTabAt(0)!!.select() }
                findNavController().navigate(R.id.action_tabsFragment_to_addCitiesListFragment)
            }
            AddCitiesListFragment::class.java.name -> AddCitiesListFragment{
                onCancelAdd()
                onCancelAdd = {}
            }
            CitiesListFragment::class.java.name -> {CitiesListFragment()}
            else -> super.instantiate(classLoader, className)
        }
    }
}