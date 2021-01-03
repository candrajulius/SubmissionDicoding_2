package com.dev_candra.myapplication.method

import android.content.Context
import android.widget.Toast

class Code{
    companion object{
        fun errorMessage(statusCode : Int, error: Throwable?): String{
            return when(statusCode){
                401 -> "$statusCode : Bad Request"
                403 -> "$statusCode : Forbidden"
                404 -> "$statusCode : Not Found"
                else -> "$statusCode : ${error?.message}"
            }
        }

        fun showToast(context: Context, message: String){
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}