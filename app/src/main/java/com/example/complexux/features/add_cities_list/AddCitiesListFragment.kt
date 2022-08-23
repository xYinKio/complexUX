package com.example.complexux.features.add_cities_list

import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
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

            checkBox.isEnabled = when{
                item.isSelected -> true
                item.isSelected && isSelectEnabled() -> true
                !item.isSelected && isSelectEnabled() -> true
                !item.isSelected && !isSelectEnabled() -> false
                else -> false
            }


            checkBox.setOnClickListener {
                if (checkBox.isChecked){
                    viewModel.obtainIntention(Intention.SelectCity(item))
                } else {
                    viewModel.obtainIntention(Intention.UnselectCity(item))
                }
            }
        },
        areItemsTheSame = {old, new -> old.name == new.name},
        areContentsTheSame = { _, _ -> false}
    ) }

    private fun isSelectEnabled() : Boolean = viewModel.flow.value.state.isSelectEnabled

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            colorPicker.attachBrightnessSlider(binding.brightnessSlideBar)
            recycler.adapter = adapter
            recycler.itemAnimator = null

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
            complete.setOnClickListener { viewModel.obtainIntention(Intention.Complete) }


        }



        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.flow.collect{
                when(it){
                    is Event.ColorSelected -> binding.color.backgroundTintList = ColorStateList.valueOf(it.state.color)
                    is Event.Filtered -> { adapter.submitList(ArrayList(it.state.cities)) }
                    is Event.Updated -> { binding.updateAll(it.state) }
                    is Event.CitySelected,
                    is Event.CityUnselected -> { adapter.submitList(ArrayList(it.state.cities))
                        binding.complete.text = "Создать (${it.state.selectedCities.size}/5)"
                    }
                    is Event.ErrorNoSelected -> {
                        Toast.makeText(requireContext(), "Не выбрано городов", Toast.LENGTH_SHORT)
                            .show()
                    }
                    else -> {}
                }
            }
        }
    }

    private fun FragmentAddCitiesListBinding.updateAll(state: State) {
        colorPicker.setInitialColor(state.color)
        name.setText(state.name)
        fullName.setText(state.fullName)
        adapter.submitList(state.cities)
        binding.complete.text = "Создать (${state.selectedCities.size}/5)"
    }
}