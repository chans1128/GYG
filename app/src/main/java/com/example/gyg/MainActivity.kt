package com.example.gyg

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import com.example.gyg.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configureBottomNavigation()
    }

    private fun configureBottomNavigation() {
        binding.vpAcMainFragPager.adapter = MainFragmentStatePagerAdapter(supportFragmentManager, 5)
        binding.tlAcMainBottomMenu.setupWithViewPager(binding.vpAcMainFragPager)

        val bottomNaviLayout: View = this.layoutInflater.inflate(R.layout.bottom_navigation_tab, null, false)

        binding.tlAcMainBottomMenu.getTabAt(0)!!.customView = bottomNaviLayout.findViewById(R.id.btn_bottom_navi_shopping_tab) as RelativeLayout
        binding.tlAcMainBottomMenu.getTabAt(1)!!.customView = bottomNaviLayout.findViewById(R.id.btn_bottom_navi_community_tab) as RelativeLayout
        binding.tlAcMainBottomMenu.getTabAt(2)!!.customView = bottomNaviLayout.findViewById(R.id.btn_bottom_navi_home_tab) as RelativeLayout
        binding.tlAcMainBottomMenu.getTabAt(3)!!.customView = bottomNaviLayout.findViewById(R.id.btn_bottom_navi_ranking_tab) as RelativeLayout
        binding.tlAcMainBottomMenu.getTabAt(4)!!.customView = bottomNaviLayout.findViewById(R.id.btn_bottom_navi_myinfo_tab) as RelativeLayout
        binding.tlAcMainBottomMenu.getTabAt(2)?.select()

    }

    fun selectTab(i: Int) {
        binding.vpAcMainFragPager.adapter = MainFragmentStatePagerAdapter(supportFragmentManager, 5)
        binding.tlAcMainBottomMenu.setupWithViewPager(binding.vpAcMainFragPager)

        val bottomNaviLayout: View = this.layoutInflater.inflate(R.layout.bottom_navigation_tab, null, false)

        binding.tlAcMainBottomMenu.getTabAt(0)!!.customView = bottomNaviLayout.findViewById(R.id.btn_bottom_navi_shopping_tab) as RelativeLayout
        binding.tlAcMainBottomMenu.getTabAt(1)!!.customView = bottomNaviLayout.findViewById(R.id.btn_bottom_navi_community_tab) as RelativeLayout
        binding.tlAcMainBottomMenu.getTabAt(2)!!.customView = bottomNaviLayout.findViewById(R.id.btn_bottom_navi_home_tab) as RelativeLayout
        binding.tlAcMainBottomMenu.getTabAt(3)!!.customView = bottomNaviLayout.findViewById(R.id.btn_bottom_navi_ranking_tab) as RelativeLayout
        binding.tlAcMainBottomMenu.getTabAt(4)!!.customView = bottomNaviLayout.findViewById(R.id.btn_bottom_navi_myinfo_tab) as RelativeLayout
        binding.tlAcMainBottomMenu.getTabAt(i)?.select()   }
}