package com.quiz.ui

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.android.gms.auth.api.Auth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.quiz.ecommerce.R


class StartPage : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    val CurrentUser = FirebaseAuth.getInstance().currentUser
if (CurrentUser!=null){

}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =inflater.inflate(R.layout.fragment_start_page, container, false)
         view.findViewById<Button>(R.id.StartpageLoginBtn).setOnClickListener{
             Log.d(TAG, "onCreateView: " + "clicked")
             try {
                       it.findNavController().navigate(R.id.login)
             }catch (e:Exception){
                 Log.d(TAG, "onCreateView:  ${e.toString()}")
             }

         }

        view.findViewById<TextView>(R.id.SignupBtn).setOnClickListener{
            it.findNavController().navigate(R.id.action_startPage_to_register)
        }
        // Inflate the layout for this fragment
        return view
    }


}