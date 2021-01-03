package com.dev_candra.myapplication.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev_candra.myapplication.R
import com.dev_candra.myapplication.activity.WebView
import com.dev_candra.myapplication.method.loadImage
import com.dev_candra.myapplication.model.FollowModel
import kotlinx.android.synthetic.main.item_follower.view.*

class FollowingAdapter: RecyclerView.Adapter<FollowingAdapter.ViewHolder>(){

   private val followingList = ArrayList<FollowModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowingAdapter.ViewHolder {
        // Your Code
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_follower,parent,false)
        )
    }

    fun setData(listUser: ArrayList<FollowModel>){
        followingList.clear()
        followingList.addAll(listUser)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
       // Your Code
        return followingList.size
    }

    override fun onBindViewHolder(holder: FollowingAdapter.ViewHolder, position: Int) {
       // Your Code
        holder.bind(followingList[position])
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind (following: FollowModel){
            with(itemView){
                imgFollower.loadImage(following.gambarUrl)
                txtName.text = following.userName
                materialTextView2.text = following.idUser.toString()
                html_url.text = following.url
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, WebView::class.java)
                    intent.putExtra(WebView.HTML_URL, following.url)
                    intent.putExtra(WebView.USER_NAME,following.userName)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

}