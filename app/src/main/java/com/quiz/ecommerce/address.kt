package com.quiz.ecommerce

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.quiz.ecommerce.ui.Address_Dailog
import com.quiz.repo.Model.Address
import com.quiz.ui.adapter.AddressAdapter
import com.quiz.ui.adapter.Onclickdelete
import com.quiz.ui.adapter.Onclickedit
import com.quiz.viewmodel.Viewmodel
import com.razorpay.Checkout
import com.razorpay.PaymentData
import com.razorpay.PaymentResultListener
import com.razorpay.PaymentResultWithDataListener
import kotlinx.android.synthetic.main.fragment_address2.view.*
import org.json.JSONException
import org.json.JSONObject


class address : Fragment() , Onclickdelete ,Onclickedit,PaymentResultWithDataListener{

   // private val TAG: String = address::class.java.getSimpleName()
    private lateinit var adapter: AddressAdapter;
    lateinit var recyclerView: RecyclerView
    lateinit var vm: Viewmodel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vm = activity?.let {
            ViewModelProviders.of(it)[Viewmodel::class.java]
        } ?: throw Exception("Activity is null")


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var amount:String = "1000"
        var RoundAmount = Math.round(amount.toFloat() * 100)
        val view =inflater.inflate(R.layout.fragment_address2, container, false)
        recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)

        view.pay_card.setOnClickListener {
            Checkout.preload(activity)
            val checkout = Checkout()
            checkout.setKeyID("rzp_test_Ae2Nf5hoWLy8gR")
            checkout.setImage(R.drawable.rzp_logo)

            val options = JSONObject()
            try {
                options.put("name", "jams Infotech")
                options.put("description", "Reference No. #123456")
                options.put("theme.color", "#3399cc")
                options.put("currency", "INR")
                options.put("amount", RoundAmount)
                options.put("prefill.contact", "8488093696")
                options.put("prefill.email", "ceo@jams.tech")
                options.put("send_sms_hash",true)
                checkout.open(activity, options)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }


        view.add_Address.setOnClickListener {

            openDialog()


        }

        address()


        return view

    }
    private fun openDialog() {

        Address_Dailog.display(fragmentManager)

    }


    fun address(){

        val userId = vm.getUser_id()

        val query: Query = FirebaseFirestore.getInstance().collection("USER").document(userId!!).collection(
            "address"
        )//.orderBy("product_name", Query.Direction.ASCENDING);
        val options = FirestoreRecyclerOptions.Builder<Address>()
                .setQuery(query, Address::class.java)
                .build();


        adapter = AddressAdapter(options, this, this)

//    recyclerView.setAdapter(adapter);

        recyclerView.adapter = adapter;
        recyclerView.layoutManager = LinearLayoutManager(activity)

    }


    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    override fun Onclick(id: String) {

        vm.delete_add(id)

    }

    override fun Onclick2(id: String, address: Address) {

        vm.edit_dailog(id, address, 1)
        openDialog()
    }

    override fun onPaymentSuccess(p0: String?, p1: PaymentData?) {
        Toast.makeText(activity,"Payment Success"+p0,Toast.LENGTH_LONG).show()
    }

    override fun onPaymentError(p0: Int, p1: String?, p2: PaymentData?) {
        Toast.makeText(activity,"Failed"+p1,Toast.LENGTH_LONG).show()
    }

    /* override fun onPaymentSuccess(p0: String?) {
         val builder = AlertDialog.Builder(activity)
         builder.setTitle("Payment Id")
         builder.setMessage(p0)
         builder.show()
     }

     override fun onPaymentError(p0: Int, p1: String?) {
         Toast.makeText(activity, p1, Toast.LENGTH_LONG).show()
     }*/


}
