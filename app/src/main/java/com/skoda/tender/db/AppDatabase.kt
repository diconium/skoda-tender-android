package com.skoda.tender.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.skoda.tender.db.dao.UserDao
import com.skoda.tender.db.entities.User

@Database(entities = arrayOf(User::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}