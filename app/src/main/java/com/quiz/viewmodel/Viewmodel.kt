package com.quiz.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.quiz.repo.Model.*
import com.quiz.repo.repository
import com.quiz.util.Resource
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

open class Viewmodel(application: Application) : AndroidViewModel(application) {
    val productitem = MutableLiveData<Product_model>()
    val CounterValue =MutableLiveData<Long>()
    val product_id = MutableLiveData<String>()
    val repository: repository = repository(application);
    val address_id = MutableLiveData<String>()
    val addressmodel = MutableLiveData<Address>()

    val ProfileData = MutableLiveData<User>()
    val finalAmount = MutableLiveData<Long>()
     private var deliveryAddress = MutableLiveData<Address>()
    var resultCode : Int? = null
    val liveDatapaymentmodel= MutableLiveData<Payment_Model>()

    private  val registerEventChannel = Channel<CurrentEvent>()
    val registerEventFlow = registerEventChannel.receiveAsFlow()


    private val loginEventChannel = Channel<CurrentEvent>()
    val loginEventFlow =loginEventChannel.receiveAsFlow()

    private val cartEventChannel  =Channel<CurrentEvent>()
    val cartEventFlow = cartEventChannel.receiveAsFlow()

    private  val addressEventChannel = Channel<CurrentEvent>()
    val addressEventFlow= addressEventChannel.receiveAsFlow()

    private val profileEventChannel = Channel<CurrentEvent>()
    val profileEventFlow = profileEventChannel.receiveAsFlow()

    private val CartAmountEventChannel = Channel<CurrentEvent>()
    val CartAmountEventFlow = CartAmountEventChannel.receiveAsFlow()



    sealed class CurrentEvent {

        class  Success<T>(val result :T) : CurrentEvent()
        class  Failure(val errorText : String) : CurrentEvent()

        object Loading: CurrentEvent()
        object Empty : CurrentEvent()
    }




    fun addcart(addtocart: Cart_Model,userID: String) =  viewModelScope.launch(Dispatchers.IO) {

           when( val response =repository.addCartItems(addtocart,userID)){
               is Resource.Success -> {addressEventChannel.send(CurrentEvent.Success(""))  }
               is Resource.Error -> {addressEventChannel.send(CurrentEvent.Failure(response.msg!!)) }
           }


    }



    //new one impl
    fun AuthenticateRegisterUser(email: String, name: String, password: String,number:String) =  viewModelScope.launch(Dispatchers.IO) {
        registerEventChannel.send(CurrentEvent.Loading)

            when(val response= repository.AuthenticateRegisterUser(email, password, name,number)){

                is Resource.Success -> {registerEventChannel.send(CurrentEvent.Success(""))


                }
                is Resource.Error -> {registerEventChannel.send(CurrentEvent.Failure(response.msg!!))

                }

            }

    }



    fun UserLoign(email:String,password:String)=viewModelScope.launch(IO){
loginEventChannel.send(CurrentEvent.Loading)

            when(val response = repository.userLogin(email, password)) {
    is Resource.Success -> { loginEventChannel.send(CurrentEvent.Success("succus")) }
    is Resource.Error -> {loginEventChannel.send(CurrentEvent.Failure(response.msg!!))}

            }

    }

    fun GetProductModel(productModel: Product_model, product_id: String) {
        productitem.value = productModel
        this.product_id.value = product_id

    }


    fun getQuantityById(userID :String)=   viewModelScope.launch(IO) {
        cartEventChannel.send(CurrentEvent.Loading)

        when (val response = product_id.value.let { repository.getQuantityById(it!!, userID) }) {
            is Resource.Success -> {
                cartEventChannel.send(CurrentEvent.Success(""))
                withContext(Main) {

                    CounterValue.value = response.data!!
                    Log.d("viewModel", "getQuantityById: ${CounterValue.value}")
                }


            }
            is Resource.Error -> {  cartEventChannel.send(CurrentEvent.Failure(response.msg!!))}


        }
    }

    fun addQuantityById(userID :String) =viewModelScope.launch(IO) {


        cartEventChannel.send(CurrentEvent.Loading)

            when (val response = product_id.value.let { repository.addQuantityById(it!!, userID) }) {
                is Resource.Success -> {
                    cartEventChannel.send(CurrentEvent.Success(""))
                }
                is Resource.Error -> {
                    cartEventChannel.send(CurrentEvent.Failure(response.msg!!))
                }
            }

    }

    fun minusQuantityById(userID :String) =viewModelScope.launch(IO){
      cartEventChannel.send(CurrentEvent.Loading)

            when(val response = product_id.value?.let { repository.minusQuantityById(it,userID) }){
                is Resource.Success -> {
                   cartEventChannel.send(CurrentEvent.Success(""))
                }
                is Resource.Error -> {cartEventChannel.send(CurrentEvent.Failure(response.msg!!))}
            }
        }



    fun removeCartProductById(userID :String)= viewModelScope.launch(IO) {
        cartEventChannel.send(Viewmodel.CurrentEvent.Loading)
        when (val response = product_id.value?.let { repository.removeCartProductById(it, userID) }) {
            is Resource.Success -> {
                cartEventChannel.send(CurrentEvent.Success(""))
            }
            is Resource.Error -> {
                cartEventChannel.send(CurrentEvent.Failure(response.msg!!))
            }
        }

    }




    fun add_address(address: Address,UserId : String)= viewModelScope.launch(IO) {

        addressEventChannel.send(Viewmodel.CurrentEvent.Loading)


            when(val response  =  repository.add_address(address,UserId)){

                is Resource.Success -> { addressEventChannel.send(CurrentEvent.Success(""))}
                is Resource.Error ->{   addressEventChannel.send(CurrentEvent.Failure(response.msg!!))}
            }

    }





    fun delete_add(id: String,UserId : String) = viewModelScope.launch(Dispatchers.IO) {


        addressEventChannel.send(Viewmodel.CurrentEvent.Loading)


            when(val response  =  repository.delete_add(id,UserId)){

                is Resource.Success -> {addressEventChannel.send(CurrentEvent.Success(""))}
                is Resource.Error ->{addressEventChannel.send(CurrentEvent.Failure(response.msg!!))}
            }

    }


    fun edit_add(id:String, address: Address,UserId : String) =  viewModelScope.launch(Dispatchers.IO) {
        addressEventChannel.send(Viewmodel.CurrentEvent.Loading)

            when (val response = repository.edit_add(id, address, UserId)) {

                is Resource.Success -> {
                    addressEventChannel.send(CurrentEvent.Success(""))
                }
                is Resource.Error -> {
                    addressEventChannel.send(CurrentEvent.Failure(response.msg!!))
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




    fun payment_detail(id : String){

        var paymentmodel: Payment_Model? =null
        viewModelScope.launch(Dispatchers.IO) {
             paymentmodel=repository.payment_details(id!!)
            withContext(Main){
                liveDatapaymentmodel.value =paymentmodel!!
            }

        }


   }


    fun addQuantityByIdCart(product_id: String,userID :String)= viewModelScope.launch(IO){


       cartEventChannel.send(CurrentEvent.Loading)

            when(val response =  repository.addQuantityById(product_id,userID) ){
                is Resource.Success -> {
                    cartEventChannel.send(CurrentEvent.Success(""))
                }
                is Resource.Error -> { cartEventChannel.send(CurrentEvent.Failure(response.msg!!))}
            }
        


    }

    fun minusQuantityByIdCart(product_id: String,userID :String)= viewModelScope.launch(IO){


        cartEventChannel.send(CurrentEvent.Loading)

            when(val response =  repository.minusQuantityById(product_id,userID) ){
                is Resource.Success -> {
                    cartEventChannel.send(CurrentEvent.Success(""))
                }
                is Resource.Error -> {cartEventChannel.send(CurrentEvent.Failure(response.msg!!))}
            }



    }
    fun setDeliveryAddress(address: Address){
       deliveryAddress.value=address
        Log.d("ViewModel", "setDeliveryAddress: ${deliveryAddress.value}")
    }
    fun getDeliveryAddress()= deliveryAddress


    fun InsertUserData(user:User)=viewModelScope.launch{
        repository.InsertUserData(user)
    }

    fun getUserData()=viewModelScope.launch {
        when (val response = repository.getUserData()) {
            is Resource.Success -> {
                Log.d("success", "getUserData: ${response.data!!}")
                profileEventChannel.send(CurrentEvent.Success(response.data))
            }
            is Resource.Error -> {
                Log.d("error", "getUserData: ")
                profileEventChannel.send(CurrentEvent.Failure(response.msg!!))
            }
        }
    }


    fun getUserDataRemote(userID: String) {
        Log.d(TAG, "getUserDataRemote: Called!")
        viewModelScope.launch {
            when (val response = repository.getUserDetailRemote(userID)) {
                is Resource.Success -> {

                    profileEventChannel.send(CurrentEvent.Success(response.data!!))
                    Log.d("success", "getUserDataRemote: ${response.data}")
                }
                is Resource.Error -> {
                    profileEventChannel.send(CurrentEvent.Failure(response.msg!!))
                    Log.d("error", "getUserDataRemote: ${response.msg}")
                }
                else -> {
                    Log.d(TAG, "getUserDataRemote: nothing happened ")}
            }
        }
    }

    fun deleteUserData(){
        viewModelScope.launch{
            repository.deleteUserData()
        }
    }

/*     fun getTotalPrice(userID:String)=viewModelScope.launch{
        when(val response =repository.getTotalPrice(userID)){
            is Resource.Success -> {
                CartAmountEventChannel.send(CurrentEvent.Success(response.data))
            }
            is Resource.Error -> {CartAmountEventChannel.send(CurrentEvent.Failure(response.msg!!))}
        }
    }*/


    fun getTotalPrice(userID:String):Flow<Long> = flow{
        when(val response =repository.getTotalPrice(userID)){
            is Resource.Success -> {
                CartAmountEventChannel.send(CurrentEvent.Success(null))
                emit(response.data!!)
            }
            is Resource.Error -> {CartAmountEventChannel.send(CurrentEvent.Failure(response.msg!!))}
        }
    }

    fun addPaymentHistory(userID: String,paymentModel: OrderDetail) =viewModelScope.launch {
        repository.addPaymentHistory(userID,paymentModel)
    }

    fun removeCart(userID:String) =viewModelScope.launch{
        when(val response =  repository.removeCart(userID) ){
            is Resource.Success -> {
                Log.d(TAG, "removeCart: ${response.toString()}")
            }
            is Resource.Error -> {
                cartEventChannel.send(CurrentEvent.Failure(response.msg!!))
                Log.d(TAG, "removeCart: ${response.msg!!}")
            }
        }
    }
    companion object{
        const val TAG = "ViewModel"
    }
}

