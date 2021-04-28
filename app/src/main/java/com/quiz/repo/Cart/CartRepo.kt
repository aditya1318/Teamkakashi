package com.quiz.repo.Cart

import com.quiz.repo.Model.Cart_Model
import com.quiz.util.Resource

interface CartRepo {
    suspend fun addCartItems(addToCart: Cart_Model):Resource<Boolean>;

    suspend fun getQuantityById(id: String): Resource<Long>

    suspend fun addQuantityById(id: String):Resource<Boolean>

    suspend fun minusQuantityById(id: String):Resource<Boolean>

    suspend fun removeCartProductById(id: String):Resource<Boolean>


    }