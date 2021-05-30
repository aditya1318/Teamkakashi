package com.quiz.util

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.view.ViewGroup
import androidx.core.graphics.drawable.toDrawable
import com.quiz.ecommerce.R

object CommonUtils {

    fun showLoadingDialog(context: Context): Dialog {
        val progressDialog = Dialog(context)
        progressDialog.let {
            it.show()
            it.window?.setBackgroundDrawable(Color.WHITE.toDrawable())
            it.setContentView(R.layout.progess_loading)
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT

            it.window?.setLayout(width, height)
            it.setCancelable(false)
            it.setCanceledOnTouchOutside(true)
            return it
        }
    }
}
