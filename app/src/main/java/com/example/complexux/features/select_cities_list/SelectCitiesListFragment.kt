package com.example.complexux.features.select_cities_list

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.*
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.complexux.databinding.CityItemBinding
import com.example.complexux.databinding.FragmentSelectCitiesListBinding
import com.example.complexux.databinding.ItemCitiesListBinding
import com.example.complexux.recycler_adapter.recyclerAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

@RequiresApi(Build.VERSION_CODES.N)
class SelectCitiesListFragment(
    private val onCancel: () -> Unit,
    private val onBack: Fragment.() -> Unit
) : BottomSheetDialogFragment() {

    private val binding: FragmentSelectCitiesListBinding by viewBinding(CreateMethod.INFLATE)

    private val citiesAdapters = mutableMapOf<String, ListAdapter<City, *>>()
    private val citiesListAdapter by lazy { citiesListAdapter() }

    @SuppressLint("ClickableViewAccessibility")
    private fun citiesListAdapter() = recyclerAdapter<CitiesList, ItemCitiesListBinding>(
        onBind = { citiesList, _ ->
            val superRoot = root
            name.text = citiesList.name
            if (citiesAdapters[citiesList.name] == null) {
                citiesAdapters[citiesList.name] = recyclerAdapter<City, CityItemBinding>(
                    onBind = { city, _ ->
                        name.text = city.name
                        date.text = city.date
                        root.setOnLongClickListener { startDragAndDrop(superRoot) }
                    }
                )
            }
            val adapter = citiesAdapters[citiesList.name]!!
            recycler.adapter = adapter
            name.setOnLongClickListener { startDragAndDrop(root) }

            adapter.submitList(citiesList.cities)
        }
    )

    private fun startDragAndDrop(it: View): Boolean {
        val clipData = ClipData.newPlainText("", "")
        val shadowBuilder = View.DragShadowBuilder(it)

        val flags = View.DRAG_FLAG_GLOBAL
        it.startDragAndDrop(clipData, shadowBuilder, null, flags)
        onCancel()
        onBack()
        return true
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.recycler.adapter = citiesListAdapter
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.recycler)
        binding.recycler.addOnScrollListener(
            object : RecyclerView.OnScrollListener(){
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    val centerView = snapHelper.findSnapView(recyclerView.layoutManager)
                    if (newState == RecyclerView.SCROLL_STATE_IDLE){
                        val position = recyclerView.layoutManager!!.getPosition(centerView!!)
                        val fullName = citiesListAdapter.currentList[position].fullName
                        binding.fullName.text = fullName
                    }

                    super.onScrollStateChanged(recyclerView, newState)
                }
            }
        )
        binding.recycler.apply {
            set3DItem(true)
            setAlpha(true)
            setInfinite(true)
        }
        citiesListAdapter.submitList(listOf(
            CitiesList(
                name = "Europe 1",
                fullName = "Some full name 1",
                cities = listOf(
                    City(
                        name = "City 1",
                        date = "date 1"
                    ),
                    City(
                        name = "City 2",
                        date = "date 2"
                    ),
                    City(
                        name = "City 3",
                        date = "date 3"
                    ),
                    City(
                        name = "City 4",
                        date = "date 4"
                    ),
                    City(
                        name = "City 5",
                        date = "date 5"
                    ),
                ),
                color = "#FF121212"
            ),
            CitiesList(
                name = "Europe 2",
                fullName = "Some full name 2",
                cities = listOf(
                    City(
                        name = "City 1",
                        date = "date 1"
                    ),
                    City(
                        name = "City 2",
                        date = "date 2"
                    ),
                    City(
                        name = "City 3",
                        date = "date 3"
                    ),
                    City(
                        name = "City 4",
                        date = "date 4"
                    ),
                    City(
                        name = "City 5",
                        date = "date 5"
                    ),
                ),
                color = "#FF121212"
            ),
            CitiesList(
                name = "Europe 3",
                fullName = "Some full name 3",
                cities = listOf(
                    City(
                        name = "City 1",
                        date = "date 1"
                    ),
                    City(
                        name = "City 2",
                        date = "date 2"
                    ),
                    City(
                        name = "City 3",
                        date = "date 3"
                    ),
                    City(
                        name = "City 4",
                        date = "date 4"
                    ),
                    City(
                        name = "City 5",
                        date = "date 5"
                    ),
                ),
                color = "#FF121212"
            ),
            CitiesList(
                name = "Europe 4",
                fullName = "Some full name 4",
                cities = listOf(
                    City(
                        name = "City 1",
                        date = "date 1"
                    ),
                    City(
                        name = "City 2",
                        date = "date 2"
                    ),
                    City(
                        name = "City 3",
                        date = "date 3"
                    ),
                    City(
                        name = "City 4",
                        date = "date 4"
                    ),
                    City(
                        name = "City 5",
                        date = "date 5"
                    ),
                ),
                color = "#FF121212"
            ),
        ))
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        onCancel()
    }




}