package com.quiz.repo

import android.app.Application
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.ktx.Firebase
import com.quiz.repo.Cart.CartRepoImpl
import com.quiz.repo.Model.*
import com.quiz.repo.UserData.UserDataImpl
import com.quiz.repo.addressRepo.AddressRepoImpl
import com.quiz.repo.appDatabase.LocalDatabase
import com.quiz.repo.appDatabase.UserDao
import com.quiz.repo.auth.AuthencationRepoImpl
import com.quiz.repo.auth.LoginRepoImpl
import com.quiz.repo.orderHistoryRepo.OrderHistoryRepoImpl
import com.quiz.repo.remoteUserDataRepo.RemoteUserSourceImpl
import com.quiz.repo.totalPrice.TotalPriceRepoImpl

import com.quiz.util.Event
import com.quiz.util.Resource
import kotlinx.coroutines.tasks.await


class repository(application: Application) {


    val authencationImpl = AuthencationRepoImpl()
    val loginRepoImpl = LoginRepoImpl()

    val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance();
    private var cartRepoImpl: CartRepoImpl = CartRepoImpl()
    var addressRepoImp : AddressRepoImpl = AddressRepoImpl()
    lateinit var UserDataImpl: UserDataImpl
    val RemoteUserSourceImpl = RemoteUserSourceImpl()

    val totalPriceRepoImpl = TotalPriceRepoImpl()
    val orderHistoryRepoImpl = OrderHistoryRepoImpl()



init {
    val db = LocalDatabase.getDatabase(application)
     UserDataImpl = db?.let { UserDataImpl(it.UserDao()) }!!
}

    suspend fun AuthenticateRegisterUser(email: String, password: String, name: String,number:String) : Resource<String>{

        return authencationImpl.AuthenticateRegisterUser(email,password, name,number)
    }

    suspend fun userLogin(email:String,password:String):Resource<Boolean>{
        return loginRepoImpl.userLogin(email,password)
    }



    suspend fun addCartItems(addToCart: Cart_Model,userID:String):Resource<Boolean> {
        return cartRepoImpl.addCartItems(addToCart,userID)
    }


    suspend fun getQuantityById(id: String,userID:String): Resource<Long> {
        Log.d("repo", "getQuantityById: $id")
        return cartRepoImpl.getQuantityById(id,userID)
    }

    suspend fun addQuantityById(id: String,userID:String) :Resource<Boolean>{
        return cartRepoImpl.addQuantityById(id,userID)
    }

    suspend fun minusQuantityById(id: String,userID:String):Resource<Boolean> {
        return cartRepoImpl.minusQuantityById(id, userID)
    }

    suspend fun removeCartProductById(id: String,userID:String):Resource<Boolean> {
        return cartRepoImpl.removeCartProductById(id, userID)
    }

    companion object {
        const val TAG = "Repo"
    }


    suspend fun add_address(address: Address,UserId : String): Resource<Boolean> {

        return addressRepoImp.add_address(address,UserId)

    }


    suspend fun delete_add(id: String,UserId : String): Resource<Boolean> {

        return addressRepoImp.delete_add(id,UserId)
    }


    suspend fun edit_add(id: String, address: Address,UserId : String): Resource<Boolean> {

        return addressRepoImp.edit_add(id, address,UserId)

    }

    fun getUser_id(): String? {
        return authencationImpl.getUserId()

    }



  suspend fun payment_details(id: String):Payment_Model{
         var paymentModel:Payment_Model?=null

         firebaseFirestore.collection("USER").document(id)

             .get().addOnSuccessListener {

                  paymentModel = Payment_Model(null,it.get("firstname").toString(),it.get("email").toString(),it.get("mobile").toString(),null)

             }

             .addOnFailureListener {

             }.await()
         return paymentModel!!
     }


    suspend fun InsertUserData(user: User){
        UserDataImpl.InsertUserData(user)
    }


    suspend fun getUserData():Resource<User>{
       return UserDataImpl.getUserData()
    }

    suspend fun getUserDetailRemote(userId:String):Resource<User>{
       return RemoteUserSourceImpl.getUserDataRemote(userId)
    }

    suspend fun deleteUserData(){
        UserDataImpl.DeleteUserData()
    }

suspend fun getTotalPrice(userID:String):Resource<Long>{
    return totalPriceRepoImpl.getTotalPrice(userID)
}
    suspend fun addPaymentHistory(userId:String,paymentModel: OrderDetail):Resource<Boolean>{
        return orderHistoryRepoImpl.paymentOrder(userId,paymentModel)
    }



    suspend fun getCartList(userId:String):Resource<List<Cart_Model>>{
        return  cartRepoImpl.getCartList(userId)
    }

    suspend fun removeCart(userId:String):Resource<Boolean>{
        return removeCart(userId)
    }

}