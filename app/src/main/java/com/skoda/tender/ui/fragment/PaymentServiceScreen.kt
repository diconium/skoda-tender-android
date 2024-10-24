package com.skoda.tender.ui.fragment

import PayListAdapter
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.skoda.tender.R
import com.skoda.tender.core.BaseFragment
import com.skoda.tender.data.source.response.ApiResult
import com.skoda.tender.data.source.response.IncludedServices
import com.skoda.tender.data.source.response.SubscriptionStatus
import com.skoda.tender.data.source.response.Subscriptions
import com.skoda.tender.databinding.FragmentPaymentServiceScreenBinding
import com.skoda.tender.ui.adapter.ServiceListAdapter
import com.skoda.tender.ui.main.MainActivityViewModel
import com.skoda.tender.ui.viewmodel.ServiceViewModel
import com.skoda.tender.utils.extensions.DateUtils
import java.util.Objects.isNull

class PaymentServiceScreen : BaseFragment<ServiceViewModel, FragmentPaymentServiceScreenBinding>(
    ServiceViewModel::class.java
) {
    override fun getLayoutRes(): Int {
        return R.layout.fragment_payment_service_screen
    }

    var isFragmentActive = false

    /**
     * Adapter for displaying the list of subscriptions.
     */
    private lateinit var serviceListAdapter: ServiceListAdapter

    /**
     * Tag for logging purposes.
     */
    private val TAG: String = PaymentServiceScreen::class.java.simpleName

    /**
     * List of all subscriptions retrieved from the API.
     */
    private var mAllSubscriptions: ArrayList<Subscriptions>? = null

    /**
     * Boolean indicating whether to filter for active subscriptions.
     */
    private var mFilterActive: Boolean = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isFragmentActive = true
        Log.d("screen", "onViewCreated")

        // Scroll listener to update title based on the current visible item
        mBinding.rvPaymentServices.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                // Check if scrolling is idle
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                    if (firstVisibleItemPosition != RecyclerView.NO_POSITION) {
                        val currentItem = mAllSubscriptions?.get(firstVisibleItemPosition)!!
                        updateSubscriptionDetails(currentItem)  // Call the function here
                    }
                }
            }
        })

        val snapHelper: SnapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(mBinding.payListRecycler)

        storiesDataObserver()
        viewModel.getSubscriptionsData()
        setAdapter(arrayListOf())
    }

    /**
     * Updates the UI with the details of the current subscription.
     *
     * @param currentItem The current subscription to display.
     */
    private fun updateSubscriptionDetails(currentItem: Subscriptions) {
        mBinding.tvServiceTitle.text = currentItem.name // Assuming name is a String property

        when (currentItem.status) {
            SubscriptionStatus.ACTIVATED -> {
                mBinding.ivRedDot.setImageDrawable(context?.getDrawable(R.drawable.active_status))
                mBinding.tvServiceStatus.text = "Active"
                mBinding.tvServiceExpiry.text = currentItem.startDate?.let {
                    DateUtils.subDateFormater(it, false, requireContext())
                }
            }
            SubscriptionStatus.INACTIVE -> {
                mBinding.ivRedDot.setImageDrawable(context?.getDrawable(R.drawable.circle_red))
                mBinding.tvServiceStatus.text = "Expired"
                mBinding.tvServiceExpiry.text = currentItem.startDate?.let {
                    DateUtils.subDateFormater(it, true, requireContext())
                }
            }
        }

        mBinding.rvPaymentServices.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        val playlistAdapter =
            PayListAdapter(currentItem.includedServices, object : PayListAdapter.SearchListener {
                override fun onClickItem(position: String) {
                }
            })
        mBinding.rvPaymentServices.adapter = playlistAdapter
    }

    /**
     * Applies the active filter to the list of subscriptions.
     *
     * @param isActive Boolean indicating whether to filter for active subscriptions.
     */
    private fun applyFilter(isActive: Boolean) {
        if (mAllSubscriptions == null) {
            Log.i(TAG, "filter: subscription data not available")
            return
        }
        if (isActive) {
            val filterData =
                mAllSubscriptions!!.filter { it.status == SubscriptionStatus.ACTIVATED }
            serviceListAdapter.setList(filterData)
        } else {
            serviceListAdapter.setList(mAllSubscriptions!!)
        }
    }

    private fun storiesDataObserver() {
        viewModel.subscriptionsLiveData.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ApiResult.Loading -> {
                    Log.i(TAG, "storiesDataObserver: loading ")
                    // TODO: Show progress bar
                }
                is ApiResult.Success -> {
                    Log.i(TAG, "storiesDataObserver: Success ")
                    if (!isNull(response.data)) {
                        response.data?.let {
                            mAllSubscriptions = it.subscriptions
                            val currentItem = mAllSubscriptions?.get(0)!!
                            updateSubscriptionDetails(currentItem)  // Call the function here
                            serviceListAdapter.setList(mAllSubscriptions!!)
                        }
                    }
                    // TODO: Hide progress bar
                }
                is ApiResult.Error -> {
                    Log.i(TAG, "storiesDataObserver: Error ")
                    // TODO: Hide progress bar
                }
                is ApiResult.Nothing -> {
                    Log.i(TAG, "storiesDataObserver: Nothing ")
                    // TODO: Hide progress bar
                }
            }
        }
    }

    private fun setAdapter(subscriptions: ArrayList<Subscriptions>) {
        mBinding.rvPaymentServices.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        serviceListAdapter =
            ServiceListAdapter(subscriptions, object : ServiceListAdapter.ServiceClickListener {
                override fun onClickItem(subscriptions: Subscriptions) {
                    if (!isFragmentActive) {
                        requireActivity().supportFragmentManager.beginTransaction()
                            .add(R.id.fragment_container, PaymentServiceScreen()).commit()
                    }
                }
            })
        mBinding.rvPaymentServices.adapter = serviceListAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isFragmentActive = false
    }
}
