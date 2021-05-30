package com.quiz.repo.auth

import com.quiz.util.Resource

interface AuthenicationRepo {

    suspend fun AuthenticateRegisterUser(email: String, password: String, name: String,number:String) : Resource<String>
    fun getUserId():String



}