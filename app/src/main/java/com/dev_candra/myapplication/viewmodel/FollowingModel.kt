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
import java.nio.channels.AsynchronousChannel

class FollowingModel: ViewModel(){
    companion object{
        private const val TAG = "FollowingModel"
    }

    private var listFollowing = MutableLiveData<ArrayList<FollowModel>>()

    fun setFollowingModel(following: String){
        val users = ArrayList<FollowModel>()
        val client = AsyncHttpClient()
        val tokenGithub = "token abbd757b3142cbb00909973eb4db2900c88b97cc"
        val url = "https://api.github.com/users/$following/following"
        client.addHeader("Authorization",tokenGithub)
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

                    for (i in 0 until (jsonArray?.length() ?: 0)) {
                        val jsonObj = jsonArray!!.getJSONObject(i)
                        users.add(
                            FollowModel(
                                idUser = jsonObj.getInt("id"),
                                userName = jsonObj.getString("login"),
                                gambarUrl = jsonObj.getString("avatar_url"),
                                url = jsonObj.getString("html_url")
                            )
                        )
                        listFollowing.postValue(users)
                    }
                } catch (e: Exception) {
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
                // Your Code
                val errorMessage = Code.errorMessage(statusCode, error)

                Log.d(TAG, "Status: $errorMessage")

            }

        })
    }

    fun getFollowing(): LiveData<ArrayList<FollowModel>>{
        return listFollowing
    }
}