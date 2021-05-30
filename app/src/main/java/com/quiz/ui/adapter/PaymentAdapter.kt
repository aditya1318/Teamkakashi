package com.quiz.ui.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.quiz.ecommerce.R
import com.quiz.repo.Model.Cart_Model
import com.quiz.repo.Model.OrderDetail
import java.text.SimpleDateFormat
import java.util.*


class PaymentAdapter(options: FirestoreRecyclerOptions<OrderDetail>): FirestoreRecyclerAdapter<OrderDetail,
        PaymentAdapter.ViewHolder>(options) {


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
val price = itemView.findViewById<TextView>(R.id.price)
val items = itemView.findViewById<TextView>(R.id.items)
val date = itemView.findViewById<TextView>(R.id.date)
        val productList = itemView.findViewById<TextView>(R.id.productList)
        val maincard =itemView.findViewById<CardView>(R.id.maincard)
        val expandLayout = itemView.findViewById<RelativeLayout>(R.id.expandLayout)
        val paymentStatus = itemView.findViewById<TextView>(R.id.paymentStatus)

        fun bind(model: OrderDetail) {
            val expanded: Boolean = model.isExpanded!!
            // Set the visibility based on state
            // Set the visibility based on state
            expandLayout.setVisibility(if (expanded) View.VISIBLE else View.GONE)
            val builder = StringBuilder()
            val cart : List<Cart_Model> =model.cart_Model!!

            Log.d("adapter", "onBindViewHolder:${cart.size} ")
            cart.forEach {
                builder.append(it.product_name + " x " + it.quantity + "\n")
            }
            productList.text=builder.toString()

        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentAdapter.ViewHolder {
        return PaymentAdapter.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_payment, parent, false))


    }

    override fun onBindViewHolder(holder: PaymentAdapter.ViewHolder, position: Int, model: OrderDetail) {
holder.price.text = model.final_amount.toInt().toString()

if(!model.paymentStatus){
    holder.paymentStatus.text = "Failed"
    holder.paymentStatus.setTextColor(Color.RED)

}
        model.time?.let {
            val t = model.time.toString().toDate()
            holder.date.text=t
        }
holder.bind(model)

        holder.maincard.setOnClickListener{
            val expanded: Boolean = model.isExpanded!!
            // Change the state
            // Change the state
            model.isExpanded = !expanded
            // Notify the adapter that item has changed
            // Notify the adapter that item has changed
            notifyItemChanged(position)
        }






    }

 private fun String?.toDate() = this?.let { this.split(" ").let { it[2] + " "+getMonthNumber(it[1]) +" "+ it[5] }.toString() }?: throw NullPointerException("String is null")
    private fun getMonthNumber(monthName: String): Int {
        var monthNumber = 0
        val date: Date = SimpleDateFormat("MMM").parse(monthName) //put your month name here

        val cal: Calendar = Calendar.getInstance()
        cal.setTime(date)
        monthNumber = cal.get(Calendar.MONTH)
   return monthNumber+1

    }
}