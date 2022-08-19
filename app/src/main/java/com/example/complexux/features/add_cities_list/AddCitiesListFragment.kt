package com.example.complexux.features.add_cities_list

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.complexux.databinding.FragmentAddCitiesListBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddCitiesListFragment(
    private val onCancel: () -> Unit
) : BottomSheetDialogFragment() {

    private val binding: FragmentAddCitiesListBinding by viewBinding(CreateMethod.INFLATE)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        onCancel()
    }
}