package com.quiz.ui

import android.app.Dialog
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
import com.quiz.repo.Model.User
import com.quiz.util.CommonUtils
import com.quiz.viewmodel.Viewmodel
import kotlinx.android.synthetic.main.fragment_start_page.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.Exception


class Login : Fragment() {
    lateinit var et_email: TextInputEditText;
    lateinit var et_password: TextInputEditText;
    lateinit var vm :Viewmodel
    private var loading: Dialog? = null
    lateinit var UserID:String
    private  val  firebase = FirebaseAuth.getInstance();



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
       val job=  lifecycleScope.launchWhenStarted {
            vm.loginEventFlow.collect { event ->

             when(event){
                 is Viewmodel.CurrentEvent.Success<*> -> {

                     UserID = vm.getUser_id()!!
                     vm.getUserDataRemote(UserID)

                     Log.d(TAG, "onCreateView: ${vm.getUserDataRemote(UserID)}")
                 }
                 is Viewmodel.CurrentEvent.Failure -> {hideLoading()
                     Snackbar.make(view,event.errorText,Snackbar.LENGTH_LONG).show()
                     StartpageLoginBtn.isEnabled = true

                 }
                 is Viewmodel.CurrentEvent.Loading ->{
                        showLoading()
                     StartpageLoginBtn.isEnabled = false
                 }
                 else -> Unit
             }
            }

        }


lifecycleScope.launch {
    vm.profileEventFlow.collect { event ->
        when (event) {

            is Viewmodel.CurrentEvent.Success<*> -> {
                Log.d(TAG, "onCreateView: ${event}")
                try {
                    vm.InsertUserData(event.result as User)
                    hideLoading()
                    Log.d("login", "onCreateView: ${event.result as User}")
                    view.findNavController().navigate(R.id.homeFragment)
                } catch (e: Exception) {
                    Log.d("logimn", "onCreateView: ${e.message}")
                }

            }
            is Viewmodel.CurrentEvent.Failure -> {
                Log.d("login", "onCreateView: error")
                firebase.signOut()
            }
            else ->{Log.d(TAG, "onCreateView: else")}
        }

    }
}

        view.findViewById<TextView>(R.id.SignupBtn).setOnClickListener {
            it.findNavController().navigate(R.id.action_login_to_register)
        }

        return view
    }

    override fun onStart() {
        super.onStart()


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
    private fun hideLoading(){
        loading?.let {
            if(it.isShowing)it.cancel()
        }

    }
    private fun showLoading(){
        hideLoading()
        loading = context?.let { CommonUtils.showLoadingDialog(it) }
    }
    companion object{
        const val TAG = "LoginPage"
    }


}