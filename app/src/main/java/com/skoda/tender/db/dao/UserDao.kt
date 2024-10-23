package com.skoda.tender.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.Companion.REPLACE
import com.skoda.tender.db.entities.User

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getUsers(): LiveData<List<User>>

    @Query("SELECT * FROM user where id = :exampleId")
    fun getUser(exampleId: Long): User

    @Insert(onConflict = REPLACE)
    fun insertUser(user: User)

    @Update
    fun updateUser(user: User)

    @Delete
    fun deleteUser(user: User)

    @Query("Select count(*) from User")
    fun getCount(): Int

}