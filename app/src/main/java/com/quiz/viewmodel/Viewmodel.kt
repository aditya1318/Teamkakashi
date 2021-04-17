package com.quiz.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.quiz.Model.Cart_model
import com.quiz.repo.repository
import kotlinx.coroutines.*

class Viewmodel(application: Application) : AndroidViewModel(application) {

    val  repository : repository = repository();

     fun  addcart( addtocart : Cart_model ){

         viewModelScope.launch(Dispatchers.IO){

            repository.addCartItems(addtocart)
         }

     }





    }

