package com.quiz.ui

import com.quiz.repo.Model.Cart_Model

interface CartItemClickListener {
    fun onCartAddClick(model: Cart_Model)
    fun onCartMinusClick(model: Cart_Model)
}