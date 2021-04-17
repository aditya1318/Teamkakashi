package com.quiz

import com.google.android.material.card.MaterialCardView
import com.quiz.repo.Model.Product_model

interface ProductClickListener {
    fun onProductClickListener(model: Product_model, holder: MaterialCardView)
}
