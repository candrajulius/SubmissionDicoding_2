package com.dev_candra.myapplication.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.dev_candra.myapplication.R
import com.dev_candra.myapplication.activity.WebView
import com.dev_candra.myapplication.method.loadImage
import com.dev_candra.myapplication.model.FollowModel
import kotlinx.android.synthetic.main.item_follower.view.*

class FollowerAdapter : RecyclerView.Adapter<FollowerAdapter.ViewHolder>(){

    private var followers = ArrayList<FollowModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowerAdapter.ViewHolder {
        // Your Code
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_follower,parent,false)
        )
    }

    fun setFollowerAdapter(listUser : ArrayList<FollowModel>){
        followers.clear()
        followers.addAll(listUser)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        // Your Code
        return followers.size
    }

    override fun onBindViewHolder(holder: FollowerAdapter.ViewHolder, position: Int) {
        // Your Code
        holder.bind(followers[position])
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(follower: FollowModel){
            with(itemView){
                imgFollower.loadImage(follower.gambarUrl)
                txtName.text = follower.userName
                materialTextView2.text = follower.idUser.toString()
                html_url.text = follower.url

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, WebView::class.java)
                    intent.putExtra(WebView.HTML_URL,follower.url)
                    intent.putExtra(WebView.USER_NAME,follower.userName)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

}