package com.quiz.repo.totalPrice

import com.google.firebase.firestore.FirebaseFirestore
import com.quiz.util.Resource
import kotlinx.coroutines.tasks.await

class TotalPriceRepoImpl:TotalPriceRepo {
    override suspend fun getTotalPrice(userID:String): Resource<Long> {
        var finalPrice :Long =0;
try {


    FirebaseFirestore.getInstance().collection("USER").document(userID)
            .collection("Cart")
            .get().addOnSuccessListener {
                it.forEach { product ->
                    val rate: String = product.get("product_rate") as String
                    val quantity = product.get("quantity") as Long
                    finalPrice += rate.toInt() * quantity
                }
            }.await()
    return Resource.Success(finalPrice)
}catch (e:Exception){
    return  Resource.Error(e.localizedMessage!!)
}
    }
}