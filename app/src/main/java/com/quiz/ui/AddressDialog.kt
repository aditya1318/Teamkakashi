package com.quiz.ecommerce.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.quiz.ecommerce.R
import com.quiz.ecommerce.address
import com.quiz.repo.Model.Address
import com.quiz.viewmodel.Viewmodel
import kotlinx.android.synthetic.main.address_dailog.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class Address_Dailog : DialogFragment() {
    private var toolbar: Toolbar? = null
    lateinit var vm: Viewmodel
      var a : String? = null

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

        vm = activity?.let {
            ViewModelProviders.of(it)[Viewmodel::class.java]
        } ?: throw Exception("Activity is null")


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)


        val view = inflater.inflate(R.layout.address_dailog, container, false)
        toolbar = view.findViewById(R.id.toolbar)

        if(vm.resultCode != null){

            vm.address_id.observe(viewLifecycleOwner, Observer {
                a = it

            })

            vm.addressmodel.observe(viewLifecycleOwner, Observer {

                view.full_name.setText(it.name)
                view.phone_no.setText(it.mobileNumber)
                view.address.setText(it.address)
                view.Pin.setText(it.zipCode)
                view.Landmark.setText(it.landmark)
                view.house_no.setText(it.homeno)

            })
        }




        view.save_btn.setOnClickListener {

            val address: Address = Address(view.full_name.text.toString(),view.phone_no.text.toString(),view.address.text.toString(),view.Pin.text.toString(),
                                            view.Landmark.text.toString(),view.house_no.text.toString())


            if(vm.resultCode != null){

                vm.edit_add(a!!,address)

            }else{
                vm.add_address(address)
            }


            dismiss()
        }

        return view
    }

    override fun onStop() {
        super.onStop()

    }

    override fun onDestroy() {
        super.onDestroy()
        vm.resultCode = null
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












