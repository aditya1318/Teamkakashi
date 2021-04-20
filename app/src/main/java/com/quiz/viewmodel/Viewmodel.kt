package com.quiz.viewmodel

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.quiz.repo.Model.Address
import com.quiz.repo.Model.Cart_Model
import com.quiz.repo.Model.Product_model
import com.quiz.repo.repository
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main

class Viewmodel(application: Application) : AndroidViewModel(application) {
    //  private val liveData:MutableLiveData<Product_model>
    val productitem = MutableLiveData<Product_model>()
    val CounterValue =MutableLiveData<Long>()
    val product_id = MutableLiveData<String>()
    val repository: repository = repository();
    val ArrayCartModel = MutableLiveData<ArrayList<Cart_Model>>();
    val UserId = MutableLiveData<String>()


    fun addcart(addtocart: Cart_Model) {

        viewModelScope.launch(Dispatchers.IO) {

            repository.addCartItems(addtocart)
        }

    }

    fun AuthenticateRegisterUser(email: String, name: String, v: View, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.AuthenticateRegisterUser(email, password, name, v)
        }
    }

    fun GetProductModel(productModel: Product_model, product_id: String) {
        productitem.value = productModel
        this.product_id.value = product_id

    }

    fun addQuantity(model: Cart_Model) {


    }
    fun getQuantityById(){
        var count:Long;
        val ID = product_id.value
        viewModelScope.launch(Dispatchers.IO){
          count  =repository.getQuantityById(ID!!)
            withContext(Main){
                CounterValue.value=count
            }
        }

    }

    fun addQuantityById(){
        val ID = product_id.value

viewModelScope.launch(Dispatchers.IO) {
    repository.addQuantityById(ID!!)
}


    }

    fun minusQuantityById(){
        val ID = product_id.value

        viewModelScope.launch(Dispatchers.IO) {
            repository.minusQuantityById(ID!!)
        }


    }
     fun removeCartProductById(){
        val ID = product_id.value
        viewModelScope.launch(Dispatchers.IO) {
            repository.removeCartProductById(ID!!)
        }

    }

    fun add_address(address: Address){

        val ID =UserId.value
        viewModelScope.launch(Dispatchers.IO) {

            repository.add_address(address)

        }
    }


}

