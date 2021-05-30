package com.quiz.repo.totalPrice

import com.quiz.util.Resource

interface TotalPriceRepo {
    suspend fun getTotalPrice(userID:String):Resource<Long>
}