package com.quiz.ecommerce

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.navigation.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.quiz.Model.Cart_model
import com.quiz.Model.Product_model
import kotlinx.android.synthetic.main.fragment_product_detail.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class productDetail : Fragment() {

   // lateinit var productdetail: productDetail
    val mFireStore = FirebaseFirestore.getInstance()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

            var product_model : Product_model = Product_model("",
                    "","","")
            addCartItems(activity =  productDetail(),product_model )
        }


        return view
    }

    fun addCartItems(activity: productDetail, addToCart: Product_model) {

            Log.d("problem ","where are you ?")


                mFireStore.collection("Cart")
                .document().set(addToCart, SetOptions.merge())


    }



}