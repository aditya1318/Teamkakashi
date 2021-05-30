package com.quiz.repo.Model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize



@Entity
data class User(
        @PrimaryKey
        val id: String = "",
        val firstName: String = "",
        val email: String = "",
        val mobile: String = "",
        val image: String = "",
        val profileCompleted: Int = 0


)


