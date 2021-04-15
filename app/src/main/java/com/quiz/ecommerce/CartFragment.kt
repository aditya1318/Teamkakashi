package com.quiz.ecommerce

import android.graphics.Color
import android.os.Bundle
import androidx.transition.Slide
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil.setContentView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.transition.MaterialContainerTransform
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import com.quiz.Model.Cart_model
import com.quiz.adapter.CartAdapter
import kotlinx.android.synthetic.main.fragment_cart.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class CartFragment : Fragment() {
    lateinit var cartFragment: CartFragment
    private lateinit var adapter: CartAdapter;
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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


    fun setUpRecyclerView() {
        val query: Query =
            FirebaseFirestore.getInstance().collection("Cart")
                .orderBy("product_rate", Query.Direction.ASCENDING)
        val options = FirestoreRecyclerOptions.Builder<Cart_model>()
            .setQuery(query, Cart_model::class.java)
            .build();
        adapter = CartAdapter(options)


        recyclerView.adapter = adapter;
        recyclerView.layoutManager = LinearLayoutManager(activity)
    }



}









