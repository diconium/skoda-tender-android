package com.skoda.tender.di.module

import androidx.room.Room
import android.content.Context
import com.skoda.tender.db.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

// Dagger module responsible for providing the database instance
@Module
class DatabaseModule {

    // Provides a singleton instance of the AppDatabase
    @Singleton
    @Provides
    fun getDatabase(context: Context): AppDatabase {
        // Build and return the AppDatabase instance
        return Room.databaseBuilder(
            context, // Application context
            AppDatabase::class.java, // Database class
            "example-db" // Name of the database
        ).build() // Finalize the database builder to create the database
    }
}
