package com.example.complexux.features.add_cities_list

import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import android.widget.RadioGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.complexux.R
import com.example.complexux.databinding.FragmentAddCitiesListBinding
import com.example.complexux.databinding.ItemCityAddCitiesListBinding
import com.example.complexux.recycler_adapter.singleTypeRecyclerAdapter
import com.skydoves.colorpickerview.listeners.ColorListener

class AddCitiesListFragment : Fragment(R.layout.fragment_add_cities_list) {

    private val binding: FragmentAddCitiesListBinding by viewBinding()
    private val viewModel: AddCitiesListViewModel by viewModels()

    private val adapter by lazy { singleTypeRecyclerAdapter<City, ItemCityAddCitiesListBinding>(
        onBind = {item, _ ->
            name.text = item.name
            date.text = item.date
            checkBox.isChecked = item.isSelected
            checkBox.setOnClickListener {
                if (checkBox.isChecked){
                    viewModel.obtainIntention(Intention.SelectCity(item))
                } else {
                    viewModel.obtainIntention(Intention.UnselectCity(item))
                }
            }
        },
        areItemsTheSame = {old, new -> old.name == new.name}
    ) }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            colorPicker.attachBrightnessSlider(binding.brightnessSlideBar)
            recycler.adapter = adapter

            val state = viewModel.flow.value.state
            updateAll(state)


            colorPicker.colorListener =
                ColorListener { color, _ -> viewModel.obtainIntention(Intention.SelectColor(color)) }

            name.doOnTextChanged { text, _, _, _ -> viewModel.obtainIntention(Intention.TypeName(text.toString()))  }
            fullName.doOnTextChanged { text, _, _, _ -> viewModel.obtainIntention(Intention.TypeName(text.toString()))  }

            search.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String): Boolean = true

                override fun onQueryTextChange(newText: String): Boolean {
                    viewModel.obtainIntention(Intention.Filter(newText))
                    return true
                }
            })


        }



        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.flow.collect{
                when(it){
                    is Event.ColorSelected -> binding.color.backgroundTintList = ColorStateList.valueOf(it.state.color)
                    is Event.Filtered -> { adapter.submitList(it.state.cities) }
                    is Event.Updated -> { binding.updateAll(it.state) }
                    is Event.CitySelected,
                    is Event.CityUnselected -> { adapter.submitList(it.state.cities) }
                    else -> {}
                }
            }
        }
    }

    private fun FragmentAddCitiesListBinding.updateAll(state: Event.State) {
        colorPicker.setInitialColor(state.color)
        name.setText(state.name)
        fullName.setText(state.fullName)
        adapter.submitList(state.cities)
    }
}