package com.quiz.repo.auth

import android.content.ContentValues
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.quiz.repo.Model.User

import com.quiz.util.Resource
import kotlinx.coroutines.tasks.await

class AuthencationRepoImpl: AuthenicationRepo {

    val firebaseFirestore  = FirebaseFirestore.getInstance()
    lateinit var user:String
    override suspend fun AuthenticateRegisterUser(email: String, password: String, name: String,number:String): Resource<String> {
        var result = false
        var errorString = " "
        try {

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(

                            OnCompleteListener<AuthResult> { task ->
                                // If the registration is successfully done
                                if (task.isSuccessful) {
                                    // Firebase registered user
                                    val firebaseUser: FirebaseUser = task.result!!.user!!
                                    // Instance of User data model class.
                                    val user = User(
                                            firebaseUser.uid,
                                            name.trim { it <= ' ' },
                                            email.trim { it <= ' ' },
                                            number.trim{it <= ' '}
                                    )
                                    this.user =user.id
                                    result = true

                                    // Pass the required values in the constructor.
                                    //     FirestoreClass().registerUser(this@RegisterActivity, user)

                                    try {
                                        firebaseFirestore.collection("USER")
                                                .document(user.id).set(user, SetOptions.merge())


                                    } catch (e: Exception) {
                                        Log.d(ContentValues.TAG, "registerUser: e ${e.toString()}")
                                    }


                                }else{
                                    errorString =task.exception!!.message!!.toString()
                                }
                            }).await();



            return if(result)  Resource.Success<String>(user)  else{Resource.Error<String>(errorString)}
        }
        catch(e:Exception){

            return  Resource.Error<String>(e.localizedMessage!!)


        }

    }

    override fun getUserId(): String {
        return FirebaseAuth.getInstance().currentUser!!.uid
    }


}