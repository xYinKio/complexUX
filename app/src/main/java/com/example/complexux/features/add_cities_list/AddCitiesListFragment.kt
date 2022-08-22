package com.example.complexux.features.add_cities_list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.complexux.R
import com.example.complexux.databinding.FragmentAddCitiesListBinding
import com.skydoves.colorpickerview.ColorPickerDialog

class AddCitiesListFragment : Fragment(R.layout.fragment_add_cities_list) {

    private val binding: FragmentAddCitiesListBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.color.setOnClickListener {
            ColorPickerDialog.Builder(requireContext())
                .setTitle("Select color")
                .setPreferenceName("Color")
                .setPositiveButton("ok"){envelope, fromUser ->

                }
                .setNegativeButton("cancel"){envelope, fromUser ->}
                .attachBrightnessSlideBar(true)
                .attachAlphaSlideBar(false)
                .show()

        }
    }
}