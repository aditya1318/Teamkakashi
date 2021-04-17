package com.quiz.repo

import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.gms.tasks.Tasks.await
import com.google.firebase.FirebaseOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import com.quiz.Model.Cart_model
import com.quiz.repo.Model.Product_model
import com.quiz.ui.adapter.CartAdapter
import com.quiz.ui.productDetail
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.tasks.await

class repository  {

    private lateinit var adapter: CartAdapter;

    val firebaseFirestore : FirebaseFirestore= FirebaseFirestore.getInstance();

    suspend fun addCartItems( addToCart: Cart_model)
    {
        firebaseFirestore.collection("Cart")

                .document().set(addToCart, SetOptions.merge())
                .await()
    }


    suspend fun  getproductList(): FirestoreRecyclerOptions<Cart_model> {

        val query: Query =
            FirebaseFirestore.getInstance()
                .collection("Cart")
                .orderBy("product_rate", Query.Direction.ASCENDING)

        val options = FirestoreRecyclerOptions.Builder<Cart_model>()
            .setQuery(query, Cart_model::class.java)
            .build()


        return  options

    }

}