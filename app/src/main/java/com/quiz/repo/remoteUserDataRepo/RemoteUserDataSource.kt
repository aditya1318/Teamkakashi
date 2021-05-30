package com.quiz.repo.remoteUserDataRepo


import com.quiz.repo.Model.User
import com.quiz.util.Resource

interface RemoteUserDataSource {
    suspend fun getUserDataRemote(userId: String):Resource<User>
}