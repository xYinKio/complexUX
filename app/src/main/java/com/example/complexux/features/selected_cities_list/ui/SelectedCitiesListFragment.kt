package com.example.complexux.features.selected_cities_list.ui

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.DragEvent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.complexux.R
import com.example.complexux.databinding.CityItemBinding
import com.example.complexux.databinding.FragmentCitiesListBinding
import com.example.complexux.recycler_adapter.singleTypeRecyclerAdapter

class SelectedCitiesListFragment() : Fragment(R.layout.fragment_cities_list) {

    private val binding: FragmentCitiesListBinding by viewBinding()
    private val itemTouchHelper: ItemTouchHelper by lazy { itemTouchHelper() }

    private val viewModel: SelectedCitiesListViewModel by viewModels()

    private val adapter by lazy { singleTypeRecyclerAdapter<City, CityItemBinding>(
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        itemTouchHelper.attachToRecyclerView(binding.recycler)
        binding.recycler.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.flow.collect{ event ->
                when(event){
                    Event.Init -> {}
                    is Event.Updated -> {
                        adapter.submitList(event.state.cities)
                        onUpdatedMap.forEach{it.value.invoke(event.state.name, event.state.color)}
                    }
                }

            }
        }


        binding.root.setOnDragListener { v, event ->
            when(event.action){
                DragEvent.ACTION_DROP -> {
                    viewModel.selectCity(event.clipData.getItemAt(0).text.toString())
                }
            }
            true
        }
    }


    private fun itemTouchHelper() = ItemTouchHelper(object : ItemTouchHelper.Callback() {
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
            if (startPosition < endPosition) {
                for (i in startPosition until endPosition) {
                    viewModel.swap(i, i + 1)
                }
            } else {
                for (i in startPosition downTo endPosition + 1) {
                    viewModel.swap(i, i - 1)
                }
            }
            adapter.notifyItemMoved(startPosition, endPosition)
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}
    })

    companion object{
        private val onUpdatedMap = mutableMapOf<String, (String, Int) -> Unit>()

        fun addOnUpdated(key: String, onUpdated: (String, color: Int) -> Unit){
            onUpdatedMap[key] = onUpdated
        }

        fun removeOnUpdated(key: String){
            onUpdatedMap.remove(key)
        }
    }
}