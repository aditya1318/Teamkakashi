package com.quiz.ui.adapter

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
import com.google.firebase.firestore.FirebaseFirestore
import com.quiz.repo.Model.Product_model
import com.quiz.ProductClickListener
import com.quiz.ecommerce.R

class ProductAdapter(options: FirestoreRecyclerOptions<Product_model>, private val productClickListener: ProductClickListener) : FirestoreRecyclerAdapter<Product_model, ProductAdapter.ViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.productlist, parent, false))
    }



    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
var productCard = itemView.findViewById<MaterialCardView>(R.id.productCard)
        var product_img = itemView.findViewById<ImageView>(R.id.product_image);
        var product_name = itemView.findViewById<TextView>(R.id.product_txt);
        var product_des = itemView.findViewById<TextView>(R.id.product_des);
        var product_rate = itemView.findViewById<TextView>(R.id.product_rate);
       // var buy_btn = itemView.findViewById<Button>(R.id.buy);
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Product_model) {
        val url: String? = model.product_image;
        Glide.with(holder.product_img.context)
            .load(url)
                .placeholder(R.drawable.ic_microphone_black_48dp)
            .into(holder.product_img)


        holder.product_name?.text = model.product_name;
        holder.product_des?.text = model.product_des;
        holder.product_rate?.text = model.product_rate;
        FirebaseFirestore.getInstance().collection("Products").document()
                .get().addOnSuccessListener {
                    model.product_id=  it.id
                }


        holder.productCard.setOnClickListener{
            productClickListener.onProductClickListener(model,holder.productCard,snapshots.getSnapshot(position).id)
        }
    }

}