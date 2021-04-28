package com.quiz.repo.Cart

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.quiz.repo.Model.Cart_Model
import com.quiz.repo.repository
import com.quiz.util.Resource
import kotlinx.coroutines.tasks.await

class CartRepoImpl() :CartRepo{


    override suspend fun addCartItems(addToCart: Cart_Model,userID:String): Resource<Boolean> {
        var result =false
        var errorMsg =""
         val firebaseFirestore  = FirebaseFirestore.getInstance().collection("USER").document(userID)
                .collection("Cart");
        firebaseFirestore.document().set(addToCart, SetOptions.merge()).addOnSuccessListener {

            result =true

        }.addOnFailureListener{exception ->

            errorMsg = if(exception.localizedMessage ==null){
                "Unexpected error occur"
            }else ({
                errorMsg = exception.message!!.toString()
            }).toString()

        }.await()

        return if(result) Resource.Success(null) else Resource.Error(errorMsg)
    }

    override suspend fun getQuantityById(id: String,userID:String): Resource<Long> {
        var result =false
        var errorMsg =""
        var data :Long? =null
        val firebaseFirestore  = FirebaseFirestore.getInstance().collection("USER").document(userID)
                .collection("Cart");
                firebaseFirestore.whereEqualTo("product_id", id)
                .get().addOnSuccessListener { it ->
                    it.forEach { i ->
                     data = i.get("quantity") as Long?
                        Log.d("aaditya", "getQuantityById: ")
                    }
                            if (data==null){
                                data=0
                            }
                            result= true
                }.addOnFailureListener {
                 errorMsg = ({errorMsg = it.message.toString()}).toString()

                }.await()

        return if(result) Resource.Success(data!!) else Resource.Error(errorMsg)
    }

    override suspend fun addQuantityById(id: String,userID:String): Resource<Boolean> {
        var result =false
        var errorMsg =""
        var data: Long?
        val firebaseFirestore  = FirebaseFirestore.getInstance().collection("USER").document(userID)
                .collection("Cart");

        firebaseFirestore.whereEqualTo("product_id", id)
                    .get().addOnSuccessListener {

                        it.forEach { i ->
                            data = i.get("quantity") as Long?
                            val num: Int = data!!.toInt() + 1
                            i.reference.update("quantity", num)
                        }
                        result= true
                    }.addOnFailureListener{

                        errorMsg = ({errorMsg = it.message.toString()}).toString()
                    }.await()

        return if(result) Resource.Success(null) else Resource.Error(errorMsg)
    }

    override suspend fun minusQuantityById(id: String,userID:String): Resource<Boolean> {
        var count: Long? = null;
        var result =false
        var errorMsg =""
        val firebaseFirestore  = FirebaseFirestore.getInstance().collection("USER").document(userID)
                .collection("Cart");

            firebaseFirestore.whereEqualTo("product_id", id)
                    .get().addOnSuccessListener {

                        it.forEach { i ->
                            count = i.get("quantity") as Long?
                            val num: Int = count!!.toInt() - 1
                            if (count!! > 1) {
                                result = true
                                i.reference.update("quantity", num)
                            }else{
                                firebaseFirestore.whereEqualTo("product_id", id)
                                        .get().addOnSuccessListener {
                                            it.forEach { i ->
                                                firebaseFirestore.document(i.reference.id).delete()
                                                        .addOnSuccessListener {
                                                            result =true
                                                        }.addOnFailureListener {
                                                            result =false
                                                            errorMsg = ({errorMsg = it.message.toString()}).toString()
                                                        }
                                            }
                                        }
                            }
                        }

                    }.addOnFailureListener{
                        errorMsg = ({errorMsg = it.message.toString()}).toString()
                    }.await()
        return if(result) Resource.Success(null) else Resource.Error(errorMsg)
    }

    override suspend fun removeCartProductById(id: String,userID:String): Resource<Boolean> {
        var errorMsg =""
        var result =false
        val firebaseFirestore  = FirebaseFirestore.getInstance().collection("USER").document(userID)
                .collection("Cart");
            firebaseFirestore.whereEqualTo("product_id", id)
                    .get().addOnSuccessListener {
                        it.forEach { i ->
                            firebaseFirestore.document(i.reference.id).delete()
                                    .addOnSuccessListener {
                            result =true
                                    }.addOnFailureListener {
                                        errorMsg = ({errorMsg = it.message.toString()}).toString()
                                    }
                        }
                    }.await()
        return if(result) Resource.Success(null) else Resource.Error(errorMsg)
    }
}