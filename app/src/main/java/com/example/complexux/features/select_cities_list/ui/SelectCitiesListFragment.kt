package com.example.complexux.features.select_cities_list.ui

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.complexux.databinding.CityItemBinding
import com.example.complexux.databinding.FragmentSelectCitiesListBinding
import com.example.complexux.databinding.ItemAddCitiesListBinding
import com.example.complexux.databinding.ItemCitiesListBinding
import com.example.complexux.recycler_adapter.MultipleTypeViewHolder
import com.example.complexux.recycler_adapter.multipleTypeRecyclerAdapter
import com.example.complexux.recycler_adapter.singleTypeRecyclerAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.N)
class SelectCitiesListFragment(
    private val onCancel: () -> Unit,
    private val onBack: Fragment.() -> Unit,
    private val onAddClick: Fragment.() -> Unit
) : BottomSheetDialogFragment() {

    private val binding: FragmentSelectCitiesListBinding by viewBinding(CreateMethod.INFLATE)

    private val citiesAdapters = mutableMapOf<String, ListAdapter<City, *>>()
    private val citiesListAdapter by lazy { citiesListAdapter() }

    private val viewModel: SelectCitiesListViewModel by viewModels()

    private val snapHelper = PagerSnapHelper()

    @SuppressLint("ClickableViewAccessibility")
    private fun citiesListAdapter() = multipleTypeRecyclerAdapter<ListItem>(
        onBind = { item, holder ->
            when(this){
                is ItemCitiesListBinding -> {showList((item as ListItem.Data).citiesList, holder)}
                is ItemAddCitiesListBinding -> { root.setOnClickListener { onAddClick() } }
            }
        },
        itemViewType = { position ->
            when(currentList[position]){
                is ListItem.Data -> 0
                is ListItem.Add -> 1
            }
        },
        onCreateViewHolder = { parent, viewType ->
            when(viewType){
                0 -> MultipleTypeViewHolder.from(ItemCitiesListBinding::class.java, parent)
                1 -> MultipleTypeViewHolder.from(ItemAddCitiesListBinding::class.java, parent)
                else -> {throw Exception("Unknown view type $viewType")}
            }
        }
    )

    private fun ItemCitiesListBinding.showList(
        citiesList: CitiesList,
        holder: RecyclerView.ViewHolder
    ) {
        name.text = citiesList.name
        if (citiesAdapters[citiesList.name] == null) {
            citiesAdapters[citiesList.name] = singleTypeRecyclerAdapter<City, CityItemBinding>(
                onBind = { city, _ ->
                    name.text = city.name
                    date.text = city.date
                    root.setOnLongClickListener {
                        viewModel.obtainIntention(Intention.StartDragAndDrop(holder.absoluteAdapterPosition))
                        true
                    }
                }
            )
        }
        val adapter = citiesAdapters[citiesList.name]!!
        recycler.adapter = adapter
        recycler.layoutManager = object : LinearLayoutManager(requireContext()){
            override fun canScrollVertically(): Boolean { return false }
        }
        name.setOnLongClickListener {
            viewModel.obtainIntention(Intention.StartDragAndDrop(holder.absoluteAdapterPosition))
            true
        }

        adapter.submitList(citiesList.cities)
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

        (dialog as? BottomSheetDialog)?.behavior?.apply {
            isFitToContents = false
            state = BottomSheetBehavior.STATE_HALF_EXPANDED
        }

        binding.recycler.adapter = citiesListAdapter
        snapHelper.attachToRecyclerView(binding.recycler)
        binding.recycler.addOnScrollListener(
            object : RecyclerView.OnScrollListener(){
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    val centerView = snapHelper.findSnapView(recyclerView.layoutManager)
                    if (newState == RecyclerView.SCROLL_STATE_IDLE){
                        val position = recyclerView.layoutManager!!.getPosition(centerView!!)
                        viewModel.obtainIntention(Intention.SelectList(position))
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

        updateView(viewModel.flow.value)

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.flow.collect{
                when(it){
                    is Event.ListSelected -> { binding.fullName.text = it.state.currentListFullName }
                    is Event.Updated -> { updateView(it) }
                    is Event.DragAndDropStarted -> {
                        startDragAndDrop(
                            view = snapHelper.findSnapView(binding.recycler.layoutManager)!!,
                            citiesListName = it.state.currentListName
                        )
                    }
                }
            }
        }
    }

    private fun updateView(it: Event) {
        lifecycleScope.launch {
            val items = it.state.citiesLists
                .map { ListItem.Data(it) }
                .toMutableList<ListItem>()
                .apply { add(ListItem.Add) }

            citiesListAdapter.submitList(items)
        }

        binding.fullName.text = it.state.currentListFullName
    }

    private fun startDragAndDrop(view: View, citiesListName: String): Boolean {
        val clipData = ClipData.newPlainText("Name", citiesListName)
        val shadowBuilder = View.DragShadowBuilder(view)

        val flags = View.DRAG_FLAG_GLOBAL
        view.startDragAndDrop(clipData, shadowBuilder, null, flags)
        onCancel()
        onBack()
        return true
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        onCancel()
    }

}