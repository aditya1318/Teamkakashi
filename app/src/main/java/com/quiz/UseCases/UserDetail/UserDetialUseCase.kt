package com.quiz.UseCases

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.quiz.Model.User
import com.quiz.ecommerce.Register

interface UserDetailUseCase {
   suspend fun createUserDetail(activity: FragmentActivity,user: User)
}