package com.quiz.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.quiz.ecommerce.R
import com.quiz.repo.Model.Address

class AddressAdapter(options: FirestoreRecyclerOptions<Address>, private val Onclickdelete: Onclickdelete, private val onclickedit: Onclickedit,private val OnclickAddress:OnclickAddress):FirestoreRecyclerAdapter<Address, AddressAdapter.ViewHolder>(options){

    var Lastclickposition : Int = -1


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {



        var tv_Address = itemView.findViewById<TextView>(R.id.Address)
        var tv_delete = itemView.findViewById<TextView>(R.id.remove)
        var tv_edit = itemView.findViewById<TextView>(R.id.edit)
        var star = itemView.findViewById<ImageView>(R.id.star)
        var card = itemView.findViewById<CardView>(R.id.Card)
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return AddressAdapter.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.adddress_item_card, parent, false))


    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Address) {


      if(Lastclickposition == position){
            holder.star.isVisible = true
        }else {
            holder.star.isVisible = false
        }


        val add : String = model.homeno+" ,"+ model.address+" "+ model.landmark+" "+ model.zipCode

        holder.tv_Address.text = add ;


        holder.card.setOnClickListener {

Lastclickposition = position

            notifyDataSetChanged()

OnclickAddress.onClickAddress(model)
        }

        val id : String = snapshots.getSnapshot(position).reference.id

        holder.tv_delete.setOnClickListener {

                    Onclickdelete.Onclick(id)


        }


        holder.tv_edit.setOnClickListener {

            onclickedit.Onclick2(id, model)
        }

    }



}

 interface Onclickdelete {

     fun Onclick(id: String)

 }

interface  Onclickedit{


    fun Onclick2(id: String, address: Address)

}

interface  OnclickAddress{
    fun onClickAddress(address: Address)
}
