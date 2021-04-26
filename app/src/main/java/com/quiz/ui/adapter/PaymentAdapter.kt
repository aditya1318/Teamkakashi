package com.quiz.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.card.MaterialCardView
import com.quiz.ecommerce.R
import com.quiz.repo.Model.Payment_Model
import org.w3c.dom.Text

class PaymentAdapter (options: FirestoreRecyclerOptions<Payment_Model>): FirestoreRecyclerAdapter<Payment_Model,
        PaymentAdapter.ViewHolder>(options) {


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {



    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentAdapter.ViewHolder {
        return PaymentAdapter.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_payment, parent, false))


    }

    override fun onBindViewHolder(holder: PaymentAdapter.ViewHolder, position: Int, model: Payment_Model) {



    }


}