package com.quiz.repo.auth

import com.quiz.util.Event
import com.quiz.util.Resource

interface LoginRepo {
    suspend fun userLogin(email:String,password:String): Resource<Boolean>
}