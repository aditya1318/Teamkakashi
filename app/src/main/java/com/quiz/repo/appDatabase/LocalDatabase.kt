package com.quiz.repo.appDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.quiz.repo.Model.User

@Database(entities = arrayOf(User::class), version = 1,exportSchema = false)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun UserDao() :UserDao


    companion object{
        private var Instance :LocalDatabase? =null


        fun getDatabase(context: Context): LocalDatabase? {
            val tempInstance = Instance
            if(tempInstance!=null){
                return Instance
            }
            synchronized(LocalDatabase::class.java){
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        LocalDatabase::class.java,
                        "UserDatabase"
                ).build()
                Instance=instance
                return instance
            }
        }
    }
}