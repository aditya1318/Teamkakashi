package com.quiz.viewmodel

import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.load.engine.cache.DiskCacheAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.quiz.Model.Cart_model
import com.quiz.repo.Model.Product_model
import com.quiz.repo.Model.User
import com.quiz.repo.repository
import kotlinx.coroutines.*
import java.io.PrintWriter
import java.net.PasswordAuthentication

class Viewmodel(application: Application) : AndroidViewModel(application) {
  //  private val liveData:MutableLiveData<Product_model>
   val productitem = MutableLiveData<Product_model>()
    val repository: repository = repository();

    fun addcart(addtocart: Cart_model) {

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

