package com.quiz.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.quiz.Model.Cart_model
import com.quiz.repo.Model.Product_model
import com.quiz.ecommerce.R
import com.quiz.repo.repository
import com.quiz.viewmodel.Viewmodel
import java.util.List.of


class productDetail : Fragment() {

    // lateinit var productdetail: productDetail
    lateinit var vm:Viewmodel
    val mFireStore = FirebaseFirestore.getInstance()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //  vm = ViewModelProvider.of(requireActivity())

        vm = activity?.let {
            ViewModelProviders.of(it)[Viewmodel::class.java]
        } ?: throw Exception("Activity is null")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view =inflater.inflate(R.layout.fragment_product_detail, container, false)
        view.findViewById<ImageView>(R.id.productDetailBackBtn).setOnClickListener{
            it.findNavController().navigate(R.id.homeFragment)

            /*   view.findViewById<Button>(R.id.product_detail_buy_btn).setOnClickListener {
                  Log.d("problem","message fuck")

               }*/

        }

        view.findViewById<Button>(R.id.product_detail_buy_btn).setOnClickListener { view ->
            Log.d("btnSetup", "Selected")
            Toast.makeText(activity,"button,",Toast.LENGTH_LONG).show();
            val cartModel:Cart_model = Cart_model("","","","")
            vm.addcart(cartModel)

        }


        return view
    }



}