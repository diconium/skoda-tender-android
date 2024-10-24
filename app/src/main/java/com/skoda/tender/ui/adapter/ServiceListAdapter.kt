package com.skoda.tender.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.skoda.tender.R
import com.skoda.tender.core.BaseListAdapter
import com.skoda.tender.data.source.response.SubscriptionStatus
import com.skoda.tender.data.source.response.Subscriptions
import com.skoda.tender.databinding.ServiceListItemsBinding
import com.skoda.tender.utils.extensions.DateUtils
import com.skoda.tender.utils.extensions.getColorCompat


/**
 * Adapter for displaying a list of subscription services in a RecyclerView.
 *
 * This adapter binds subscription data to the views and handles click events
 * for each item in the list.
 *
 * @param dataList The list of subscriptions to display.
 * @param serviceClickListener The listener for handling item click events.
 */
class ServiceListAdapter(
    private val dataList: List<Subscriptions>,
    private val serviceClickListener: ServiceClickListener
) : BaseListAdapter<Subscriptions>(dataList) {

    /**
     * Functional interface for handling click events on service items.
     */
    @FunctionalInterface
    interface ServiceClickListener {
        /**
         * Called when a service item is clicked.
         *
         * @param subscriptions The clicked subscription item.
         */
        fun onClickItem(subscriptions: Subscriptions)
    }

    /**
     * Creates a new binding for a service list item.
     *
     * @param parent The parent view group to which the new view will be attached.
     * @param viewType The view type of the new view.
     * @return A [ViewDataBinding] instance for the service list item.
     */
    override fun createBinding(parent: ViewGroup, viewType: Int): ViewDataBinding {
        return ServiceListItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    /**
     * Binds data to the provided binding for the specified position in the list.
     *
     * @param binding The binding for the item view.
     * @param position The position of the item in the list.
     */
    override fun bind(binding: ViewDataBinding, position: Int) {
        if ((binding is ServiceListItemsBinding)) {
            val context = binding.root.context
            val itemBinding: ServiceListItemsBinding = binding
            val subscription = list[position]

            itemBinding.typeText.text = subscription.name
            itemBinding.serviceItemLy.setOnClickListener {
                serviceClickListener.onClickItem(subscription)
            }


            when (subscription.status) {
                SubscriptionStatus.ACTIVATED -> {
                    itemBinding.expireText.text = "Active"
                    itemBinding.ivRedDot.setImageDrawable(context.getDrawable(R.drawable.active_status))

                    //itemBinding.expireText.setBackgroundColor(context.getColorCompat(R.color.md_theme_light_primary))
                    itemBinding.expDateText.text =
                        subscription.startDate?.let { DateUtils.subDateFormater(it, false, context) }
                }

                SubscriptionStatus.INACTIVE -> {
                    itemBinding.expireText.text = "Expired"
                    itemBinding.ivRedDot.setImageDrawable(context.getDrawable(R.drawable.circle_red))
                    //itemBinding.expireText.setBackgroundColor(context.getColorCompat(R.color.white))
                    itemBinding.expDateText.text =
                        subscription.endDate?.let { DateUtils.subDateFormater(it, true, context) }
                }
            }
        }
    }
}
