package com.quiz.repo

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.ktx.Firebase
import com.quiz.repo.Cart.CartRepoImpl
import com.quiz.repo.Model.Address
import com.quiz.repo.Model.Cart_Model
import com.quiz.repo.Model.Payment_Model
import com.quiz.repo.auth.AuthencationRepoImpl
import com.quiz.repo.auth.LoginRepoImpl
import com.quiz.util.Contants
import com.quiz.util.Resource
import kotlinx.coroutines.tasks.await

class repository {


    val authencationImpl = AuthencationRepoImpl()
    val loginRepoImpl = LoginRepoImpl()
    var userID: String? = null
    val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance();
    private var cartRepoImpl:CartRepoImpl

    init {
userID = if (Firebase.auth.currentUser== null) {
    null
        }else{
            FirebaseAuth.getInstance().currentUser!!.uid
        }
        cartRepoImpl = userID?.let { CartRepoImpl(it) }!!
}

   suspend fun AuthenticateRegisterUser(email: String, password: String, name: String) : Resource<String>{
       when(val response =authencationImpl.AuthenticateRegisterUser(email,password, name)){
           is Resource.Success ->{userID = response.data!!}
       }

       return authencationImpl.AuthenticateRegisterUser(email,password, name)
   }

    suspend fun userLogin(email:String,password:String):Resource<Boolean>{
        return loginRepoImpl.userLogin(email,password)
    }



    suspend fun addCartItems(addToCart: Cart_Model):Resource<Boolean> {
        return cartRepoImpl.addCartItems(addToCart)
    }


    suspend fun getQuantityById(id: String): Resource<Long> {
        Log.d("repo", "getQuantityById: $id")
        return cartRepoImpl.getQuantityById(id)
    }

    suspend fun addQuantityById(id: String) :Resource<Boolean>{
       return cartRepoImpl.addQuantityById(id)
    }

    suspend fun minusQuantityById(id: String):Resource<Boolean> {
      return cartRepoImpl.minusQuantityById(id)
    }

    suspend fun removeCartProductById(id: String):Resource<Boolean> {
       return cartRepoImpl.removeCartProductById(id)
    }

    companion object {
        const val TAG = "Repo"
    }


    suspend fun add_address(address: Address) {

        if (userID != null) {
            firebaseFirestore.collection("USER").document(userID!!)
                    .collection("address")
                    .document().set(address, SetOptions.merge())

            Log.d(TAG, "msg:" + userID)
        }



    }


    fun getUser_id(): String? {

        return userID
    }


    suspend fun delete_add(id: String) {

        if (userID != null) {
            firebaseFirestore.collection("USER").document(userID!!)
                    .collection("address")
                    .document(id).delete()

        }

    }


    suspend fun edit_add(id:String , address: Address){


        firebaseFirestore.collection("USER").document(userID!!)
                .collection("address")
                .document(id).set(address, SetOptions.merge())

    }
    suspend fun payment_details(id: String):Payment_Model{
        var paymentModel:Payment_Model?=null

        firebaseFirestore.collection("USER").document(userID!!)

            .get().addOnSuccessListener {

                 paymentModel = Payment_Model(it.get("firstname").toString(),it.get("email").toString(),it.get("mobile").toString())

            }

            .addOnFailureListener {

            }.await()
        return paymentModel!!
    }


}

