package com.quiz.repo.Cart

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.quiz.repo.Model.Cart_Model
import com.quiz.repo.repository
import com.quiz.util.Resource
import kotlinx.coroutines.tasks.await

class CartRepoImpl(private val userID:String) :CartRepo{
    private val firebaseFirestore  = FirebaseFirestore.getInstance().collection("USER").document(userID)
            .collection("Cart");

    override suspend fun addCartItems(addToCart: Cart_Model): Resource<Boolean> {
        var result =false
        var errorMsg =""
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

    override suspend fun getQuantityById(id: String): Resource<Long> {
        var result =false
        var errorMsg =""
        var data :Long? =null

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

    override suspend fun addQuantityById(id: String): Resource<Boolean> {
        var result =false
        var errorMsg =""
        var data: Long?

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

    override suspend fun minusQuantityById(id: String): Resource<Boolean> {
        var count: Long? = null;
        var result =false
        var errorMsg =""

            firebaseFirestore.whereEqualTo("product_id", id)
                    .get().addOnSuccessListener {

                        it.forEach { i ->
                            count = i.get("quantity") as Long?
                            val num: Int = count!!.toInt() - 1
                            i.reference.update("quantity", num)
                        }
                        result = true
                    }.addOnFailureListener{
                        errorMsg = ({errorMsg = it.message.toString()}).toString()
                    }.await()
        return if(result) Resource.Success(null) else Resource.Error(errorMsg)
    }

    override suspend fun removeCartProductById(id: String): Resource<Boolean> {
        var errorMsg =""
        var result =false
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