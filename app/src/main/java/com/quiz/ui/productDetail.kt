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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.quiz.repo.Model.Address
import com.quiz.repo.Model.Product_model
import com.quiz.ecommerce.R
import com.quiz.repo.Model.Cart_Model
import com.quiz.viewmodel.Viewmodel
import kotlinx.android.synthetic.main.fragment_product_detail.*
import kotlinx.android.synthetic.main.fragment_product_detail.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext


class productDetail : Fragment() {

    // lateinit var productdetail: productDetail
    lateinit var vm:Viewmodel
    lateinit var id:String
    lateinit var model:Product_model
    var Counter: Long? =null
    lateinit var userId :String
    val mFireStore = FirebaseFirestore.getInstance()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        vm = activity?.let {
            ViewModelProviders.of(it)[Viewmodel::class.java]
        } ?: throw Exception("Activity is null")

        userId = vm.getUser_id()!!
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

        vm.getQuantityById(userId)
        Log.d("asd", "onCreateView: asdasd")
        vm.CounterValue.observe(viewLifecycleOwner, {
            view.countertext.text = it.toString()
            Log.d("kuchbhi", "onCreateView: $it")
            Counter = it
            if (it!! > 0) {
                product_detail_buy_btn.visibility = View.GONE
                product_detail_buy_btn.isEnabled = false

            }
            else if (Counter!!.toInt() == 0) {
                product_detail_buy_btn.visibility = View.VISIBLE
                product_detail_buy_btn.isEnabled = true


            }
        })



        vm.productitem.observe(viewLifecycleOwner, Observer<Product_model>{
            model=it
            view.Product_name.text = it.product_name
            view.product_des.text = it.product_des
            Glide.with(view.img1.context)
                    .load(it.product_image)
                    .placeholder(R.drawable.ic_microphone_black_48dp)
                    .into(view.img1)
            view.product_rate.text = it.product_rate
            Glide.with(view.imageprofile.context)
                    .load(it.product_image)
                    .placeholder(R.drawable.ic_microphone_black_48dp)
                    .into(view.imageprofile)
            view.product_rate.text = it.product_rate
        })

        vm.product_id.observe(viewLifecycleOwner, Observer {
            id=it

        })



        lifecycleScope.launchWhenStarted {
            vm.cartEventFlow.collect { event ->
                when(event){
                    is Viewmodel.CurrentEvent.Success<*> -> {
                        counteradd.isEnabled= true
                        counterminus.isEnabled =true
                    }
                    is Viewmodel.CurrentEvent.Failure ->{
                        counteradd.isEnabled= true
                        counterminus.isEnabled =true
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
                        counteradd.isEnabled= false
                        counterminus.isEnabled =false
                    }
                    else -> Unit
                }
            }
        }

        view.counteradd.setOnClickListener {
            if(countertext.text=="0"){
                val cartModel :Cart_Model=Cart_Model(id,model.product_image,model.product_name,model.product_rate,1)
                val c: String = view.countertext.text as String
                view.countertext.text = (c.toInt() + 1).toString()
                vm.addcart(cartModel,userId)
            }else {
                vm.addQuantityById(userId)
                val c: String = view.countertext.text as String
                view.countertext.text = (c.toInt() + 1).toString()
            }
            if (view.countertext.text.toString().toInt() > 0) {
                product_detail_buy_btn.visibility = View.GONE
                product_detail_buy_btn.isEnabled = false

            }

        }

        view.counterminus.setOnClickListener {
            if (view.countertext.text.toString().toInt() > 1) {
                vm.minusQuantityById(userId)
                val c: String = view.countertext.text as String
                view.countertext.text = (c.toInt() - 1).toString()
            }else{
                if(view.countertext.text.toString().toInt() == 1){
                    val c: String = view.countertext.text as String
                    view.countertext.text = (c.toInt() - 1).toString()
                }
                vm.removeCartProductById(userId)
            }
             if (view.countertext.text.toString().toInt() == 0) {
                product_detail_buy_btn.visibility = View.VISIBLE
                product_detail_buy_btn.isEnabled = true

            }
        }



        view.product_detail_buy_btn.setOnClickListener {
view.product_detail_buy_btn.isEnabled = false
            val cartModel: Cart_Model = Cart_Model(id, model.product_image, model.product_name, model.product_rate, 1)
            vm.addcart(cartModel,userId)
            vm.getQuantityById(userId)

        }



        return view
    }



}