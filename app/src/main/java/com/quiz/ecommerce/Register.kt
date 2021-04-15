package com.quiz.ecommerce

import android.content.ContentValues.TAG
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.quiz.Model.User
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_register.*
import java.lang.Exception

class Register : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)



        view.findViewById<Button>(R.id.SignUpBtn).setOnClickListener{
           registerUser(it)
        }
        view.findViewById<TextView>(R.id.LoginBtn).setOnClickListener{
            it.findNavController().navigate(R.id.action_register_to_login)
        }
        view.findViewById<ImageView>(R.id.backBtn).setOnClickListener{
            it.findNavController().navigate(R.id.action_register_to_startPage)
        }


        return view
    }
    private fun validateRegisterDetails(): Boolean {
        return when {
            TextUtils.isEmpty(et_name.text.toString().trim { it <= ' ' }) -> {

                false
            }

            TextUtils.isEmpty(Email.text.toString().trim { it <= ' ' }) -> {

                false
            }

            TextUtils.isEmpty(phoneNumber.toString().trim { it <= ' ' }) -> {

                false
            }

            TextUtils.isEmpty(password.text.toString().trim { it <= ' ' }) -> {

                false
            }

            TextUtils.isEmpty(cpassword.text.toString().trim { it <= ' ' }) -> {



                false
            }

            password.text.toString().trim { it <= ' ' } != cpassword.text.toString()
                .trim { it <= ' ' } -> {


                false
            }




            else -> {
                true
            }
        }
    }
    private fun registerUser(v :View) {

        // Check with validate function if the entries are valid or not.
        if (validateRegisterDetails()) {
            val email: String = Email.text.toString().trim { it <= ' ' }
            val password: String = password.text.toString().trim { it <= ' ' }
            Log.d(TAG, "registerUser: ${email}${password}")
            // Create an instance and create a register a user with email and password.
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
                                et_name.text.toString().trim { it <= ' ' },
                                Email.text.toString().trim { it <= ' ' }
                            )
                            // Pass the required values in the constructor.
                            //     FirestoreClass().registerUser(this@RegisterActivity, user)
                            try {
                                v.findNavController().navigate(R.id.action_register_to_login)
                            } catch (e: Exception) {
                                Log.d(TAG, "registerUser: e ${e.toString()}")
                            }

                        } else {
                            Log.d(TAG, "registerUser: ${task.exception}")
                            // Hide the progress dialog

                        }
                    });
        }
    }

}