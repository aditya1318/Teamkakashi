package com.quiz.UseCases

import androidx.fragment.app.Fragment
import com.quiz.Model.User
import com.quiz.ecommerce.Register

interface UserDetailUseCase {
   suspend fun createUserDetail(activity: Register,user: User)
}