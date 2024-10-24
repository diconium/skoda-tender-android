package com.skoda.tender.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.skoda.launcher.ui.adapterimport.ServiceListAdapter
import com.skoda.tender.R
import com.skoda.tender.core.BaseFragment
import com.skoda.tender.databinding.FragmentHomeBinding
import com.skoda.tender.ui.main.MainActivityViewModel


/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : BaseFragment<MainActivityViewModel, FragmentHomeBinding >(MainActivityViewModel::class.java) {
    override fun getLayoutRes(): Int {

        return R.layout.fragment_home
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set onClick listener for the TextView
        mBinding.paymentText.setOnClickListener {
            // Navigate to FragmentTwo
//            val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
//            transaction.replace(R.id.fragment_container, PaymentServiceScreen())
//            transaction.addToBackStack(null)
//            transaction.commit()
        }

        val list = arrayListOf<String>()
        list.add("Ambient Lighting")
        list.add("Infotainment Online")
        list.add("Infotainment Online")
        mBinding.rvPaymentServices.layoutManager = LinearLayoutManager(requireContext(),RecyclerView.HORIZONTAL,false)
        mBinding.rvPaymentServices.adapter= ServiceListAdapter(list,object : ServiceListAdapter.SearchListener{
            override fun onClickItem(position: String) {
                val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
                transaction.replace(R.id.fragment_container, PaymentServiceScreen())
                transaction.addToBackStack(null)
                transaction.commit()
            }

        })
    }

}