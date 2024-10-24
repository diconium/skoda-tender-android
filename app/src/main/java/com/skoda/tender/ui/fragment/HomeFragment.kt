package com.skoda.tender.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.skoda.tender.R
import com.skoda.tender.core.BaseFragment
import com.skoda.tender.data.source.response.ApiResult
import com.skoda.tender.data.source.response.SubscriptionStatus
import com.skoda.tender.data.source.response.Subscriptions
import com.skoda.tender.databinding.FragmentHomeBinding
import com.skoda.tender.ui.adapter.ServiceListAdapter
import com.skoda.tender.ui.viewmodel.ServiceViewModel
import java.util.Objects.isNull


/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : BaseFragment<ServiceViewModel, FragmentHomeBinding >(ServiceViewModel::class.java) {
    override fun getLayoutRes(): Int {

        return R.layout.fragment_home
    }

    /**
     * Adapter for displaying the list of subscriptions.
     */
    private lateinit var serviceListAdapter: ServiceListAdapter

    /**
     * Tag for logging purposes.
     */
    private val TAG: String = HomeFragment::class.java.simpleName

    /**
     * List of all subscriptions retrieved from the API.
     */
    private var mAllSubscriptions: ArrayList<Subscriptions>? = null

    /**
     * Boolean indicating whether to filter for active subscriptions.
     */
    private var mFilterActive: Boolean = false



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        storiesDataObserver()
        viewModel.getSubscriptionsData()
        setAdapter(arrayListOf())


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
                            applyFilter(mFilterActive)
                        }
                    }
                    // TODO: Hide progress bar
                }

                is ApiResult.Error -> {
                    Log.i(TAG, "storiesDataObserver: Error "+response)
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
                    requireActivity().supportFragmentManager.beginTransaction()
                        .add(R.id.fragment_container, PaymentServiceScreen()).commit()
                }
            })
        mBinding.rvPaymentServices.adapter = serviceListAdapter
    }

}