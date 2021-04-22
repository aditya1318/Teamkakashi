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
import com.quiz.ecommerce.address

class AddressAdapter (options: FirestoreRecyclerOptions<Address>,private val Onclickdelete : Onclickdelete , private val onclickedit: Onclickedit):FirestoreRecyclerAdapter<Address,AddressAdapter.ViewHolder>(options){

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {



        var tv_Address = itemView.findViewById<TextView>(R.id.Address)
        var tv_delete = itemView.findViewById<TextView>(R.id.remove)
        var tv_edit = itemView.findViewById<TextView>(R.id.edit)

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return AddressAdapter.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.adddress_item_card, parent, false))


    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Address) {

        val add : String = model.homeno+" ,"+ model.address+" "+ model.landmark+" "+ model.zipCode

        holder.tv_Address.text = add ;

/*
        FirebaseFirestore.getInstance().collection("USER")
                .document().collection("address")
                .document().get()
*/

        val id : String = snapshots.getSnapshot(position).reference.id

        holder.tv_delete.setOnClickListener {

                    Onclickdelete.Onclick(id)


        }


        holder.tv_edit.setOnClickListener {

            onclickedit.Onclick2(id,model)
        }

    }



}

 interface Onclickdelete {

     fun Onclick(id:String)

 }

interface  Onclickedit{


    fun Onclick2(id: String,address: Address)

}
