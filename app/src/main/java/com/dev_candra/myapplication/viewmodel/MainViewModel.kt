package com.dev_candra.myapplication.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev_candra.myapplication.method.Code
import com.dev_candra.myapplication.model.User
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.lang.Exception

class MainViewModel: ViewModel(){
    companion object{
        private const val TAG = "mainViewModel"
    }
    
    private val listUser = MutableLiveData<ArrayList<User>>()

    fun setUser(userName: String){
        val users = ArrayList<User>()

        val client = AsyncHttpClient()
        val githubUser = "token abbd757b3142cbb00909973eb4db2900c88b97cc"
        client.addHeader("Authorization",githubUser)
        client.addHeader("User-Agent","request")
        val url = "https://api.github.com/search/users?q=$userName"
        client.get(url,object : AsyncHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                // Your Code
                try {
                    val result = responseBody?.let { String(it) }
                    val res = result?.let { JSONObject(result) }
                    val jsonArray = res?.getJSONArray("items")
                    if (jsonArray != null) {
                        for (i in 0 until jsonArray.length()){
                            val jsonObject = jsonArray.getJSONObject(i)
                            users.add(
                                User(
                                    id = jsonObject.getInt("id"),
                                    login = jsonObject.getString("login"),
                                    avatarUrl = jsonObject.getString("avatar_url")
                                )
                            )
                        }
                        listUser.postValue(users)
                    }

                }catch (e: Exception){
                    e.printStackTrace()
                    Log.d(TAG, "onSuccess: ${e.message}")
                }

            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                // Your Code
                val erroCode = Code.errorMessage(statusCode,error)
                Log.d(TAG, "onFailure: ${erroCode.toString()}")
            }

        })
    }

    fun getUser(): LiveData<ArrayList<User>>{
        return listUser
    }

}