package com.quiz.ecommerce

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.quiz.ecommerce.ui.Address_Dailog
import com.quiz.repo.Model.Address
import com.quiz.repo.Model.Product_model
import com.quiz.ui.adapter.AddressAdapter
import com.quiz.ui.adapter.ProductAdapter
import com.quiz.viewmodel.Viewmodel
import kotlinx.android.synthetic.main.fragment_address2.view.*
import kotlinx.android.synthetic.main.fragment_cart.view.*


class address : Fragment() {


    private lateinit var adapter: AddressAdapter;
    lateinit var recyclerView: RecyclerView
    lateinit var vm: Viewmodel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vm = activity?.let {
            ViewModelProviders.of(it)[Viewmodel::class.java]
        } ?: throw Exception("Activity is null")


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

    }


    fun address(){

        val query: Query = FirebaseFirestore.getInstance().collection("USER").document().collection("Address")//.orderBy("product_name", Query.Direction.ASCENDING);
        val options = FirestoreRecyclerOptions.Builder<Address>()

                .setQuery(query, Address::class.java)
                .build();
        adapter = AddressAdapter(options)

//    recyclerView.setAdapter(adapter);
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter;


    }


}
