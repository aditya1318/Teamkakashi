package com.quiz.repo.auth

import android.content.ContentValues
import android.util.Log
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.quiz.ecommerce.R
import com.quiz.util.Event
import com.quiz.util.Resource
import kotlinx.coroutines.tasks.await

import kotlin.Exception

class LoginRepoImpl :LoginRepo{

    override suspend fun userLogin(email: String, password: String): Resource<Boolean> {

        try {

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->

                        if (task.isSuccessful) {


                        } else {




                        }
                    }.await()
            return  Resource.Success(null)
        }catch (e :Exception){
            return Resource.Error(e.localizedMessage!!)
        }

    }

}