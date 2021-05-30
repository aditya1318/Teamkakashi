package com.quiz.repo.appDatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.quiz.repo.Model.User
import com.quiz.util.Resource
@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserDetail(user:User)
    @Query("SELECT * FROM User")
    suspend fun getUserDetail():User
    @Query("DELETE FROM User")
    suspend fun DeleteUserDetail()
}