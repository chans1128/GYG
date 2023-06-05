package com.example.gyg.Home

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.gyg.R
import com.google.firebase.database.DatabaseReference
import com.example.gyg.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class HomeFragment : Fragment() {
    private lateinit var myRef: DatabaseReference
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater , container: ViewGroup? ,
        savedInstanceState: Bundle?

    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        initAdapter()
        initData()
        return binding.root
    }
    private fun initAdapter() {
        val fragmentList = listOf(HomePlantFragment(),HomeMissionFragment(),HomeDailyTipFragment())
        val adapter = HomePageAdapter(this)
        val viewPager: ViewPager2 = binding.vpSample
        adapter.fragments.addAll(fragmentList)
        viewPager.adapter = adapter
//        viewPager.setClipToPadding(false)
//        viewPager.setPadding(10, 0, 10, 0)
//        viewPager.setPageTransformer(new MarginPageTransformer(1500));
//        val pageTransformer = ViewPager2.PageTransformer { page, position ->
//            val offset = resources.getDimensionPixelOffset(R.dimen.viewpager_page_offset)
//            val scaleFactor = resources.getDimension(R.dimen.viewpager_scale_factor)
//
//            val scale = (1 - Math.abs(position * scaleFactor)).coerceAtLeast(0.75f)
//            page.scaleX = scale
//            page.scaleY = scale
//
//            if (position != 0f) {
//                page.translationX = if (position < 0) (-offset).toFloat() else offset.toFloat()
//            }
//        }
//
//        viewPager.setPageTransformer(pageTransformer)
    }
    private fun initData() {
        val user = FirebaseAuth.getInstance().currentUser
        val uid = user?.uid
        val today: LocalDateTime = LocalDateTime.now()
        val todayDate = today.format(DateTimeFormatter.ofPattern("yyyyMMdd",Locale("ko", "KR")))
        binding.gygLogo.setImageResource(R.drawable.gyg_logo2)

        myRef = FirebaseDatabase.getInstance().getReference("User").child(uid.toString())
        myRef.addValueEventListener(object : ValueEventListener {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onDataChange(snapshot: DataSnapshot) {
                var signupDate = snapshot.child("UserInfo").child("user_signup_date").value.toString()
                var startDate = SimpleDateFormat("yyyyMMdd",Locale("ko", "KR")).parse(signupDate)
                var endDate = SimpleDateFormat("yyyyMMdd",Locale("ko", "KR")).parse(todayDate.toString())
                var days =(endDate.time - startDate.time) / (24 * 60 * 60 * 1000) +1
                binding.daysTextBottom.text="%d일 째 초록을 키우는 중🍀".format(days)
}
            override fun onCancelled(error: DatabaseError) {
            }
        })

    }
    }