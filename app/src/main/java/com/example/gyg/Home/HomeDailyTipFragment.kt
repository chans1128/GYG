package com.example.gyg.Home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gyg.R
import com.example.gyg.databinding.FragmentHomeDailyTipBinding
import com.google.firebase.database.DatabaseReference

class HomeDailyTipFragment : Fragment() {
    private lateinit var myRef: DatabaseReference
    private lateinit var binding: FragmentHomeDailyTipBinding
    //private lateinit var dailyMissionAdapter: DailyMissonAdapter
    //private lateinit var dailyMissionRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeDailyTipBinding.inflate(layoutInflater)
//        initDailyMissonList()
        return inflater.inflate(R.layout.fragment_home_daily_tip, container, false)
    }

//    private fun initDailyMissonList(){
//        dailyMissionRecyclerView = binding.dailyMissonRecyclerView
//
//        val dailyMissons = listOf(
//            DailyMission(1,"텀블러 or 다회용 빨대 사용",true),
//            DailyMission(2,"대중교통타기",false),
//            DailyMission(3,"일회용 티슈 대신 손수건 사용",false),
//            DailyMission(4,"종이 대신 전자문서 사용",false)
//        )
//        dailyMissionAdapter = DailyMissonAdapter(dailyMissons)
//        dailyMissionRecyclerView.adapter = dailyMissionAdapter
//    }
}