package com.quiz.UseCases

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.quiz.Model.User
import com.quiz.ecommerce.Register

class UserDetailImpli : UserDetailUseCase {
    val db:FirebaseFirestore = FirebaseFirestore.getInstance()
    override suspend fun createUserDetail(activity:FragmentActivity,user: User) {
   db.collection("USER")
           .document()
           .set(user, SetOptions.merge())
           .addOnSuccessListener {
               activity.getInstance().userRegistrationSuccess()
           }
    }
}