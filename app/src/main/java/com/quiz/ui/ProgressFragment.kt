package com.quiz.ui

import android.os.Bundle
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.quiz.ecommerce.R
import com.quiz.ecommerce.ui.Address_Dailog
import kotlinx.android.synthetic.main.activity_main.*

class ProgressFragment:Fragment(){
    companion object{
        const val TAG = "example_dialog"
        fun display(fragmentManager: FragmentManager?): Address_Dailog {
            val exampleDialog = Address_Dailog()
            if (fragmentManager != null) {
                exampleDialog.show(fragmentManager, TAG)
            }
            return exampleDialog
        }}
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
           val view = inflater.inflate(R.layout.progess_loading, container, false)
        return view;
    }
    override fun onStart() {
        super.onStart()


    }
}
