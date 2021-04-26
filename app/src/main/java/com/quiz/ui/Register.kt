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
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.quiz.repo.Model.User
import com.quiz.ecommerce.R
import com.quiz.viewmodel.Viewmodel
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.util.EnumSet.of

class Register : Fragment() {

    lateinit var vm: Viewmodel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm = activity?.let {
            ViewModelProviders.of(it)[Viewmodel::class.java]
        } ?: throw Exception("Activity is null")

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)



        view.findViewById<Button>(R.id.SignUpBtn).setOnClickListener {
            registerUser()
        }
        lifecycleScope.launchWhenStarted {
            vm.register.collect { event ->
                when(event){
                    is Viewmodel.CurrentEvent.Success ->{
                        withContext(Main) {
                            view.findNavController().navigate(R.id.action_register_to_login)
                        }
                    }
                    is Viewmodel.CurrentEvent.Failure ->{
                        val snackBar = view?.let {
                            Snackbar.make(
                                    it, event.errorText,
                                    Snackbar.LENGTH_LONG
                            ).setAction("Action", null)
                        }
                        if (snackBar != null) {
                            snackBar.show()
                        }

                    }
                    is Viewmodel.CurrentEvent.Loading -> {

                      //for jeet

                    }
                    else -> Unit
                }
            }
        }
        view.findViewById<TextView>(R.id.LoginBtn).setOnClickListener {
            it.findNavController().navigate(R.id.action_register_to_login)
        }
        view.findViewById<ImageView>(R.id.backBtn).setOnClickListener {
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

                val snackBar = view?.let {
                    Snackbar.make(
                        it, "Please  check your both password ",
                        Snackbar.LENGTH_LONG
                    ).setAction("Action", null)
                }
                if (snackBar != null) {
                    snackBar.show()
                }
                false
            }

            phoneNumber.text.toString().trim().length < 10  && phoneNumber.text.toString().trim().length < 10 ->{

                  val snackBar = view?.let {
                      Snackbar.make(
                          it, "please check your number ",
                          Snackbar.LENGTH_LONG
                      ).setAction("Action", null)
                  }
                  if (snackBar != null) {
                      snackBar.show()
                  }

                  false
              }



            else -> {
                true
            }
        }
    }

    private fun registerUser() {

        // Check with validate function if the entries are valid or not.
        if (validateRegisterDetails()) {
           vm.AuthenticateRegisterUser(Email.text.toString(),et_name.text.toString(),password.text.toString())

        }
    }


}