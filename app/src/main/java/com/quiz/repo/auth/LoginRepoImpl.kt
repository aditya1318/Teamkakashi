package com.quiz.repo.auth

import android.content.ContentValues
import android.util.Log
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.quiz.ecommerce.R
import com.quiz.util.Resource
import kotlinx.coroutines.tasks.await

class LoginRepoImpl :LoginRepo{

    override suspend fun userLogin(email: String, password: String): Resource<Boolean> {
var errorMessage = ""
        var result = false

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->

                    if (task.isSuccessful) {
                       result=true

                    } else {
                        // Hide the progress dialog
                        //hideProgressDialog()
                        //  showErrorSnackBar(task.exception!!.message.toString(), true)
                        Log.d(ContentValues.TAG, "logInRegisteredUser: ${task.exception}")
                        errorMessage = if (task.exception == null){
                            "Unexpected error occur."
                        }else{
                            task.exception!!.message.toString()
                        }

                    }
                }.await()
     return if(result) Resource.Success(null) else Resource.Error(errorMessage)
    }

}