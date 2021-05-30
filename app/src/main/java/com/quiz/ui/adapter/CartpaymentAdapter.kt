package com.quiz.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.card.MaterialCardView
import com.quiz.repo.Model.Address
import com.quiz.ecommerce.R
import com.quiz.repo.Model.Cart_Model
import com.quiz.repo.Model.Payment_Model
import com.quiz.ui.CartItemClickListener


class CartpaymentAdapter(options: FirestoreRecyclerOptions<Cart_Model>): FirestoreRecyclerAdapter<Cart_Model,
        CartpaymentAdapter.ViewHolder>(options)  {





    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        var cart_card = itemView.findViewById<MaterialCardView>(R.id.cart_card);
        var Product_name = itemView.findViewById<TextView>(R.id.cart_pname);
        //   var Product_price = itemView.findViewById<TextView>(R.id.product_price);
        var Product_img = itemView.findViewById<ImageView>(R.id.cart_image);
        var add_btn = itemView.findViewById<ImageView>(R.id.Add_btn)
        var Counter = itemView.findViewById<TextView>(R.id.Counter)
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.cart_item, parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Cart_Model) {
        Log.d("Bind:", "Mirage");
        val url: String? = model.product_image;
        Glide.with(holder.Product_name.context)
                .load(url)
                .placeholder(R.drawable.ic_baseline_add_24)
                .into(holder.Product_img)

        holder.Counter.text = model.quantity!!.toInt().toString()

        holder.Product_name?.text = model.product_name.toString();
        //  holder.Product_price?.text = model.product_rate.toString();
        holder.cart_card


    }


}



