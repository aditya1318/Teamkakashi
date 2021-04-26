package com.quiz.ecommerce

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.quiz.repo.Model.Address
import com.quiz.ui.adapter.AddressAdapter
import com.quiz.ui.adapter.CartAdapter
import com.quiz.ui.adapter.PaymentAdapter


class Payment_history : Fragment() {


    private lateinit var adapter: PaymentAdapter;
    lateinit  var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view =  inflater.inflate(R.layout.fragment_payment_history, container, false)

        payment()



        return view
    }



    fun payment(){

        /* val userId = vm.getUser_id()

         val query: Query = FirebaseFirestore.getInstance().collection("USER").document(userId!!).collection(
                 "address"
         )//.orderBy("product_name", Query.Direction.ASCENDING);
         val options = FirestoreRecyclerOptions.Builder<Address>()
                 .setQuery(query, Address::class.java)
                 .build();*/


        //adapter = PaymentAdapter()

//    recyclerView.setAdapter(adapter);

        recyclerView.adapter = adapter;
        recyclerView.layoutManager = LinearLayoutManager(activity)

    }

//    override fun onStart() {
//        super.onStart()
//        adapter.startListening()
//    }
//
//    override fun onStop() {
//        super.onStop()
//        adapter.stopListening()
//    }

}