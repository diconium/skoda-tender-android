package com.skoda.tender.ui.main

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.skoda.tender.R
import com.skoda.tender.core.BaseActivity
import com.skoda.tender.databinding.ActivityMainBinding
import com.skoda.tender.ui.fragment.HomeFragment


class MainActivity : BaseActivity<MainActivityViewModel, ActivityMainBinding>(MainActivityViewModel::class.java) {
    override fun initViewModel(viewModel: MainActivityViewModel) {
        binding.viewModel = viewModel
    }
    override fun getLayoutRes() = R.layout.activity_main
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.navView.setOnItemSelectedListener(navListener)

        // as soon as the application opens the first
        // fragment should be shown to the user
        // in this case it is home fragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, HomeFragment()).commit()
    }

    private val navListener = NavigationBarView.OnItemSelectedListener { item: MenuItem ->
        // By using switch we can easily get
        // the selected fragment
        // by using there id.
        var selectedFragment: Fragment? = null
        val itemId = item.itemId
        if (itemId == R.id.navigation_home) {
            selectedFragment = HomeFragment()
        }

        // It will help to replace the
        // one fragment to other.
        if (selectedFragment != null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, selectedFragment).commit()
        }
        true
    }
}
