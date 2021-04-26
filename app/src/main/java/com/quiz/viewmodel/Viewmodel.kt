package com.quiz.viewmodel

import android.app.Application
import android.nfc.Tag
import android.os.Build.ID
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.quiz.repo.Model.Address
import com.quiz.repo.Model.Cart_Model
import com.quiz.repo.Model.Payment_Model
import com.quiz.repo.Model.Product_model
import com.quiz.repo.repository
import com.quiz.util.Resource
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class Viewmodel(application: Application) : AndroidViewModel(application) {
    //  private val liveData:MutableLiveData<Product_model>
    val productitem = MutableLiveData<Product_model>()
    val CounterValue =MutableLiveData<Long>()
    val product_id = MutableLiveData<String>()
    val repository: repository = repository();
    val ArrayCartModel = MutableLiveData<ArrayList<Cart_Model>>();
    val UserId = MutableLiveData<String>()
    val address_id = MutableLiveData<String>()
    val addressmodel = MutableLiveData<Address>()
    var resultCode : Int? = null
    val liveDatapaymentmodel= MutableLiveData<Payment_Model>()

private  val _register = MutableStateFlow<CurrentEvent>(CurrentEvent.Empty)
val register : StateFlow<CurrentEvent> = _register
    sealed class CurrentEvent {

        class  Success(val resultText :String) : CurrentEvent()
        class  Failure(val errorText : String) : CurrentEvent()

        object Loading: CurrentEvent()
        object Empty : CurrentEvent()
    }




    fun addcart(addtocart: Cart_Model) {

        viewModelScope.launch(Dispatchers.IO) {

            repository.addCartItems(addtocart)
        }

    }



    //new one impl
    fun AuthenticateRegisterUser(email: String, name: String, password: String) {
        _register.value = CurrentEvent.Loading
        viewModelScope.launch(Dispatchers.IO) {
            when(val response= repository.AuthenticateRegisterUser(email, password, name)){

                is Resource.Success -> {_register.value =CurrentEvent.Success("Success")}
                is Resource.Error -> {_register.value = CurrentEvent.Failure(response.msg!!)

                }

            }
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




    fun getUser_id():String?{

        return repository.getUser_id()
    }


    fun delete_add(id: String) {

            viewModelScope.launch(Dispatchers.IO) {

                repository.delete_add(id)
            }

        }


    fun edit_add(id:String, address: Address){

        viewModelScope.launch(Dispatchers.IO) {

            repository.edit_add(id,address)

        }



    }

    fun edit_dailog(id: String,address: Address, resultCode:Int? ){

        address_id.value= id
        addressmodel.value = address
        this.resultCode = resultCode
    }

     fun payment_detail(){
         val id =getUser_id()
         var paymentmodel: Payment_Model? =null
         viewModelScope.launch(Dispatchers.IO) {
              paymentmodel=repository.payment_details(id!!)
             withContext(Main){
                 liveDatapaymentmodel.value =paymentmodel!!
             }

         }


    }


}

