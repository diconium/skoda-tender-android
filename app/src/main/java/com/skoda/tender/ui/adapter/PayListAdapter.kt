import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.skoda.tender.core.BaseListAdapter
import com.skoda.tender.data.source.response.IncludedServices
import com.skoda.tender.databinding.PayListItemsBinding

class PayListAdapter(
    private val dataList: List<IncludedServices>,
    private val searchListener: SearchListener
) : BaseListAdapter<IncludedServices>(dataList) {

    @FunctionalInterface
    interface SearchListener {
        fun onClickItem(position: String)
    }

    // Keeps track of which item is expanded (showing the extra TextViews)
    private val expandedPositions = mutableSetOf<Int>()

    override fun createBinding(parent: ViewGroup, viewType: Int): ViewDataBinding {
        return PayListItemsBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    }

    override fun bind(binding: ViewDataBinding, position: Int) {
        if (binding is PayListItemsBinding) {
            val currentItem = dataList[position]
            binding.tvPayType.text = currentItem.name
            binding.tvPayTypeExt1.text = currentItem.description

            // Check if this position is expanded or collapsed
            val isExpanded = expandedPositions.contains(position)

            // Set visibility based on expansion state
            binding.tvPayTypeExt1.visibility = if (isExpanded) View.VISIBLE else View.GONE

            // Handle dropdown button click
            binding.root.setOnClickListener {
                if (isExpanded) {
                    // Collapse the item
                    expandedPositions.remove(position)
                } else {
                    // Expand the item
                    expandedPositions.add(position)
                }
                // Notify adapter to refresh this item
                notifyItemChanged(position)
            }

        }
    }
}
