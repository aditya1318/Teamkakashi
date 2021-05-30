package com.quiz.repo.UserData

import com.quiz.repo.Model.User
import com.quiz.repo.appDatabase.UserDao
import com.quiz.util.Resource

interface userData {
    suspend fun getUserData():Resource<User>
    suspend fun InsertUserData(user:User)
    suspend fun DeleteUserData()
}