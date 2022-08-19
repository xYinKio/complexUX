package com.example.complexux.features.cities_list

import android.os.Bundle
import android.util.Log
import android.view.DragEvent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.complexux.R
import com.example.complexux.databinding.CityItemBinding
import com.example.complexux.databinding.FragmentCitiesListBinding
import com.example.complexux.recycler_adapter.recyclerAdapter
import java.util.*

class CitiesListFragment : Fragment(R.layout.fragment_cities_list) {

    private val binding: FragmentCitiesListBinding by viewBinding()
    private val itemTouchHelper: ItemTouchHelper by lazy {
        ItemTouchHelper(object : ItemTouchHelper.Callback(){
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
                return makeMovementFlags(dragFlags, 0)
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val startPosition = viewHolder.absoluteAdapterPosition
                val endPosition = target.absoluteAdapterPosition
                if (startPosition < endPosition){
                    for (i in startPosition until endPosition) {
                        Collections.swap(cities, i, i + 1)
                    }
                } else {
                    for (i in startPosition downTo endPosition + 1) {
                        Collections.swap(cities, i, i - 1)
                    }
                }
                adapter.notifyItemMoved(startPosition, endPosition)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}
        })
    }


    private val adapter by lazy { recyclerAdapter<City, CityItemBinding>(
        onBind = {item, holder ->
            name.text = item.name
            date.text = item.date
            holder.itemView.setOnTouchListener{view, _ ->
                view.performClick()
                itemTouchHelper.startDrag(holder)
                false
            }

        }
    ) }

    private val cities = listOf(
        City("Saint-Petersburg", "1703 г"),
        City("Moscow", "1147 г"),
        City("Chelyabinsk", "1736 г"),
        City("Volgograd", "1589г"),
        City("Paris", "53 г до н. э.")
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        itemTouchHelper.attachToRecyclerView(binding.recycler)
        binding.recycler.adapter = adapter
        adapter.submitList(cities)

        binding.root.setOnDragListener { v, event ->
            when(event.action){
                DragEvent.ACTION_DRAG_STARTED -> {
                    Log.d("!!!", "ACTION_DRAG_STARTED")
                }
                DragEvent.ACTION_DRAG_LOCATION -> {
                    Log.d("!!!", "ACTION_DRAG_LOCATION")
                }
                DragEvent.ACTION_DRAG_ENTERED -> {
                    Log.d("!!!", "ACTION_DRAG_ENTERED")
                }
                DragEvent.ACTION_DRAG_ENDED -> {
                    Log.d("!!!", "ACTION_DRAG_ENDED")
                }
                DragEvent.ACTION_DROP -> {
                    Log.d("!!!", "ACTION_DROP")
                }
                DragEvent.ACTION_DRAG_EXITED -> {
                    Log.d("!!!", "ACTION_DRAG_EXITED")
                }
            }
            true
        }
    }

    override fun onResume() {
        super.onResume()
    }

}