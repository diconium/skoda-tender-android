package com.skoda.tender.core

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.skoda.tender.R

abstract class BaseListAdapter<T>(@JvmField var list: List<T>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        getViewHolder(parent, viewType)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as BaseViewHolder<*>).binding.root.setTag(R.string.position, position)
        bind(holder.binding, position)
    }

    open fun getViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        BaseViewHolder(createBinding(parent, viewType))

    abstract fun createBinding(parent: ViewGroup, viewType: Int): ViewDataBinding

    protected abstract fun bind(binding: ViewDataBinding, position: Int)

    fun setList(list: List<T>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return this.list.size
    }
}