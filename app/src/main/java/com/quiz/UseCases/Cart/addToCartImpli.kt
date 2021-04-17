package com.quiz.UseCases.Cart

import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.quiz.Model.Product_model

class addToCartImpli :addToCartUseCase {
    private val mFireStore : FirebaseFirestore = FirebaseFirestore.getInstance()
    override suspend fun addToCart(product: Product_model, activity: Fragment) {

        mFireStore.collection("Cart")
                .document().set(product, SetOptions.merge()).addOnSuccessListener {
                    activity.
                }

    }

}