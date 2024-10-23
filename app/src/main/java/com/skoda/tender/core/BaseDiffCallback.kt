package com.skoda.tender.core

import androidx.recyclerview.widget.DiffUtil

open class BaseDiffCallback<T> : DiffUtil.ItemCallback<T>() {
     override fun areItemsTheSame(oldItem: T & Any, newItem: T & Any): Boolean {
         return oldItem == newItem
     }

    override fun areContentsTheSame(oldItem: T & Any, newItem: T & Any): Boolean {
        return oldItem == newItem
    }
}