package com.quiz.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.quiz.ecommerce.R
import kotlinx.android.synthetic.main.fragment_setting.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Setting.newInstance] factory method to
 * create an instance of this fragment.
 */
class Setting : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private  val  firebase = FirebaseAuth.getInstance();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val view =inflater.inflate(R.layout.fragment_setting, container, false)

        view.findViewById<ImageView>(R.id.cart_image).setOnClickListener{
            it.findNavController().navigate(R.id.homeFragment)


        }

        view.findViewById<CardView>(R.id.cardView4).setOnClickListener {

            it.findNavController().navigate(R.id.action_SettingFragment_to_payment_history)
        }


        view.findViewById<ImageView>(R.id.logout).setOnClickListener {

            firebase.signOut()

            it.findNavController().navigate(R.id.startPage)

        }


        return view
    }


}