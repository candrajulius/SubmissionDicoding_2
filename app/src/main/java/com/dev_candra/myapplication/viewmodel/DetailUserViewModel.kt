package com.dev_candra.myapplication.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev_candra.myapplication.model.DetailUser
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.lang.Exception

class DetailUserViewModel: ViewModel(){
    companion object{
        private const val TAG = "DetailUserViewModel"
    }

    private var userDetail = MutableLiveData<DetailUser>()

    fun setLogin(login: String){
        val client = AsyncHttpClient()
        val tokenGithub = "token abbd757b3142cbb00909973eb4db2900c88b97cc"
        client.addHeader("Authorization",tokenGithub)
        client.addHeader("User-Agent","request")
        val url = "https://api.github.com/users/$login"

        client.get(url,object: AsyncHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                // Your Code
                try {
                    val result = responseBody?.let { String(it) }
                    val res = result?.let { JSONObject(result) }

                    if (res != null) {
                        val user = DetailUser(
                            id = res.getInt("id"),
                            avatarUrl = res.getString("avatar_url"),
                            name = res.getString("name"),
                            location = res.getString("location"),
                            followers = res.getInt("followers"),
                            following = res.getInt("following")
                        )

                        userDetail.postValue(user)
                    }
            }catch (e: Exception){
                    e.printStackTrace()
                    e.message?.let { Log.d(TAG, it) }
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                TODO("Not yet implemented")
            }

        })
    }

    fun getUserDetail(): LiveData<DetailUser>{
        return userDetail
    }
}