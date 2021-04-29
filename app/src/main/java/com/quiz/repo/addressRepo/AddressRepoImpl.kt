package com.quiz.repo.addressRepo

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.quiz.repo.Model.Address
import com.quiz.repo.repository
import com.quiz.util.Resource
<<<<<<< HEAD
import kotlin.math.log
=======
>>>>>>> origin/main

class AddressRepoImpl : AddressRepo {


    var result = false

    val firebaseFirestore  = FirebaseFirestore.getInstance()
    var userID = FirebaseAuth.getInstance().currentUser?.uid

<<<<<<< HEAD
    override suspend fun add_address(address: Address): Resource<Boolean> {
=======
    override suspend fun add_address(address: Address,UserId : String): Resource<Boolean> {
>>>>>>> origin/main
        var errorMessage = " "
      try {

          if (userID != null) {
<<<<<<< HEAD
              firebaseFirestore.collection("USER").document(userID!!)
=======
              firebaseFirestore.collection("USER").document(UserId)
>>>>>>> origin/main
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




<<<<<<< HEAD

    override suspend fun delete_add(id: String): Resource<Boolean> {
        
        var errorMessage = " "

        try {
            if (userID != null) {
                firebaseFirestore.collection("USER").document(userID!!)
                        .collection("address")
                        .document(id).delete()

                result = true

            }
            return if(result) Resource.Success<Boolean>(true) else {Resource.Error<Boolean>(errorMessage)}

        }catch (E:Exception){

            return Resource.Error(E.localizedMessage!!)
=======
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
>>>>>>> origin/main

        }
    }



<<<<<<< HEAD
        override suspend fun edit_add(id: String ,address: Address): Resource<Boolean> {
            var errorMessage = " "

            try {

                firebaseFirestore.collection("USER").document(userID!!)
                        .collection("address")
                        .document(id).set(address, SetOptions.merge())

                result = true
                return if(result) Resource.Success<Boolean>(true) else {Resource.Error<Boolean>(errorMessage)}

               }catch (e:Exception){

                return Resource.Error<Boolean>(e.localizedMessage!!)
            }

        }
=======
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
>>>>>>> origin/main
}