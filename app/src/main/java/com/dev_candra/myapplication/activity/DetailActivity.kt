package com.dev_candra.myapplication.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dev_candra.myapplication.R
import com.dev_candra.myapplication.adapter.UserAdapter
import com.dev_candra.myapplication.adapter.ViewPager
import com.dev_candra.myapplication.viewmodel.DetailUserViewModel
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.toolbar.*

class DetailActivity : AppCompatActivity(){

    private lateinit var detailUserViewModel: DetailUserViewModel
    var login: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        containerImage.bringChildToFront(progressBar)

        val sectionsPagerAdapter = ViewPager(this, supportFragmentManager)
        viewPager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(viewPager)

        login = intent.getStringExtra(UserAdapter.EXTRA_LOGIN)
        login?.let { initToolbar(it) }
        detailUserViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DetailUserViewModel::class.java)
        login?.let { detailUserViewModel.setLogin(it) }
        detailUserViewModel.getUserDetail().observe(this, Observer { userDetail ->
            if (userDetail != null) {
                Glide.with(this)
                    .load(userDetail.avatarUrl)
                    .apply(RequestOptions().override(500,500))
                    .centerCrop()
                    .into(imgAvatarDetail)
                txtName.text = userDetail.name
                txtLokasi.text = userDetail.location
                txtId.text = userDetail.id.toString()
                txtFollowing.text = userDetail.following.toString()
                txtFollowers.text = userDetail.followers.toString()

                progressBar.visibility = View.INVISIBLE
                containerName.visibility = View.VISIBLE
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        if (viewPager.currentItem == 0) {
            super.onBackPressed()
        } else {
            viewPager.currentItem = viewPager.currentItem - 1
        }
    }

    private fun initToolbar(login: String){
        supportActionBar?.title = resources.getString(R.string.nama_developer)
        supportActionBar?.subtitle = login
    }

}
