package com.quiz.UseCases

import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.quiz.Model.User
import com.quiz.ecommerce.Register

class UserDetailImpli : UserDetailUseCase {
    val db:FirebaseFirestore = FirebaseFirestore.getInstance()
    override suspend fun createUserDetail(activity:Register,user: User) {
   db.collection("USER")
           .document()
           .set(user, SetOptions.merge())
           .addOnSuccessListener {
               activity.getInstance()?.userRegistrationSuccess()
           }
    }
}