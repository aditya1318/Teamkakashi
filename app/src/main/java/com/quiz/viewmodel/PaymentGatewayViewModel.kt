package com.quiz.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quiz.repo.Model.Cart_Model
import com.quiz.repo.repository
import com.quiz.util.Resource
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PaymentGatewayViewModel(application: Application): AndroidViewModel(application) {
    val repo: repository = repository(application)

    fun getCartList(userId: String): Flow<List<Cart_Model>> {
        var List :List<Cart_Model>? = null
        return flow {
            when (val response = repo.getCartList(userId)) {
                is Resource.Success<*> -> {

                    Log.d("payViewModel", "getCartList: ${response.data} ")

                     emit(response.data!!)

                }
                is Resource.Error<*> -> {

                }
            }

        }

    }

}