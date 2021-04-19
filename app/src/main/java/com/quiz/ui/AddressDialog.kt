package com.quiz.ecommerce.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.quiz.ecommerce.R


class Address_Dailog : DialogFragment() {
    private var toolbar: Toolbar? = null
    companion object{
    const val TAG = "example_dialog"
    fun display(fragmentManager: FragmentManager?): Address_Dailog {
        val exampleDialog = Address_Dailog()
        if (fragmentManager != null) {
            exampleDialog.show(fragmentManager, TAG)
        }
        return exampleDialog
    }}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)

        val view = inflater.inflate(R.layout.address_dailog, container, false)
        toolbar = view.findViewById(R.id.toolbar)


        return view
    }

    override fun onStart() {
        super.onStart()

        val dialog = dialog
        if (dialog != null) {


            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT

            dialog.getWindow()?.setLayout(width, height)

            dialog.getWindow()?.setWindowAnimations(R.style.AppTheme_Slide)

        }

    }
        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            toolbar?.setNavigationOnClickListener {

                it ->
                dismiss()
            }
            toolbar?.setTitle("Some Title");

        }

    }












