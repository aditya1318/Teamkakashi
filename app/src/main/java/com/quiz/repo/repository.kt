package com.quiz.repo

import android.content.ContentValues
import android.util.Log
import android.view.View
import androidx.navigation.findNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.quiz.ecommerce.R
import com.quiz.ecommerce.address
import com.quiz.repo.Model.Address
import com.quiz.repo.Model.Cart_Model
import com.quiz.repo.Model.User
import com.quiz.ui.adapter.CartAdapter
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class repository {

    var userID = FirebaseAuth.getInstance().currentUser?.uid
    val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance();


    suspend fun addCartItems(addToCart: Cart_Model) {


        if (userID != null) {
            firebaseFirestore.collection("USER").document(userID!!)
                    .collection("Cart").document().set(addToCart, SetOptions.merge())
                    .await()
        }
    }


    suspend fun AuthenticateRegisterUser(email: String, password: String, name: String, v: View) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                        OnCompleteListener<AuthResult> { task ->
                            // If the registration is successfully done
                            if (task.isSuccessful) {
                                // Firebase registered user
                                val firebaseUser: FirebaseUser = task.result!!.user!!
                                // Instance of User data model class.
                                val user = User(
                                        firebaseUser.uid,
                                        name.trim { it <= ' ' },
                                        email.trim { it <= ' ' }
                                )
                                if (userID == null) {
                                    userID = user.id
                                }
                                // Pass the required values in the constructor.
                                //     FirestoreClass().registerUser(this@RegisterActivity, user)
                                try {
                                    firebaseFirestore.collection("USER")
                                            .document(user.id).set(user, SetOptions.merge())
                                    GlobalScope.launch(Dispatchers.IO) {
                                        withContext(Dispatchers.Main) {
                                            v.findNavController().navigate(R.id.action_register_to_login)
                                        }

                                    }


                                } catch (e: Exception) {
                                    Log.d(ContentValues.TAG, "registerUser: e ${e.toString()}")
                                }

                            } else {
                                Log.d(ContentValues.TAG, "registerUser: ${task.exception}")
                                // Hide the progress dialog

                            }
                        });
    }

    suspend fun getQuantityById(id: String): Long {

        var count: Long? = null;
        if (userID != null) {
            firebaseFirestore.collection("USER").document(userID!!).collection("Cart").whereEqualTo("product_id", id)
                    .get().addOnSuccessListener { it ->
                        it.forEach { i ->
                            count = i.get("quantity") as Long?
                            Log.d(TAG, "getQuantityById: ${count.toString()}")

                            Log.d(TAG, "getQuantityById: ${i.getString("product_id").toString()}")
                        }
                    }.addOnFailureListener {
                        Log.d(TAG, "getQuantityById: ${it.message}")
                    }.await()
        }

        return count ?: 0
    }

    suspend fun addQuantityById(id: String) {
        var count: Long? = null;
        if (userID != null) {
            firebaseFirestore.collection("USER").document(userID!!).collection("Cart").whereEqualTo("product_id", id)
                    .get().addOnSuccessListener {

                        it.forEach { i ->
                            count = i.get("quantity") as Long?
                            val num: Int = count!!.toInt() + 1
                            i.reference.update("quantity", num)
                        }
                    }
        }
    }

    suspend fun minusQuantityById(id: String) {
        var count: Long? = null;
        if (userID != null) {
            firebaseFirestore.collection("USER").document(userID!!).collection("Cart").whereEqualTo("product_id", id)
                    .get().addOnSuccessListener {

                        it.forEach { i ->
                            count = i.get("quantity") as Long?
                            val num: Int = count!!.toInt() - 1
                            i.reference.update("quantity", num)
                        }
                    }
        }
    }

    suspend fun removeCartProductById(id: String) {
        if (userID != null) {
            firebaseFirestore.collection("USER").document(userID!!).collection("Cart").whereEqualTo("product_id", id)
                    .get().addOnSuccessListener {
                        it.forEach { i ->
                            firebaseFirestore.collection("USER").document(userID!!).collection("Cart").document(i.reference.id).delete()
                                    .addOnSuccessListener {

                                    }.addOnFailureListener {

                                    }
                        }
                    }
        }
    }

    companion object {
        const val TAG = "Repo"
    }


    suspend fun add_address(address: Address) {

        if (userID != null) {
            firebaseFirestore.collection("USER").document(userID!!)
                    .collection("address")
                    .document().set(address, SetOptions.merge())

            Log.d(TAG, "msg:" + userID)
        }



    }


    fun getUser_id(): String? {

        return userID
    }


    suspend fun delete_add(id: String) {

        if (userID != null) {
            firebaseFirestore.collection("USER").document(userID!!)
                    .collection("address")
                    .document(id).delete()

        }

    }


    suspend fun edit_add(id:String , address: Address){


        firebaseFirestore.collection("USER").document(userID!!)
                .collection("address")
                .document(id).set(address, SetOptions.merge())

    }
}