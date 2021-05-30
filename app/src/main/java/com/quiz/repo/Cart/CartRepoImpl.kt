package com.quiz.repo.Cart

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.quiz.repo.Model.Cart_Model
import com.quiz.repo.repository
import com.quiz.util.Resource
import kotlinx.coroutines.tasks.await
import java.util.ArrayList

class CartRepoImpl() :CartRepo{


    override suspend fun addCartItems(addToCart: Cart_Model,userID:String): Resource<Boolean> {

        return try {
            val firebaseFirestore = FirebaseFirestore.getInstance().collection("USER").document(userID)
                    .collection("Cart");
            firebaseFirestore.document().set(addToCart, SetOptions.merge()).addOnSuccessListener {


            }.await()
            Resource.Success(null)
        }catch (e:Exception)
        {
            Resource.Error(e.message!!)
        }

    }

    override suspend fun getCartList(userID: String): Resource<List<Cart_Model>> {
        try {
            var cartList = ArrayList<Cart_Model>()
            val firebaseFirestore = FirebaseFirestore.getInstance().collection("USER").document(userID)
                    .collection("Cart");

            firebaseFirestore.get().addOnSuccessListener {
                for (Cart_model in it){


                    val car = it.toObjects(Cart_Model::class.java)
                    cartList = car as ArrayList<Cart_Model>
                }
            }.await()
            Log.d("cartRepo", "getCartList: ${cartList}")
            return  Resource.Success(cartList)
        }catch (e:Exception)
        {
            Log.d("cartRepo", "getCartList: ${e.message}")
            return    Resource.Error(e.message!!)

        }
    }

    override suspend fun getQuantityById(id: String,userID:String): Resource<Long> {

        var data :Long? =null
        try {


            val firebaseFirestore = FirebaseFirestore.getInstance().collection("USER").document(userID)
                    .collection("Cart");
            firebaseFirestore.whereEqualTo("product_id", id)
                    .get().addOnSuccessListener { it ->
                        it.forEach { i ->
                            data = i.get("quantity") as Long?
                            Log.d("aaditya", "getQuantityById: ")
                        }
                        if (data == null) {
                            data = 0
                        }
                    }.await()

            return Resource.Success(data!!)
        }catch (e:Exception){
            return Resource.Error(e.message!!)
        }
    }

    override suspend fun addQuantityById(id: String,userID:String): Resource<Boolean> {

        var data: Long?
        try {


            val firebaseFirestore = FirebaseFirestore.getInstance().collection("USER").document(userID)
                    .collection("Cart");

            firebaseFirestore.whereEqualTo("product_id", id)
                    .get().addOnSuccessListener {

                        it.forEach { i ->
                            data = i.get("quantity") as Long?
                            val num: Int = data!!.toInt() + 1
                            i.reference.update("quantity", num)
                        }
                    }.await()


                        return Resource.Success(null)
        }catch (e:Exception){
            return Resource.Error(e.message!!)
        }
    }

    override suspend fun minusQuantityById(id: String,userID:String): Resource<Boolean> {
        var count: Long? = null;

        try {


            val firebaseFirestore = FirebaseFirestore.getInstance().collection("USER").document(userID)
                    .collection("Cart");

            firebaseFirestore.whereEqualTo("product_id", id)
                    .get().addOnSuccessListener {

                        it.forEach { i ->
                            count = i.get("quantity") as Long?
                            val num: Int = count!!.toInt() - 1

                            if (count!! > 1) {
                                i.reference.update("quantity", num)
                            } else {
                                firebaseFirestore.whereEqualTo("product_id", id)
                                        .get().addOnSuccessListener {
                                            it.forEach { i ->
                                                firebaseFirestore.document(i.reference.id).delete()

                                            }
                                        }
                            }
                        }

                    }.await()
            return  Resource.Success(null)
        }catch (e:Exception){
            return Resource.Error(e.message!!)
        }
    }

    override suspend fun removeCartProductById(id: String,userID:String): Resource<Boolean> {


        return try{
            val firebaseFirestore = FirebaseFirestore.getInstance().collection("USER").document(userID)
                    .collection("Cart");
            firebaseFirestore.whereEqualTo("product_id", id)
                    .get().addOnSuccessListener {
                        it.forEach { i ->
                            firebaseFirestore.document(i.reference.id).delete()

                        }
                    }.await()
            Resource.Success(null)
        }catch (e:Exception){
            Resource.Error(e.message!!)
        }
    }

    override suspend fun removeCart(userID: String): Resource<Boolean> {
        try {
            val firebaseFirestore = FirebaseFirestore.getInstance().collection("USER").document(userID)
                    .collection("Cart");
            firebaseFirestore.get().addOnSuccessListener { it ->
                it.forEach { id ->
                    firebaseFirestore.document(id.reference.toString()).delete()
                }
            }
            return Resource.Success(null)
        }catch (e:Exception){

return Resource.Error(e.message!!)
        }

    }

}