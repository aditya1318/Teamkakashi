package com.quiz.UseCases.Cart

import android.app.Activity
import androidx.fragment.app.Fragment
import com.quiz.Model.Product_model
import com.quiz.ecommerce.productDetail

interface addToCartUseCase {
    suspend fun addToCart(product:Product_model,activity:Activity)
}