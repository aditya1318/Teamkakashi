package com.quiz.repo

import android.content.ContentValues
import android.provider.ContactsContract
import android.util.Log
import android.view.View
import androidx.navigation.findNavController
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks.await
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import com.quiz.Model.Cart_model
import com.quiz.ecommerce.R
import com.quiz.repo.Model.Product_model
import com.quiz.repo.Model.User
import com.quiz.ui.adapter.CartAdapter
import com.quiz.ui.productDetail
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import java.util.*

class repository  {

    private lateinit var adapter: CartAdapter;

    val firebaseFirestore : FirebaseFirestore= FirebaseFirestore.getInstance();

    suspend fun addCartItems( addToCart: Cart_model)
    {
        firebaseFirestore.collection("Cart")

                .document().set(addToCart, SetOptions.merge())
                .await()
    }


suspend fun AuthenticateRegisterUser(email:String,password:String,name:String,v:View)
{
    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
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
                        email.trim{ it <= ' '}
                    )

                    // Pass the required values in the constructor.
                    //     FirestoreClass().registerUser(this@RegisterActivity, user)
                    try {
                        firebaseFirestore.collection("USER")
                            .document().set(user, SetOptions.merge())
GlobalScope.launch(Dispatchers.IO){
    withContext(Dispatchers.Main){
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
}