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
import androidx.navigation.findNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.quiz.repo.Model.User
import com.quiz.ecommerce.R
import com.quiz.viewmodel.Viewmodel
import kotlinx.android.synthetic.main.fragment_register.*
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
            registerUser(it)
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

            TextUtils.isEmpty(phoneNumber.toString().trim { it <= ' ' })  && phoneNumber.text!!.length ==10 -> { Toast.makeText(activity,"falses",Toast.LENGTH_SHORT)
                false }

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
           vm.AuthenticateRegisterUser(Email.text.toString(),et_name.text.toString(),v,password.text.toString())

        }
    }


}