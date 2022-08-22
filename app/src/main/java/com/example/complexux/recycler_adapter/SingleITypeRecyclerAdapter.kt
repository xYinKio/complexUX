package com.example.complexux.recycler_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding


inline fun <T, reified VB: ViewBinding> singleTypeRecyclerAdapter(
    crossinline onBind: VB.(T, SingleTypeViewHolder<T, VB>) -> Unit,
    crossinline areItemsTheSame: (T, T) -> Boolean = { item1, item2 -> item1 == item2},
    crossinline areContentsTheSame: (T, T) -> Boolean = { item1, item2 -> item1 == item2},
) = object : ListAdapter<T, SingleTypeViewHolder<T, VB>>(
    itemCallback<T>(areItemsTheSame, areContentsTheSame)
){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleTypeViewHolder<T, VB> {
        return SingleTypeViewHolder.from(VB::class.java, parent)
    }

    override fun onBindViewHolder(holder: SingleTypeViewHolder<T, VB>, position: Int) {
        holder.bind(getItem(position), onBind)
    }
}

inline fun <T> itemCallback(
    crossinline areItemsTheSame: (T, T) -> Boolean,
    crossinline areContentsTheSame: (T, T) -> Boolean
) = object : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T) = areItemsTheSame(oldItem, newItem)

    override fun areContentsTheSame(oldItem: T, newItem: T) =
        areContentsTheSame(oldItem, newItem)
}

class SingleTypeViewHolder<T, VB : ViewBinding> constructor(
    val viewBinding: VB
)  : RecyclerView.ViewHolder(viewBinding.root){

    inline fun bind(item: T, crossinline bind: VB.(T, SingleTypeViewHolder<T, VB>) -> Unit){
        viewBinding.bind(item, this)
    }

    companion object {
        fun <T, VB : ViewBinding>from(
            vbClass: Class<VB>,
            parent: ViewGroup
        ) : SingleTypeViewHolder<T, VB> {

            val method = vbClass.getMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java)
            val inflater = LayoutInflater.from(parent.context)
            val binding = method.invoke(vbClass, inflater, parent, false) as VB
            return SingleTypeViewHolder(binding)
        }
    }
}