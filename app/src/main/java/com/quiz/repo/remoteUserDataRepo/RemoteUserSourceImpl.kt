package com.quiz.repo.remoteUserDataRepo


import com.google.firebase.firestore.FirebaseFirestore
import com.quiz.repo.Model.User
import com.quiz.util.Resource
import kotlinx.coroutines.tasks.await

class RemoteUserSourceImpl :RemoteUserDataSource{
    override suspend fun getUserDataRemote(userId: String): Resource<User> {
        try {


        var userData : User? = null
        FirebaseFirestore.getInstance().collection("USER").document(userId)
                .get().addOnSuccessListener {
                    userData=it.toObject(User::class.java)!!
                }.await()
            return Resource.Success(userData!!)
                }catch (e:Exception){
                    return Resource.Error(e.message!!)
                }

    }
}