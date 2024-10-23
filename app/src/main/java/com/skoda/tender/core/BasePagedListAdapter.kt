package com.skoda.tender.core

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.skoda.tender.R

abstract class BasePagedListAdapter(diffCallback: DiffUtil.ItemCallback<BaseEntity> = BaseDiffCallback()) : PagingDataAdapter<BaseEntity, RecyclerView.ViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = getViewHolder(parent, viewType)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as BaseViewHolder<*>).binding.root.setTag(R.string.position, position)
        bind(holder.binding, position)
    }

    open fun getViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = BaseViewHolder(createBinding(parent, viewType))

    abstract fun createBinding(parent: ViewGroup, viewType: Int): ViewDataBinding

    protected abstract fun bind(binding: ViewDataBinding, position: Int)

    suspend fun setList(list: PagingData<BaseEntity>) {
        submitData(list)
    }

    companion object {
        /**
         * This diff callback informs the PagedListAdapter how to compute list differences when new
         * PagedLists arrive.
         * <p>
         * When you add a Cheese with the 'Add' button, the PagedListAdapter uses diffCallback to
         * detect there's only a single item difference from before, so it only needs to animate and
         * rebind a single view.
         *
         * @see android.support.v7.util.DiffUtil
         */
        open class BaseDiffCallback : DiffUtil.ItemCallback<BaseEntity>() {
            override fun areItemsTheSame(oldItem: BaseEntity, newItem: BaseEntity) = oldItem == newItem
            /**
             * Note that in kotlin, == checking on data classes compares all contents, but in Java,
             * typically you'll implement Object#equals, and use it to compare object contents.
             */
            override fun areContentsTheSame(oldItem: BaseEntity, newItem: BaseEntity) = oldItem == newItem
        }
    }
}