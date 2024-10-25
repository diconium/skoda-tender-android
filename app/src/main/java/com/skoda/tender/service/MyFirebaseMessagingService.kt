package com.skoda.tender.service


import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.skoda.tender.R

val CHANNEL_ID = "skoda_notification"

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.i("TAG", "onMessageReceived: ")
        // Check if message contains a data payload.
        if (remoteMessage.data.isNotEmpty()) {
            // Process the data here
        }

        // Check if message contains a notification payload.

        var messageBody = ""
        var messageTitle = ""

        if (remoteMessage.notification != null) {
            messageBody = remoteMessage.notification!!.body!!
            messageTitle = remoteMessage.notification!!.title!!
        } else if (remoteMessage.data != null) {
            messageBody = remoteMessage.data["message"]!!
            messageTitle =  remoteMessage.data["title"]!!
        } else
            return;

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle(messageTitle)
            .setContentText(messageBody)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        // Show the notification
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.i("TAG", "onMessageReceived: issue ")

            return
        }
        NotificationManagerCompat.from(this).notify(1, builder.build())

        Log.i("TAG", "onMessageReceived: last ")

    }
}