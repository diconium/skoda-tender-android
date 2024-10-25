package com.skoda.tender.ui.fragment

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.messaging.messaging
import com.skoda.tender.R
import com.skoda.tender.core.BaseFragment
import com.skoda.tender.data.source.remote.config.APIConfig
import com.skoda.tender.data.source.response.ApiResult
import com.skoda.tender.data.source.response.SubscriptionStatus
import com.skoda.tender.data.source.response.Subscriptions
import com.skoda.tender.databinding.FragmentHomeBinding
import com.skoda.tender.service.CHANNEL_ID
import com.skoda.tender.ui.adapter.ServiceListAdapter
import com.skoda.tender.ui.main.MainActivity
import com.skoda.tender.ui.viewmodel.ServiceViewModel
import java.util.Date
import java.util.concurrent.TimeUnit

/**
 * Fragment that represents the home screen of the application.
 *
 * This fragment displays a list of services and handles notifications.
 */
class HomeFragment :
    BaseFragment<ServiceViewModel, FragmentHomeBinding>(ServiceViewModel::class.java) {

    // Notification-related constants
    private val NOTIFICATION_ID = 1

    private lateinit var serviceListAdapter: ServiceListAdapter
    private val TAG: String = HomeFragment::class.java.simpleName
    private var mAllSubscriptions: ArrayList<Subscriptions>? = null
    private var mFilterActive: Boolean = false

    // Launcher for requesting notification permissions
    private val notificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission granted, show the notification
            //showNotification()
        } else {
            // Handle the case where the permission is denied
            Log.i(TAG, "Notification permission denied")
        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_home
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up observers and load subscription data
        storiesDataObserver()
        viewModel.getSubscriptionsData()
        setAdapter(arrayListOf())
        val topic = APIConfig.VIN_NO
        Firebase.messaging.subscribeToTopic(topic).addOnCompleteListener {
            Log.i(
                TAG, "subscribed: $topic ${it.isSuccessful}"
            )
        }
        createNotificationChannel()
    }

    /**
     * Applies a filter to the list of subscriptions based on their active status.
     *
     * @param isActive If true, only active subscriptions will be shown.
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

    /**
     * Sets up an observer for subscription data from the ViewModel.
     */
    private fun storiesDataObserver() {
        viewModel.subscriptionsLiveData.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ApiResult.Loading -> {
                    Log.i(TAG, "storiesDataObserver: loading ")
                    // TODO: Show progress bar
                }

                is ApiResult.Success -> {
                    Log.i(TAG, "storiesDataObserver: Success ")
                    response.data?.let {
                        mAllSubscriptions = it.subscriptions
                        applyFilter(mFilterActive)
                        for (it in mAllSubscriptions!!) {
                            val diffInMillisec = it.endDate!!.time - Date().time
                            // Convert the difference from milliseconds to days
                            val diffInDays: Long = TimeUnit.MILLISECONDS.toDays(diffInMillisec)
                            Log.i(TAG, "storiesDataObserver: " + diffInDays)
                            if (diffInDays in 1..30) {
                                it.name?.let { it1 -> showNotification("The ${it1}") }
                                break;

                            }
                        }
                    }
                    // TODO: Hide progress bar
                }

                is ApiResult.Error -> {
                    Log.i(TAG, "storiesDataObserver: Error " + response)
                    // TODO: Hide progress bar
                }

                is ApiResult.Nothing -> {
                    Log.i(TAG, "storiesDataObserver: Nothing ")
                    // TODO: Hide progress bar
                }
            }
        }
    }

    /**
     * Sets up the RecyclerView adapter with a list of subscriptions.
     *
     * @param subscriptions The list of subscriptions to display.
     */
    private fun setAdapter(subscriptions: ArrayList<Subscriptions>) {
        mBinding.rvPaymentServices.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        serviceListAdapter =
            ServiceListAdapter(subscriptions, object : ServiceListAdapter.ServiceClickListener {
                override fun onClickItem(subscriptions: Subscriptions) {
                    // Navigate to PaymentServiceScreen when an item is clicked
                    requireActivity().supportFragmentManager.beginTransaction()
                        .add(R.id.fragment_container2, PaymentServiceScreen()).commit()
                }
            })
        mBinding.rvPaymentServices.adapter = serviceListAdapter
    }

    /**
     * Shows a notification indicating that the HomeFragment has launched.
     */
    private fun showNotification(title: String) {
        Log.i(TAG, "Attempting to show notification")

        // Create the notification channel if needed (for Android 8.0+)
        createNotificationChannel()

        // Request notification permission for Android 13+ (Tiramisu)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED -> {
                    // Permission is already granted, so show the notification

                    // Pass the joined descriptions to buildAndShowNotification
                    buildAndShowNotification(title)
                }

                else -> {
                    // Request the permission
                    notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        } else {
            // For older versions, show the notification directly
            // Pass the joined descriptions to buildAndShowNotification
            buildAndShowNotification(title)
        }

    }

    /**
     * Builds and shows the notification.
     */
    private fun buildAndShowNotification(description: String) {
        // Create an explicit intent for an Activity in your app (optional)
        val intent = Intent(requireContext(), MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(requireContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE)

        // Build the notification
        val builder = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
            .setSmallIcon(R.drawable.notification_icon) // Replace with your app's icon
            .setContentTitle(context?.getString(R.string.notification_expired))
            .setContentText(getString(R.string.notification_expired_desc,description))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent) // Optional, if you want an action on click
            .setAutoCancel(true)

        // Show the notification
        with(NotificationManagerCompat.from(requireContext())) {
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // Permission not granted, consider requesting it
                return
            }
            notify(NOTIFICATION_ID, builder.build())
        }
    }

    /**
     * Creates a notification channel for Android 8.0+.
     */
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Home Fragment Channel"
            val descriptionText = "Channel for Home Fragment notifications"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            // Register the channel with the system
            val notificationManager: NotificationManager =
                requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
