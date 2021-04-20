package com.quiz.repo.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class User(
        val id: String = "",
        val firstName: String = "",
        val email: String = "",
        val mobile: String = "",
        val image: String = "",


        val profileCompleted: Int = 0
) : Parcelable


