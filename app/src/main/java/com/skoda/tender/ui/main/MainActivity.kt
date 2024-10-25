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

/**
 * MainActivity serves as the entry point of the application, managing the bottom navigation
 * and fragment transactions for the main user interface.
 */
class MainActivity : BaseActivity<MainActivityViewModel, ActivityMainBinding>(MainActivityViewModel::class.java) {

    /**
     * Initializes the ViewModel for the activity.
     * @param viewModel The ViewModel instance for this activity.
     */
    override fun initViewModel(viewModel: MainActivityViewModel) {
        binding.viewModel = viewModel
    }

    /**
     * Returns the layout resource ID for this activity.
     * @return The layout resource ID.
     */
    override fun getLayoutRes() = R.layout.activity_main

    /**
     * Called when the activity is created. Sets up the initial fragment and the navigation listener.
     * @param savedInstanceState A Bundle containing the activity's previously saved state.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set the listener for bottom navigation item selection
        binding.bottomView.setOnItemSelectedListener(navListener)

        // Load the HomeFragment as the initial fragment when the application starts
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, HomeFragment()).commit()
    }

    /**
     * Navigation listener to handle item selection in the bottom navigation view.
     */
    private val navListener = NavigationBarView.OnItemSelectedListener { item: MenuItem ->
        // Variable to hold the selected fragment
        var selectedFragment: Fragment? = null

        // Get the selected item's ID
        val itemId = item.itemId

        // Determine which fragment to show based on the selected item
        if (itemId == R.id.navigation_home) {
            selectedFragment = HomeFragment()
        }

        // Replace the current fragment with the selected fragment, if it's not null
        if (selectedFragment != null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, selectedFragment).commit()
        }
        true // Indicate that the item selection has been handled
    }
}
