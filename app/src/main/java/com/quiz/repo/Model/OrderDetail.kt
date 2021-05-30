package com.quiz.repo.Model

import android.os.Parcelable
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.android.parcel.Parcelize
import java.util.*


data class OrderDetail(
        val orderId :String="",

        val cart_Model:List<Cart_Model>?=null,
        val final_amount: Long=0,
        val payment_mode:String="",
        val OrderStatus :String?="",
        val paymentStatus :Boolean =false,
        @ServerTimestamp
        val time: Date? = null,

        var isExpanded:Boolean?=false
)
