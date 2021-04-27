package com.quiz.repo

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.quiz.repo.Model.Address
import com.quiz.repo.Model.Cart_Model
import com.quiz.repo.Model.Payment_Model
import com.quiz.repo.auth.AuthencationRepoImpl
import com.quiz.repo.auth.LoginRepoImpl
import com.quiz.util.Resource
import kotlinx.coroutines.tasks.await

class repository {


    val authencationImpl = AuthencationRepoImpl()
    val loginRepoImpl = LoginRepoImpl()
    var userID = FirebaseAuth.getInstance().currentUser?.uid
    val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance();



   suspend fun AuthenticateRegisterUser(email: String, password: String, name: String) : Resource<Boolean>{

       return authencationImpl.AuthenticateRegisterUser(email,password, name)
   }

    suspend fun userLogin(email:String,password:String):Resource<Boolean>{
        return loginRepoImpl.userLogin(email,password)
    }



    suspend fun addCartItems(addToCart: Cart_Model) {


        if (userID != null) {
            firebaseFirestore.collection("USER").document(userID!!)
                    .collection("Cart").document().set(addToCart, SetOptions.merge())
                    .await()
        }
    }


    suspend fun getQuantityById(id: String): Long {

        var count: Long? = null;
        if (userID != null) {
            firebaseFirestore.collection("USER").document(userID!!).collection("Cart").whereEqualTo("product_id", id)
                    .get().addOnSuccessListener { it ->
                        it.forEach { i ->
                            count = i.get("quantity") as Long?
                            Log.d(TAG, "getQuantityById: ${count.toString()}")

                            Log.d(TAG, "getQuantityById: ${i.getString("product_id").toString()}")
                        }
                    }.addOnFailureListener {
                        Log.d(TAG, "getQuantityById: ${it.message}")
                    }.await()
        }

        return count ?: 0
    }

    suspend fun addQuantityById(id: String) {
        var count: Long? = null;
        if (userID != null) {
            firebaseFirestore.collection("USER").document(userID!!).collection("Cart").whereEqualTo("product_id", id)
                    .get().addOnSuccessListener {

                        it.forEach { i ->
                            count = i.get("quantity") as Long?
                            val num: Int = count!!.toInt() + 1
                            i.reference.update("quantity", num)
                        }
                    }
        }
    }

    suspend fun minusQuantityById(id: String) {
        var count: Long? = null;
        if (userID != null) {
            firebaseFirestore.collection("USER").document(userID!!).collection("Cart").whereEqualTo("product_id", id)
                    .get().addOnSuccessListener {

                        it.forEach { i ->
                            count = i.get("quantity") as Long?
                            val num: Int = count!!.toInt() - 1
                            i.reference.update("quantity", num)
                        }
                    }
        }
    }

    suspend fun removeCartProductById(id: String) {
        if (userID != null) {
            firebaseFirestore.collection("USER").document(userID!!).collection("Cart").whereEqualTo("product_id", id)
                    .get().addOnSuccessListener {
                        it.forEach { i ->
                            firebaseFirestore.collection("USER").document(userID!!).collection("Cart").document(i.reference.id).delete()
                                    .addOnSuccessListener {

                                    }.addOnFailureListener {

                                    }
                        }
                    }
        }
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

