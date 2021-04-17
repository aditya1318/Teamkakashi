package com.quiz.ecommerce

import android.content.ContentValues.TAG
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.quiz.Model.User
import com.quiz.UseCases.UserDetailImpli
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Register : Fragment() {

    private val instance: Register? = null
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
    private fun registerUser(v: View) {

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
                                        Email.text.toString().trim { it <= ' ' },
                                        phoneNumber.text.toString().trim() { it <= ' ' }

                                )
                                // Pass the required values in the constructor.
                                CoroutineScope(Dispatchers.IO).launch {
                                    UserDetailImpli().createUserDetail(, user)
                                }
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
    fun getInstance(): Register? {
        return instance
    }
    fun userRegistrationSuccess() {


        Toast.makeText(
                activity,
                "success",
                Toast.LENGTH_SHORT
        ).show()


        /**
         * Here the new user registered is automatically signed-in so we just sign-out the user from firebase
         * and send him to Intro Screen for Sign-In
         */
        FirebaseAuth.getInstance().signOut()
        // Finish the Register Screen

    }

}