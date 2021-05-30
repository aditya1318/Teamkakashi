package com.quiz.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.bumptech.glide.Glide

import com.google.firebase.auth.FirebaseAuth
import com.quiz.ecommerce.R
import com.quiz.repo.Model.User
import com.quiz.util.Resource
import com.quiz.viewmodel.Viewmodel
import kotlinx.android.synthetic.main.fragment_product_detail.view.*
import kotlinx.android.synthetic.main.fragment_setting.*
import kotlinx.android.synthetic.main.fragment_setting.view.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class Setting : Fragment() {
lateinit var vm:Viewmodel

    private  val  firebase = FirebaseAuth.getInstance();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm = activity?.let {
            ViewModelProviders.of(it)[Viewmodel::class.java]
        } ?: throw Exception("Activity is null")
        lifecycleScope.launch {
            vm.getUserData()
            vm.profileEventFlow.collect { event ->
                when (event) {
                    is Viewmodel.CurrentEvent.Success<*> -> {
                        event.apply {
                            val user = this.result as User
                            vm.ProfileData.value = user

                            Log.d("setting", "onCreateView: ${user.toString()}")
                        }

                    }
                    is Viewmodel.CurrentEvent.Failure -> {
                        Log.d("setting", "onCreateView: ${event.errorText}")
                    }
                    else -> {
                        Unit
                    }
                }
            }
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val view =inflater.inflate(R.layout.fragment_setting, container, false)




        vm.ProfileData.observe(viewLifecycleOwner, Observer {user ->
            view.UserName.text = user.firstName
            view.UserEmail.text = user.email
            Glide.with(view.UserPhoto.context)
                    .load(user.image)
                    .placeholder(R.drawable.ic_microphone_black_48dp)
                    .into(view.UserPhoto)
        })


        view.findViewById<ImageView>(R.id.cart_image).setOnClickListener{
            it.findNavController().navigate(R.id.homeFragment)


        }

        view.findViewById<CardView>(R.id.cardView4).setOnClickListener {

            it.findNavController().navigate(R.id.action_SettingFragment_to_payment_history)
        }


        view.findViewById<ImageView>(R.id.logout).setOnClickListener {

            firebase.signOut()
            vm.deleteUserData()
            it.findNavController().navigate(R.id.action_SettingFragment_to_startPage)



        }


        return view
    }


}