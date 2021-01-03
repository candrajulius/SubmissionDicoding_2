package com.dev_candra.myapplication.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev_candra.myapplication.R
import com.dev_candra.myapplication.activity.DetailActivity
import com.dev_candra.myapplication.method.loadImage
import com.dev_candra.myapplication.model.DetailUser
import com.dev_candra.myapplication.model.User
import kotlinx.android.synthetic.main.item_layout.view.*

class UserAdapter(private val context: Context, private val listItem : ArrayList<User>): RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    companion object{
        const val EXTRA_LOGIN ="extra_login"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.ViewHolder {
        // Your Code
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout,parent,false)
        )
    }

    fun setData(listItem1: ArrayList<User>){
        listItem.clear()
        listItem.addAll(listItem1)
        notifyDataSetChanged()
    }

    fun clearData(){
        listItem.clear()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
       // Your Code
        return listItem.size
    }

    override fun onBindViewHolder(holder: UserAdapter.ViewHolder, position: Int) {
       // Your Code
        holder.bind(listItem[position])
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(user: User){
            with(itemView){
                imgAvatarDetail1.loadImage(user.avatarUrl)
                materialTextView.text = user.login
                userId.text =  user.id.toString()
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(UserAdapter.EXTRA_LOGIN,user.login)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }
}