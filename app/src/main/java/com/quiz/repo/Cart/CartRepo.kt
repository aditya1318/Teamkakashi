package com.quiz.repo.Cart

import com.quiz.repo.Model.Cart_Model
import com.quiz.util.Resource

interface CartRepo {
    suspend fun addCartItems(addToCart: Cart_Model,userID:String):Resource<Boolean>;

    suspend fun getQuantityById(id: String,userID:String): Resource<Long>

    suspend fun addQuantityById(id: String,userID:String):Resource<Boolean>

    suspend fun minusQuantityById(id: String,userID:String):Resource<Boolean>

    suspend fun removeCartProductById(id: String,userID:String):Resource<Boolean>


    }