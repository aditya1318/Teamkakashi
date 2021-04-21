package com.quiz.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.quiz.repo.Model.Address
import com.quiz.ecommerce.R

class AddressAdapter (options: FirestoreRecyclerOptions<Address>):FirestoreRecyclerAdapter<Address,AddressAdapter.ViewHolder>(options){

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {



        var tv_Address = itemView.findViewById<TextView>(R.id.address)
        var tv_delete = itemView.findViewById<TextView>(R.id.remove)
        var tv_edit = itemView.findViewById<TextView>(R.id.edit)

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return AddressAdapter.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.adddress_item_card, parent, false))


    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Address) {


        holder.tv_Address?.text = model.homeno + model.address+model.landmark +model.zipCode;

/*
        FirebaseFirestore.getInstance().collection("USER")
                .document().collection("address")
                .document().get()
*/


    }


}