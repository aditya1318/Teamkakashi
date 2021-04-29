package com.quiz.ecommerce

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.quiz.repo.Model.Cart_Model
import com.quiz.repo.Model.Product_model
import com.quiz.ui.adapter.CartAdapter
import com.quiz.ui.adapter.CartpaymentAdapter
import com.quiz.ui.adapter.ProductAdapter
import com.quiz.viewmodel.Viewmodel
import com.razorpay.Checkout
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import kotlinx.android.synthetic.main.fragment_address2.*
import kotlinx.android.synthetic.main.fragment_address2.view.*
import org.json.JSONException
import org.json.JSONObject
import java.security.Provider

class PaymentGateway : AppCompatActivity() , PaymentResultWithDataListener{

        lateinit var payment_rate : String
        lateinit var  adapter : CartpaymentAdapter
        lateinit var   rc_cart : RecyclerView
        lateinit var phone_number: String
        lateinit var user_email : String
        lateinit var merchant_name : String
        lateinit var viewmodel: Viewmodel
        lateinit var id: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_gateway)


        rc_cart = findViewById(R.id.rc_paymentCart)


        viewmodel = this?.let {
            ViewModelProviders.of(it)[Viewmodel::class.java]

        } ?: throw Exception("Activity is null")

            id = viewmodel.getUser_id()!!
        setUpRecyclerView()


        viewmodel.payment_detail(id)


        viewmodel.liveDatapaymentmodel.observe(this, Observer {
            merchant_name = it.user_name!!
            user_email = it.user_email!!
            phone_number = it.user_phonenumber!!
        })



        var amount:String = "1000"


        var RoundAmount = Math.round(amount.toFloat() * 100)


        findViewById<CardView>(R.id.pay_card).setOnClickListener {
            Checkout.preload(this)
            val checkout = Checkout()
            checkout.setKeyID("rzp_test_Ae2Nf5hoWLy8gR")
            checkout.setImage(R.drawable.rzp_logo)


            payment_rate = "1000000"
            val options = JSONObject()
            try {
                options.put("name",merchant_name)
                options.put("description", "Reference No. #123456")
                options.put("theme.color", "#3399cc")
                options.put("currency", "INR")
                options.put("amount", payment_rate)
                options.put("prefill.contact", phone_number)
                options.put("prefill.email", user_email)
                options.put("send_sms_hash",true)
                checkout.open(this, options)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }


    }



    override fun onPaymentSuccess(p0: String?, p1: PaymentData?) {
        Toast.makeText(this,"Payment Success"+p0, Toast.LENGTH_LONG).show()
    }



    override fun onPaymentError(p0: Int, p1: String?, p2: PaymentData?) {
        Toast.makeText(this,"Failed"+p1, Toast.LENGTH_LONG).show()
    }


    fun setUpRecyclerView() {
        val userID = viewmodel.getUser_id()
        val query: Query =
                FirebaseFirestore.getInstance().collection("USER").document(userID!!).collection("Cart")
                        .orderBy("product_rate", Query.Direction.ASCENDING)
        val options = FirestoreRecyclerOptions.Builder<Cart_Model>()
                .setQuery(query, Cart_Model::class.java)
                .build();
        adapter = CartpaymentAdapter(options)


        rc_cart.adapter = adapter;
        rc_cart.layoutManager = LinearLayoutManager(this)

    }


    override fun onStart() {
        super.onStart()
        adapter.startListening();
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

}