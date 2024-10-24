package com.skoda.tender.ui.fragment

import PayListAdapter
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.skoda.launcher.ui.adapterimport.ServiceListAdapter
import com.skoda.tender.R
import com.skoda.tender.core.BaseFragment
import com.skoda.tender.databinding.FragmentPaymentServiceScreenBinding
import com.skoda.tender.ui.main.MainActivityViewModel

class PaymentServiceScreen : BaseFragment<MainActivityViewModel, FragmentPaymentServiceScreenBinding>(
    MainActivityViewModel::class.java
) {
    override fun getLayoutRes(): Int {
        return R.layout.fragment_payment_service_screen
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Assuming ServiceItem holds your service data
        val serviceList = arrayListOf<String>()
        serviceList.add("Ambient Lighting")
        serviceList.add("Infotainment Online")
        serviceList.add("Infotainment Offline")

        // Set up ServiceListAdapter
        mBinding.rvPaymentServices.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        val serviceListAdapter = ServiceListAdapter(serviceList, object : ServiceListAdapter.SearchListener {

            override fun onClickItem(position: String) {

            }


        })
        mBinding.rvPaymentServices.adapter = serviceListAdapter

        // Scroll listener to update title based on the current visible item
        mBinding.rvPaymentServices.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                // Check if scrolling is idle
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                    if (firstVisibleItemPosition != RecyclerView.NO_POSITION) {
                        val currentItem = serviceList[firstVisibleItemPosition]
                        // Update title_text in services_layout
                        mBinding.tvServiceTitle.text = currentItem // Assuming type is a String property
                    }
                }
            }
        })

        // Setup PayListAdapter
        val paymentList = arrayListOf<String>()
        paymentList.add("Pay to fuel")
        paymentList.add("Pay to Park")
        paymentList.add("Stay Yozu")

        mBinding.payListRecycler.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        mBinding.payListRecycler.adapter = PayListAdapter(paymentList, object : PayListAdapter.SearchListener {
            override fun onClickItem(position: String) {
                // Handle click for the pay list if needed
            }
        })

        val snapHelper: SnapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(mBinding.payListRecycler)
    }
}

// Assuming a data class for service items
data class ServiceItem(val type: String)
