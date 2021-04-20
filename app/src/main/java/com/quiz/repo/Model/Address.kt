package com.quiz.repo.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * A data model class for Address item with required fields.
 */
@Parcelize
data class Address(

    val name: String = "",
    val mobileNumber: String = "",
    val address: String = "",
    val zipCode: String = "",
    val landmark: String = "",
    val homeno: String = "",

   // var id: String = "",

) : Parcelable