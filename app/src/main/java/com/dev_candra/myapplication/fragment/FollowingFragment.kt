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
import com.dev_candra.myapplication.adapter.FollowingAdapter
import com.dev_candra.myapplication.adapter.ViewPager
import com.dev_candra.myapplication.viewmodel.FollowingModel
import kotlinx.android.synthetic.main.fragment_following.*

class FollowingFragment : Fragment(){

    private lateinit var adatperFollowing: FollowingAdapter
    private lateinit var followingViewModel: FollowingModel
    private var login: String? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_following,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showLoading(true)


        arguments?.let {
            login = it.get(ViewPager.EXTRA_LOGIN) as String?
        }

        login?.let { setFollowingList(it) }

    }

    private fun showLoading(b: Boolean) {
        progressBarFollowing.visibility = if (b) View.VISIBLE else View.GONE
    }


    private fun setFollowingList(login: String){
        setLayoutManager()

        followingViewModel = ViewModelProvider(
            this,ViewModelProvider.NewInstanceFactory()
        ).get(FollowingModel:: class.java)

        followingViewModel.setFollowingModel(login)

        activity?.let {
            followingViewModel.getFollowing().observe(it, Observer {
                following ->
                if (following != null){
                    adatperFollowing.setData(following)
                }
                showLoading(false)
            })
        }
    }

    private fun setLayoutManager() {

        adatperFollowing = FollowingAdapter()
        adatperFollowing.notifyDataSetChanged()

        listFollowing.apply {
            layoutManager  = LinearLayoutManager(this.context)
            adapter = adatperFollowing
        }
    }

}