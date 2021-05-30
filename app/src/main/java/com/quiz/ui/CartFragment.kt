    package com.quiz.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.quiz.repo.Model.Address
import com.quiz.ui.adapter.CartAdapter
import com.quiz.ecommerce.R
import com.quiz.repo.Model.Cart_Model
import com.quiz.viewmodel.Viewmodel
import kotlinx.android.synthetic.main.fragment_cart.view.*
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext

    class CartFragment : Fragment(),CartItemClickListener {
    lateinit var cartFragment: CartFragment
    private lateinit var adapter: CartAdapter;
    lateinit var recyclerView: RecyclerView
     val vm: Viewmodel by activityViewModels()
    lateinit  var address : Button
    lateinit var userID :String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




userID=vm.getUser_id()!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
vm.getTotalPrice(userID)


        val view = inflater.inflate(R.layout.fragment_cart, container, false)
               recyclerView = view.findViewById<RecyclerView>(R.id.cartrecyclerview)
        setUpRecyclerView()
        lifecycleScope.launchWhenStarted {
            vm.CartAmountEventFlow.collect { event ->
                when(event){
                    is Viewmodel.CurrentEvent.Success<*>->{
                         vm.getTotalPrice(userID)
                    }
                    is Viewmodel.CurrentEvent.Failure -> {

                    }

                }

            }
        }


        lifecycleScope.launchWhenStarted {
            vm.getTotalPrice(userID).collect{
                withContext(Main){
                    view.totalprice.text =it.toInt().toString()
                }
            }

        }


        address = view.findViewById(R.id.btn_address)

        address.setOnClickListener{

           it.findNavController().navigate(R.id.action_cartFragment_to_address)

        }


        lifecycleScope.launchWhenStarted {
            vm.cartEventFlow.collect { event ->
                when(event){
                     is Viewmodel.CurrentEvent.Success<*> ->{
                         adapter.notifyDataSetChanged()
                     }
                    is Viewmodel.CurrentEvent.Failure -> {

                    }
                    is Viewmodel.CurrentEvent.Loading -> {

                    }
                }
            }
        }
        return view





    }
    override fun onStart() {
        super.onStart()
        adapter.startListening();
    }



    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }


    fun setUpRecyclerView(){
        val userID = vm.getUser_id()
        val query: Query =
            FirebaseFirestore.getInstance().collection("USER").document(userID!!).collection("Cart")
                .orderBy("product_rate", Query.Direction.ASCENDING)
        val options = FirestoreRecyclerOptions.Builder<Cart_Model>()
            .setQuery(query, Cart_Model::class.java)
            .build();
        adapter = CartAdapter(options,this)


        recyclerView.adapter = adapter;
        recyclerView.layoutManager = LinearLayoutManager(activity)


    }

    override fun onCartAddClick(model: Cart_Model) {
vm.addQuantityByIdCart(model.product_id!!,userID )
        vm.getTotalPrice(userID)
    }

    override fun onCartMinusClick(model: Cart_Model) {
        vm.minusQuantityByIdCart(model.product_id!!,userID)
        vm.getTotalPrice(userID)
    }
}









