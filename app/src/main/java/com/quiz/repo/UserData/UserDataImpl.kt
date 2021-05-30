package com.quiz.repo.UserData

import com.google.firebase.firestore.FirebaseFirestore
import com.quiz.repo.Model.User
import com.quiz.repo.appDatabase.UserDao
import com.quiz.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO

import kotlinx.coroutines.launch

class UserDataImpl(private val UserDao:UserDao) :userData{
    override suspend fun getUserData(): Resource<User> {
        return try {
            Resource.Success(UserDao.getUserDetail())
        }catch (e:Exception){
            Resource.Error(e.message!!)
        }

    }

    override suspend fun InsertUserData(user:User){
        FirebaseFirestore.getInstance().collection("USER").document(user.id)
                .get().addOnSuccessListener {
                   val recentUser= it.toObject(User::class.java)
                    if (recentUser != null) {
                         CoroutineScope(IO).launch {
                            UserDao.insertUserDetail(recentUser)
                        }

                    }
                }
       }

    override suspend fun DeleteUserData() {
        UserDao.DeleteUserDetail()
    }
}