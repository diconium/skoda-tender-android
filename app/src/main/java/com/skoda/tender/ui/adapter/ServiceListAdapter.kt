package com.skoda.launcher.ui.adapterimport

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.skoda.tender.core.BaseListAdapter
import com.skoda.tender.databinding.ServiceListItemsBinding


class SearchAdapter(
    private val dataList: List<String>, private val searchListener: SearchListener
) : BaseListAdapter<String>(dataList) {
    @FunctionalInterface
    interface SearchListener {
        fun onClickItem(position: String)
    }

    override fun createBinding(parent: ViewGroup, viewType: Int): ViewDataBinding {
        return ServiceListItemsBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    }

    override fun bind(binding: ViewDataBinding, position: Int) {
        if (binding is ServiceListItemsBinding) {
            val i: ServiceListItemsBinding = binding
            val currentItem = dataList[position]
            i.typeText.text = currentItem
            i.root.setOnClickListener {
                searchListener.onClickItem(currentItem)
            }
        }
    }
}