package com.quiz.ui

import android.content.ContentValues.TAG
import android.os.Bundle
import android.os.SharedMemory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.card.MaterialCardView
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.quiz.repo.Model.Product_model
import com.quiz.ProductClickListener
import com.quiz.ui.adapter.ProductAdapter
import com.quiz.ecommerce.R
import com.quiz.viewmodel.Viewmodel


class HomeFragment : Fragment(), ProductClickListener {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance();
    private val collectionReference: CollectionReference = db.collection("Products");
    private lateinit var adapter: ProductAdapter;
    lateinit var recyclerView: RecyclerView
    lateinit var vm: Viewmodel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm = activity?.let {
            ViewModelProviders.of(it)[Viewmodel::class.java]
        } ?: throw Exception("Activity is null")

    }

    override fun onStart() {
        super.onStart()
        adapter.startListening();
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)

    /*    db.collection("Products")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        Log.d(TAG, "${document.id} => ${document.data}")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "Error getting documents: ", exception)
                }*/
        recyclerView = view.findViewById<RecyclerView>(R.id.Rcview1)

        return view;
    }

    fun setUpRecyclerView() {
        val query: Query = FirebaseFirestore.getInstance().collection("Products")//.orderBy("product_name", Query.Direction.ASCENDING);
        val options = FirestoreRecyclerOptions.Builder<Product_model>()

                .setQuery(query, Product_model::class.java)
                .build();
        adapter = ProductAdapter(options, this)

//    recyclerView.setAdapter(adapter);
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter;


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
    }



    override fun onProductClickListener(model: Product_model, holder: MaterialCardView) {
    vm.GetProductModel(model)

        holder.findNavController().navigate(R.id.action_homeFragment_to_productDetail)

    }


}