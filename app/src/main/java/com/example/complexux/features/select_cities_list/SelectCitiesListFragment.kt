package com.example.complexux.features.select_cities_list

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.complexux.databinding.FragmentAddCitiesListBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SelectCitiesListFragment(
    private val onCancel: () -> Unit,
    private val onBack: Fragment.() -> Unit
) : BottomSheetDialogFragment() {

    private val binding: FragmentAddCitiesListBinding by viewBinding(CreateMethod.INFLATE)

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.mover.setOnTouchListener { v, event ->
            v.performClick()
            if (event.action == MotionEvent.ACTION_DOWN){


                val clipData = ClipData.newPlainText("","")
                val shadowBuilder = View.DragShadowBuilder(v)

                val flags = View.DRAG_FLAG_GLOBAL
                v.startDragAndDrop(clipData, shadowBuilder, null, flags)
                onCancel()
                onBack()
            }
            true

        }

        return binding.root
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        onCancel()
    }


}