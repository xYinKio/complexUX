package com.example.complexux.recycler_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding


inline fun <T> multipleTypeRecyclerAdapter(
    crossinline onBind: ViewBinding.(T, MultipleTypeViewHolder<T>) -> Unit,
    crossinline areItemsTheSame: (T, T) -> Boolean = { item1, item2 -> item1 == item2},
    crossinline areContentsTheSame: (T, T) -> Boolean = { item1, item2 -> item1 == item2},
    crossinline onCreateViewHolder: (ViewGroup, Int) -> MultipleTypeViewHolder<T>,
    crossinline itemViewType: ListAdapter<T, MultipleTypeViewHolder<T>>.(position: Int) -> Int
) = object : ListAdapter<T, MultipleTypeViewHolder<T>>(
    itemCallback<T>(areItemsTheSame, areContentsTheSame)
){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MultipleTypeViewHolder<T> {
        return onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: MultipleTypeViewHolder<T>, position: Int) {
        holder.bind(getItem(position), onBind)
    }

    override fun getItemViewType(position: Int): Int {
        return itemViewType(position)
    }
}

class MultipleTypeViewHolder<T> constructor(
    val binding: ViewBinding
)  : RecyclerView.ViewHolder(binding.root){

    inline fun bind(item: T, crossinline bind: ViewBinding.(T, MultipleTypeViewHolder<T>) -> Unit){
        binding.bind(item, this)
    }

    companion object {
        fun <T, VB : ViewBinding>from(
            vbClass: Class<VB>,
            parent: ViewGroup
        ) : MultipleTypeViewHolder<T> {

            val method = vbClass.getMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java)
            val inflater = LayoutInflater.from(parent.context)
            val binding = method.invoke(vbClass, inflater, parent, false) as VB
            return MultipleTypeViewHolder(binding)
        }
    }
}
