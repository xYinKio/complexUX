package com.example.complexux.features.tabs

import android.annotation.SuppressLint
import android.content.ClipData
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.DragEvent
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.complexux.R
import com.example.complexux.databinding.FragmentTabsBinding
import com.example.complexux.features.select_cities_list.ui.SelectCitiesListFragment
import com.example.complexux.features.selected_cities_list.ui.SelectedCitiesListFragment
import com.google.android.material.tabs.TabLayout

class TabsFragment(
    private val onAddClick: Fragment.(TabLayout) -> Unit
) : Fragment(R.layout.fragment_tabs){

    private val viewModel: TabsViewModel by viewModels()
    private val binding: FragmentTabsBinding by viewBinding()


    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            pager.adapter = PagesAdapter(this@TabsFragment)

            tabsLayout.clearOnTabSelectedListeners()

            val tab1 = tabsLayout.newTab().apply {
                text = "Loading..."
            }
            val tab2 = tabsLayout.newTab().apply {
                text = "Select Citites List"
            }
            tabsLayout.addTab(tab1)
            tabsLayout.addTab(tab2)

            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                viewModel.state.collect{
                    when(it){
                        TabsState.FirstTab -> {
                            tabsLayout.selectTab(tab1)
                        }
                        TabsState.SecondTab -> {
                            tabsLayout.selectTab(tab2)
                        }
                    }
                }
            }

            tabsLayout.addOnTabSelectedListener(object  : TabLayout.OnTabSelectedListener{
                override fun onTabSelected(tab: TabLayout.Tab) {
                    if (tab.position == 1){
                        runCatching { onAddClick(tabsLayout) }
                        viewModel.selectSecondTab()
                    } else {
                        viewModel.selectFirstTab()
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {}

                override fun onTabReselected(tab: TabLayout.Tab?) {}
            })

        }


    }

    override fun onResume() {
        super.onResume()
        SelectedCitiesListFragment.addOnUpdated(this@TabsFragment::class.java.name){
            binding.tabsLayout.getTabAt(0)!!.text = it
        }
    }

    override fun onPause() {
        super.onPause()
        SelectedCitiesListFragment.removeOnUpdated(this@TabsFragment::class.java.name)
    }

}