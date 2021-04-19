package com.quiz.ecommerce

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.quiz.ecommerce.ui.Address_Dailog
import kotlinx.android.synthetic.main.fragment_address2.view.*
import kotlinx.android.synthetic.main.fragment_cart.view.*


class address : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view =inflater.inflate(R.layout.fragment_address2, container, false)

        view.add_Address.setOnClickListener {

            openDialog()

        }
        return view

    }
    private fun openDialog() {

        Address_Dailog.display( fragmentManager)

    }}
