package com.quiz.repo.appDatabase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserTable (
        @PrimaryKey val uid: Int,
        val firstName: String?,
        val email: String?,
        val image: String?,
        val mobile_number: String?
        )