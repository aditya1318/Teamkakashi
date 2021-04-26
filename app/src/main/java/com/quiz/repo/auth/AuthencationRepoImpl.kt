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
    override suspend fun AuthenticateRegisterUser(email: String, password: String, name: String): Resource<Boolean> {
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

                                          email.trim { it <= ' ' }
                                  )
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



                            return if(result)  Resource.Success<Boolean>(true)  else{Resource.Error<Boolean>(errorString)}
                         }
         catch(e:Exception){

                           return  Resource.Error<Boolean>(e.localizedMessage!!)


      }

    }
}