package com.quiz.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.quiz.Model.Address
import com.quiz.ecommerce.R

class AddressAdapter(options:FirestoreRecyclerOptions<Address>) : FirestoreRecyclerAdapter<Address,

        CartAdapter.ViewHolder>(options)  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapter.ViewHolder {

      return CartAdapter.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.adddress_item_card, parent, false)
      )
    }

    override fun onBindViewHolder(
        holder: CartAdapter.ViewHolder,
        position: Int,
        model: Address
    ) {

    }
}