package com.example.complexux.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.navigation.fragment.findNavController
import com.example.complexux.R
import com.example.complexux.features.selected_cities_list.SelectedCitiesListFragment
import com.example.complexux.features.select_cities_list.CitiesList
import com.example.complexux.features.select_cities_list.SelectCitiesListFragment
import com.example.complexux.features.tabs.TabsFragment

class NavigationFragmentFactory : FragmentFactory() {

    private var onCancelAdd: () -> Unit = {}
    private var citiesList: CitiesList? = null

    @RequiresApi(Build.VERSION_CODES.N)
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            TabsFragment::class.java.name -> TabsFragment{ tabLayout ->
                onCancelAdd = { tabLayout.getTabAt(0)!!.select() }
                findNavController().navigate(R.id.action_tabsFragment_to_addCitiesListFragment)
            }
            SelectCitiesListFragment::class.java.name -> SelectCitiesListFragment(
                onCancel = {
                    onCancelAdd()
                    onCancelAdd = {}
                },
                onBack = {
                    findNavController().navigateUp()
                }
            )
            SelectedCitiesListFragment::class.java.name -> SelectedCitiesListFragment()
            else -> super.instantiate(classLoader, className)
        }
    }
}