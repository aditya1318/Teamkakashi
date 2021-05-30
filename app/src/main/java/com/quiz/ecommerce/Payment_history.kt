package com.quiz.ecommerce

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.quiz.repo.Model.OrderDetail
import com.quiz.ui.adapter.PaymentAdapter
import com.quiz.viewmodel.Viewmodel


class Payment_history : Fragment() {


    private lateinit var adapter: PaymentAdapter;
    lateinit  var recyclerView: RecyclerView
    val vm :Viewmodel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val view =  inflater.inflate(R.layout.fragment_payment_history, container, false)
recyclerView = view.findViewById(R.id.recyclerOrders)
        payment()



        return view
    }



    fun payment(){

        val userId = vm.getUser_id()

         val query: Query = FirebaseFirestore.getInstance().collection("USER").document(userId!!).collection(
                 "OrderHistory")
         val options = FirestoreRecyclerOptions.Builder<OrderDetail>()
                 .setQuery(query, OrderDetail::class.java)
                 .build();

        adapter = PaymentAdapter(options)
        (recyclerView.itemAnimator as SimpleItemAnimator?)!!.supportsChangeAnimations = false


        recyclerView.adapter = adapter;
        recyclerView.layoutManager = LinearLayoutManager(activity)

    }

    override fun onStart() {
        super.onStart()
       adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

}