package com.dev_candra.myapplication.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev_candra.myapplication.method.Code
import com.dev_candra.myapplication.model.FollowModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import java.lang.Exception

class FollowerModel : ViewModel(){

    companion object{
        private const val TAG = "followerModel"
    }

    private var listFollowers = MutableLiveData<ArrayList<FollowModel>>()

    fun setFollowerModel(follower: String){
        val users = ArrayList<FollowModel>()
        val client = AsyncHttpClient()
        val tokeGithub = "token abbd757b3142cbb00909973eb4db2900c88b97cc"
        val url = "https://api.github.com/users/$follower/followers"
        client.addHeader("Authorization",tokeGithub)
        client.addHeader("User-Agent","request")
        client.get(url,object: AsyncHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                // Your Code
                try {
                    val result = responseBody?.let { String(it) }
                    val jsonArray = result?.let { JSONArray(result) }

                    if (jsonArray != null) {
                        for (i in 0 until jsonArray.length()){
                            val jsonObject = jsonArray.getJSONObject(i)
                            users.add(
                                FollowModel(
                                    idUser = jsonObject.getInt("id"),
                                    userName = jsonObject.getString("login"),
                                    gambarUrl = jsonObject.getString("avatar_url"),
                                    url = jsonObject.getString("html_url")
                                )
                            )
                            listFollowers.postValue(users)
                        }
                    }
                }catch (e: Exception){
                    e.printStackTrace()
                    e.message.let { Log.d(TAG, "onSuccess: $it") }
                }

            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                // Your Code
                val errorMessage = Code.errorMessage(statusCode,error)
                Log.d(TAG, "onFailure: ${errorMessage.toString()}")
            }

        })
    }

    fun getFollowers(): LiveData<ArrayList<FollowModel>>{
        return listFollowers
    }
}