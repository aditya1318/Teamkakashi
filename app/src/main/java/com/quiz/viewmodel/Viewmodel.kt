package com.quiz.viewmodel

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.quiz.Model.Address
import com.quiz.repo.Model.Product_model
import com.quiz.repo.repository
import kotlinx.coroutines.*

class Viewmodel(application: Application) : AndroidViewModel(application) {
  //  private val liveData:MutableLiveData<Product_model>
   val productitem = MutableLiveData<Product_model>()
    val repository: repository = repository();

    fun addcart(addtocart: Address) {

        viewModelScope.launch(Dispatchers.IO) {

            repository.addCartItems(addtocart)
        }

    }

    fun AuthenticateRegisterUser(email: String, name: String, v: View, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.AuthenticateRegisterUser(email, password, name, v)
        }
    }
    fun GetProductModel(productModel: Product_model)
    {
        productitem.value = productModel

    }

}

