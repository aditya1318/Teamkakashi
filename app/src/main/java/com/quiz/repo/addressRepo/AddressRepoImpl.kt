package com.quiz.repo.addressRepo

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.quiz.repo.Model.Address
import com.quiz.repo.repository
import com.quiz.util.Resource

class AddressRepoImpl : AddressRepo {


    var result = false

    val firebaseFirestore  = FirebaseFirestore.getInstance()
    var userID = FirebaseAuth.getInstance().currentUser?.uid

    override suspend fun add_address(address: Address,UserId : String): Resource<Boolean> {
        var errorMessage = " "
      try {

          if (userID != null) {
              firebaseFirestore.collection("USER").document(UserId)
                      .collection("address")
                      .document().set(address, SetOptions.merge())

              result = true
              Log.d(repository.TAG, "msg:" + userID)
          }
          return if(result)  Resource.Success<Boolean>(true)  else{Resource.Error<Boolean>(errorMessage)}

      }catch (e:Exception){

         return Resource.Error<Boolean>(e.localizedMessage!!)
      }
    }




    override suspend fun delete_add(id: String,UserId : String): Resource<Boolean> {
        var errorMessage = " "
        Log.d("msg " , "$result")

        try {
            if (userID != null) {
                firebaseFirestore.collection("USER").document(UserId)
                        .collection("address")
                        .document(id).delete()

            }
            return if(result) Resource.Success<Boolean>(true) else {Resource.Error<Boolean>(errorMessage)}


        }catch (E:Exception){

            return Resource.Error<Boolean>(E.localizedMessage!!)

        }
    }



    override suspend fun edit_add(id: String ,address: Address,UserId : String): Resource<Boolean> {
        var errorMessage = " "

        try {

            firebaseFirestore.collection("USER").document(UserId)
                    .collection("address")
                    .document(id).set(address, SetOptions.merge())

            return if(result) Resource.Success<Boolean>(true) else {Resource.Error<Boolean>(errorMessage)}

           }catch (e:Exception){

            return Resource.Error<Boolean>(e.localizedMessage!!)
        }

    }
}