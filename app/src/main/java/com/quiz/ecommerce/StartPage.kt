package com.quiz.ecommerce

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController


class StartPage : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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