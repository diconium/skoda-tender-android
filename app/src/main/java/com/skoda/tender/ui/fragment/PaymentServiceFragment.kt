package com.skoda.tender.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.skoda.tender.R
import com.skoda.tender.core.BaseFragment
import com.skoda.tender.databinding.FragmentHomeBinding
import com.skoda.tender.databinding.FragmentPaymentServiceBinding
import com.skoda.tender.ui.main.MainActivityViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PaymentServiceFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PaymentServiceFragment : BaseFragment<MainActivityViewModel, FragmentPaymentServiceBinding>(MainActivityViewModel::class.java) {
    override fun getLayoutRes(): Int {

        return R.layout.fragment_payment_service
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set onClick listener for the TextView
        mBinding.text2.setOnClickListener {
            // Navigate to FragmentTwo
            val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, HomeFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }
}