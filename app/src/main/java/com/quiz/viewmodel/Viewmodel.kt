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
import kotlinx.coroutines.Dispatchers.IO
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

    private val _Login = MutableStateFlow<CurrentEvent>(CurrentEvent.Empty)
    val Login : StateFlow<CurrentEvent> = _Login

    private  val _add_address = MutableStateFlow<CurrentEvent>(CurrentEvent.Empty)
    val add_address : StateFlow<CurrentEvent> = _add_address

    private  val _edit_add = MutableStateFlow<CurrentEvent>(CurrentEvent.Empty)
    val edit_add : StateFlow<CurrentEvent> = _edit_add

    private  val _delete_add = MutableStateFlow<CurrentEvent>(CurrentEvent.Empty)
    val delete_add : StateFlow<CurrentEvent> = _delete_add



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



    fun UserLoign(email:String,password:String){
        _Login.value =CurrentEvent.Loading
        viewModelScope.launch(IO){
            when(val response = repository.userLogin(email, password)){
                is Resource.Success -> {_Login.value = CurrentEvent.Success("success")}
                is Resource.Error -> {_Login.value = CurrentEvent.Failure(response.msg!!)}
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

    fun add_address(address: Address,UserId : String){

        _add_address.value = CurrentEvent.Loading
        viewModelScope.launch(Dispatchers.IO) {

            when(val response  =  repository.add_address(address,UserId)){

                is Resource.Success -> {_add_address.value = CurrentEvent.Success("succers")}
                is Resource.Error ->{_add_address.value = CurrentEvent.Failure(response.msg!!)}
            }
        }
    }





    fun delete_add(id: String,UserId : String) {


             _delete_add.value = CurrentEvent.Loading
             viewModelScope.launch(Dispatchers.IO) {

             when(val response  =  repository.delete_add(id,UserId)){

                is Resource.Success -> {_delete_add.value = CurrentEvent.Success("succers")}
                is Resource.Error ->{_delete_add.value = CurrentEvent.Failure(response.msg!!)}
            }
        }
    }


    fun edit_add(id:String, address: Address,UserId : String){

        _edit_add.value = CurrentEvent.Loading
        viewModelScope.launch(Dispatchers.IO) {

            when(val response  =  repository.edit_add(id,address,UserId)){

                is Resource.Success -> {_edit_add.value = CurrentEvent.Success("succers")}
                is Resource.Error ->{_edit_add.value = CurrentEvent.Failure(response.msg!!)}
            }
        }
    }

    fun edit_dailog(id: String,address: Address, resultCode:Int? ){

        address_id.value= id
        addressmodel.value = address
        this.resultCode = resultCode
    }







    fun getUser_id():String?{

        return repository.getUser_id()
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

