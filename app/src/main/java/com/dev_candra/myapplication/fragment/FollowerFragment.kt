package com.dev_candra.myapplication.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev_candra.myapplication.R
import com.dev_candra.myapplication.adapter.FollowerAdapter
import com.dev_candra.myapplication.adapter.ViewPager
import com.dev_candra.myapplication.viewmodel.FollowerModel
import kotlinx.android.synthetic.main.fragment_follower.*

class FollowerFragment : Fragment(){
    private lateinit var followerModel: FollowerModel
    private lateinit var adapterFollower: FollowerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_follower,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showLoading(true)
        updateViewModel()
    }

    private fun updateViewModel() {
        var login: String? = null

        arguments?.let {
            login = it.get(ViewPager.EXTRA_LOGIN) as String?
        }

        setLayoutManager()

        followerModel = ViewModelProvider(
            this,ViewModelProvider.NewInstanceFactory()
        ).get(FollowerModel::class.java)
        login?.let { followerModel.setFollowerModel(it) }

        activity?.let {
            followerModel.getFollowers().observe(it, Observer {
                followers ->
                if (followers != null){
                    adapterFollower.setFollowerAdapter(followers)
                }
                showLoading(false)
            })
        }
    }

    private fun setLayoutManager() {
        adapterFollower = FollowerAdapter()
        adapterFollower.notifyDataSetChanged()

        listFollowers.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = adapterFollower
        }
    }

    private fun showLoading(benar: Boolean){
        progressBarFollowers.visibility = if (benar) View.VISIBLE else View.GONE
    }
}