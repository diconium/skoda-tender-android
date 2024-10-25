import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.skoda.tender.core.BaseListAdapter
import com.skoda.tender.data.source.response.IncludedServices
import com.skoda.tender.databinding.PayListItemsBinding

/**
 * Adapter for displaying a list of payment types in a RecyclerView.
 *
 * This adapter binds payment data to the views and manages the expansion
 * and collapse of items to show/hide additional information.
 *
 * @param dataList The list of payment types to display.
 * @param searchListener The listener for handling item click events.
 */
class PayListAdapter(
    private val dataList: List<IncludedServices>,
    private val searchListener: SearchListener
) : BaseListAdapter<IncludedServices>(dataList) {

    /**
     * Functional interface for handling click events on payment items.
     */
    @FunctionalInterface
    interface SearchListener {
        /**
         * Called when a payment item is clicked.
         *
         * @param position The position of the clicked item.
         */
        fun onClickItem(position: String)
    }

    // Keeps track of which item is expanded (showing the extra TextViews)
    private val expandedPositions = mutableSetOf<Int>()

    /**
     * Creates a new binding for a payment list item.
     *
     * @param parent The parent view group to which the new view will be attached.
     * @param viewType The view type of the new view.
     * @return A [ViewDataBinding] instance for the payment list item.
     */
    override fun createBinding(parent: ViewGroup, viewType: Int): ViewDataBinding {
        // Inflate the layout for payment list items and return the binding instance
        return PayListItemsBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    }

    /**
     * Binds data to the provided binding for the specified position in the list.
     *
     * @param binding The binding for the item view.
     * @param position The position of the item in the list.
     */
    override fun bind(binding: ViewDataBinding, position: Int) {
        if (binding is PayListItemsBinding) {
            // Get the current item from the data list
            val currentItem = dataList[position]

            // Set the name and description of the payment type
            binding.tvPayType.text = currentItem.name
            binding.tvPayTypeExt1.text = currentItem.description

            // Check if this position is expanded or collapsed
            val isExpanded = expandedPositions.contains(position)

            // Set visibility based on the expansion state
            binding.tvPayTypeExt1.visibility = if (isExpanded) View.VISIBLE else View.GONE

            // Handle the click event for the item
            binding.root.setOnClickListener {
                if (isExpanded) {
                    // Collapse the item (remove from expanded positions)
                    expandedPositions.remove(position)
                } else {
                    // Expand the item (add to expanded positions)
                    expandedPositions.add(position)
                }
                // Notify the adapter to refresh this item
                notifyItemChanged(position)
            }
        }
    }
}
