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
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.quiz.Model.Address
import com.quiz.ui.adapter.CartAdapter
import com.quiz.ecommerce.R
import com.quiz.viewmodel.Viewmodel
import kotlinx.android.synthetic.main.fragment_cart.view.*

class CartFragment : Fragment() {
    lateinit var cartFragment: CartFragment
    private lateinit var adapter: CartAdapter;
    lateinit var recyclerView: RecyclerView
    lateinit var vm: Viewmodel
    lateinit  var address : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        vm = activity?.let {
            ViewModelProviders.of(it)[Viewmodel::class.java]
        } ?: throw Exception("Activity is null")

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

        val query: Query =
            FirebaseFirestore.getInstance().collection("Cart")
                .orderBy("product_rate", Query.Direction.ASCENDING)
        val options = FirestoreRecyclerOptions.Builder<Address>()
            .setQuery(query, Address::class.java)
            .build();
        adapter = CartAdapter(options)


        recyclerView.adapter = adapter;
        recyclerView.layoutManager = LinearLayoutManager(activity)


    }
}









