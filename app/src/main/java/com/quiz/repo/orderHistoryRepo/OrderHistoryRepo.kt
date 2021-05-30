package com.quiz.repo.orderHistoryRepo


import com.quiz.repo.Model.OrderDetail
import com.quiz.repo.Model.Payment_Model
import com.quiz.util.Resource

interface OrderHistoryRepo {
    suspend fun paymentOrder(userId:String,paymentModel: OrderDetail): Resource<Boolean>

}