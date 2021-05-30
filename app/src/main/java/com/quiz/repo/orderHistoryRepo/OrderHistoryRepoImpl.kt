package com.quiz.repo.orderHistoryRepo


import com.google.firebase.firestore.FirebaseFirestore
import com.quiz.repo.Model.OrderDetail
import com.quiz.repo.Model.Payment_Model
import com.quiz.util.Resource

class OrderHistoryRepoImpl:OrderHistoryRepo {
    override suspend fun paymentOrder(userId: String,paymentModel: OrderDetail): Resource<Boolean> {
        return try {
            FirebaseFirestore.getInstance().collection("USER").document(userId)
                    .collection("OrderHistory").add(paymentModel)
            Resource.Success(null)
        }catch (e:Exception){
            Resource.Error(e.message!!)
        }
    }
}