package com.example.gyg

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.gyg.Community.CommunityFragment
import com.example.gyg.Home.HomeFragment
import com.example.gyg.MyInfo.MyInfoFragment
import com.example.gyg.Ranking.RankingFragment
import com.example.gyg.Shopping.ShoppingFragment


class MainFragmentStatePagerAdapter(fm : FragmentManager , val fragmentCount : Int) : FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        when(position){
            0 -> return ShoppingFragment()
            1 -> return CommunityFragment()
            2 -> return HomeFragment()
            3 -> return RankingFragment()
            4 -> return MyInfoFragment()
            else -> return HomeFragment()
        }
    }

    override fun getCount(): Int = fragmentCount // 자바에서는 { return fragmentCount }

}