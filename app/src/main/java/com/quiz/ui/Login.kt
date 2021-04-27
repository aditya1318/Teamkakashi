package com.quiz.ui

import android.content.ContentValues.TAG
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.quiz.ecommerce.R
import com.quiz.viewmodel.Viewmodel
import kotlinx.coroutines.flow.collect
import java.lang.Exception
import java.security.Provider


class Login : Fragment() {
    lateinit var et_email: TextInputEditText;
    lateinit var et_password: TextInputEditText;
    lateinit var vm :Viewmodel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)


        et_email = view.findViewById(R.id.et_email)

        et_password = view.findViewById(R.id.et_password)


        view.findViewById<Button>(R.id.StartpageLoginBtn).setOnClickListener {
            logInRegisteredUser(it)
        }
        lifecycleScope.launchWhenStarted {
            vm.Login.collect {event ->
             when(event){
                 is Viewmodel.CurrentEvent.Success -> {view.findNavController().navigate(R.id.homeFragment)}
                 is Viewmodel.CurrentEvent.Failure -> {Snackbar.make(view,event.errorText,Snackbar.LENGTH_LONG).show()}
                 is Viewmodel.CurrentEvent.Loading ->{}
                 else -> Unit
             }
            }
        }

        view.findViewById<TextView>(R.id.SignupBtn).setOnClickListener {
            it.findNavController().navigate(R.id.action_login_to_register)
        }

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm = activity?.let {
            ViewModelProviders.of(it)[Viewmodel::class.java]
        } ?: throw Exception("Activity is null")

    }

    private fun validateLoginDetails(): Boolean {
        return when {
            TextUtils.isEmpty(et_email.text.toString().trim { it <= ' ' }) -> {

                false
            }
            TextUtils.isEmpty(et_password.text.toString().trim { it <= ' ' }) -> {

                false
            }
            else -> {
                true
            }
        }
    }

    private fun logInRegisteredUser(v:View){

        if (validateLoginDetails()) {

            // Show the progress dialog.


            // Get the text from editText and trim the space
            val email = et_email.text.toString().trim { it <= ' ' }
            val password = et_password.text.toString().trim { it <= ' ' }

         vm.UserLoign(email, password)

        }

    }

}