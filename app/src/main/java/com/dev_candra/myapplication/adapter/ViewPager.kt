package com.dev_candra.myapplication.adapter

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.dev_candra.myapplication.R
import com.dev_candra.myapplication.activity.DetailActivity
import com.dev_candra.myapplication.fragment.FollowerFragment
import com.dev_candra.myapplication.fragment.FollowingFragment

class ViewPager(private val context: Context,fm: FragmentManager):
FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

    companion object{
        const val EXTRA_LOGIN = "extra_login"
    }

    private val tabTittles = intArrayOf(R.string.txt1,R.string.txt2)

    override fun getItem(position: Int): Fragment {

        var fragment: Fragment? = null

        val login = (context as DetailActivity).login
        val bundle = Bundle()
        bundle.putString(EXTRA_LOGIN,login)

        when(position){
            0 -> {
                fragment = FollowerFragment()
                fragment.arguments = bundle
            }
            1 -> {
                fragment = FollowingFragment()
                fragment.arguments = bundle
            }
        }

        return fragment as Fragment
    }

    override fun getCount(): Int = tabTittles.size

    override fun getPageTitle(position: Int): CharSequence? {
        return context.getString(tabTittles[position])
    }
}