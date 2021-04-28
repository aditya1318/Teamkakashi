package com.quiz.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProviders
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

class CartFragment : Fragment(),CartItemClickListener {
    lateinit var cartFragment: CartFragment
    private lateinit var adapter: CartAdapter;
    lateinit var recyclerView: RecyclerView
    lateinit var vm: Viewmodel
    lateinit  var address : Button
    lateinit var userID :String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        vm = activity?.let {
            ViewModelProviders.of(it)[Viewmodel::class.java]
        } ?: throw Exception("Activity is null")
userID=vm.getUser_id()!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment



        val view = inflater.inflate(R.layout.fragment_cart, container, false)
               recyclerView = view.findViewById<RecyclerView>(R.id.cartrecyclerview)
        setUpRecyclerView()

        address = view.findViewById(R.id.btn_address)

        address.setOnClickListener{

           it.findNavController().navigate(R.id.action_cartFragment_to_address)

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
    }

    override fun onCartMinusClick(model: Cart_Model) {
        vm.minusQuantityByIdCart(model.product_id!!,userID)
    }
}









