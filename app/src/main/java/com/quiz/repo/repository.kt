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

    val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance();
    private var cartRepoImpl: CartRepoImpl = CartRepoImpl()




    suspend fun AuthenticateRegisterUser(email: String, password: String, name: String) : Resource<String>{

        return authencationImpl.AuthenticateRegisterUser(email,password, name)
    }

    suspend fun userLogin(email:String,password:String):Resource<Boolean>{
        return loginRepoImpl.userLogin(email,password)
    }



    suspend fun addCartItems(addToCart: Cart_Model,userID:String):Resource<Boolean> {
        return cartRepoImpl.addCartItems(addToCart,userID)
    }


    suspend fun getQuantityById(id: String,userID:String): Resource<Long> {
        Log.d("repo", "getQuantityById: $id")
        return cartRepoImpl.getQuantityById(id,userID)
    }

    suspend fun addQuantityById(id: String,userID:String) :Resource<Boolean>{
        return cartRepoImpl.addQuantityById(id,userID)
    }

    suspend fun minusQuantityById(id: String,userID:String):Resource<Boolean> {
        return cartRepoImpl.minusQuantityById(id, userID)
    }

    suspend fun removeCartProductById(id: String,userID:String):Resource<Boolean> {
        return cartRepoImpl.removeCartProductById(id, userID)
    }

    companion object {
        const val TAG = "Repo"
    }


    suspend fun add_address(address: Address) {





    }


    fun getUser_id(): String? {
        return authencationImpl.getUserId()

    }


    suspend fun delete_add(id: String) {


    }


    suspend fun edit_add(id:String , address: Address){



    }
    /* suspend fun payment_details(id: String):Payment_Model{
         var paymentModel:Payment_Model?=null

         firebaseFirestore.collection("USER").document(userID!!)

             .get().addOnSuccessListener {

                  paymentModel = Payment_Model(it.get("firstname").toString(),it.get("email").toString(),it.get("mobile").toString())

             }

             .addOnFailureListener {

             }.await()
         return paymentModel!!
     }
 */

}