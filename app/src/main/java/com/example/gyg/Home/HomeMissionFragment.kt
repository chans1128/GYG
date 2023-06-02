package com.example.gyg.Home

import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gyg.R
import com.example.gyg.databinding.FragmentHomeMissionBinding
import com.google.firebase.database.DatabaseReference

class HomeMissionFragment : Fragment() {
    private lateinit var myRef: DatabaseReference
    private lateinit var binding: FragmentHomeMissionBinding
    private lateinit var dailyMissionAdapter: DailyMissionAdapter
    private lateinit var dailyMissionRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_home_mission, container, false)
        dailyMissionRecyclerView =  rootView.findViewById(R.id.dailyMissonRecyclerView)
        binding = FragmentHomeMissionBinding.inflate(layoutInflater)
        initDailyMissionList()
        checkMission()
        return binding.root
    }

    private fun initDailyMissionList(){

    val dailyMissions = listOf(
        DailyMission(1,"텀블러 or 다회용 빨대 사용",true),
        DailyMission(2,"대중교통타기",false),
        DailyMission(3,"일회용 티슈 대신 손수건 사용",false),
        DailyMission(4,"종이 대신 전자문서 사용",false)
    )
        dailyMissionAdapter = DailyMissionAdapter(dailyMissions)
        dailyMissionRecyclerView.adapter = dailyMissionAdapter
    }

    private fun checkMission(){
        binding.dailyMissionCheck1.setText(null)
        binding.dailyMissionCheck3.setText(null)
        binding.dailyMission2.paintFlags = binding.dailyMission2.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        binding.dailyMission2.setTextColor(Color.rgb(211, 211, 211))
        binding.dailyMission4.paintFlags = binding.dailyMission4.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        binding.dailyMission4.setTextColor(Color.rgb(211, 211, 211))
    }
}