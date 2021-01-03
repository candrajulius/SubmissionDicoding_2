package com.dev_candra.myapplication.method

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Message

class SosialMedia {
    companion object{
        fun btnFacebook(context: Context,message: String){
            val packageUrl = "com.facebook.katana"
            val url = "https://facebook.com/candra.julius.52"
            val installed: Boolean = installedApp(packageUrl,context)
            if (installed){
                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
            }else{
                Code.showToast(context,message)
            }
        }
        fun btnWhatsapp(context: Context,message: String,messageError: String){
            val url = "com.whatsapp"
            val intalled: Boolean = installedApp(url,context)
            if (intalled){
                val urlWhatsApp = "https://wa.me/6282311558341/?text=$message"
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(urlWhatsApp)
                context.startActivity(intent)
            }else{
                Code.showToast(context,messageError)
            }
        }
        fun btnInstagram(context: Context,message: String){
            val url = "com.instagram.android"
            val installed = installedApp(url,context)
            if (installed){
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("http://instagram.com/_u/candrajuliussinaga")
                context.startActivity(intent)
            }else{
                Code.showToast(context,message)
            }
        }

        private fun installedApp(url: String,context: Context): Boolean{
            val packageManager = context.packageManager
            return try {
                packageManager.getPackageInfo(url,PackageManager.GET_ACTIVITIES)
                true
            }catch (e: PackageManager.NameNotFoundException){
                false
            }
        }
    }
}