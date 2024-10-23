package com.skoda.tender.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.skoda.launcher.ui.adapterimport.SearchAdapter
import com.skoda.tender.R
import com.skoda.tender.core.BaseFragment
import com.skoda.tender.databinding.FragmentHomeBinding
import com.skoda.tender.databinding.FragmentPaymentServiceScreenBinding
import com.skoda.tender.ui.main.MainActivityViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PaymentServiceScreen.newInstance] factory method to
 * create an instance of this fragment.
 */
class PaymentServiceScreen : BaseFragment<MainActivityViewModel, FragmentPaymentServiceScreenBinding>(
    MainActivityViewModel::class.java) {
    override fun getLayoutRes(): Int {

        return R.layout.fragment_payment_service_screen
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
        mBinding.recyclerViewLayout.layoutManager = LinearLayoutManager(requireContext(),
            RecyclerView.HORIZONTAL,false)
        mBinding.recyclerViewLayout.adapter= SearchAdapter(list,object : SearchAdapter.SearchListener{
            override fun onClickItem(position: String) {
//                val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
//                transaction.replace(R.id.fragment_container, PaymentServiceScreen())
//                transaction.addToBackStack(null)
//                transaction.commit()
            }

        })
    }

}