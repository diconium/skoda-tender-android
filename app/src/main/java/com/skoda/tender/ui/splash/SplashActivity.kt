package com.skoda.tender.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.skoda.tender.ui.main.MainActivity

/**
 * SplashActivity displays a splash screen for a short duration
 * before transitioning to the MainActivity.
 */
class SplashActivity : AppCompatActivity() {

    /**
     * Called when the activity is created. Sets up the splash screen delay.
     * @param savedInstanceState A Bundle containing the activity's previously saved state.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Use a Handler to delay the transition to the MainActivity
        Handler(Looper.getMainLooper()).postDelayed({
            // Start MainActivity after a delay of 1000 milliseconds (1 second)
            startActivity(Intent(this, MainActivity::class.java))
            // Finish the SplashActivity to remove it from the back stack
            finish()
        }, timeDelay.toLong()) // Delay time in milliseconds
    }

    companion object {
        const val  timeDelay = 1000;
    }
}
