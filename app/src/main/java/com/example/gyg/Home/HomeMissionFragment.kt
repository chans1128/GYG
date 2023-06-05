package com.example.gyg.Home

import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.collection.arrayMapOf
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.gyg.R
import com.example.gyg.Sign.myRef
import com.example.gyg.databinding.FragmentHomeMissionBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class HomeMissionFragment : Fragment() {
    private lateinit var myRef: DatabaseReference
    private lateinit var binding: FragmentHomeMissionBinding
    private lateinit var user: FirebaseUser
    private lateinit var uid: String
    private lateinit var todayDate: String

    //    private lateinit var dailyMissionAdapter: DailyMissionAdapter
//    private lateinit var dailyMissionRecyclerView: RecyclerView
    var checkButtonArr = arrayOf<CheckBox>(
    )
    var missionList = mapOf<Int, String>(
        1 to "텀블러 사용하기",
        2 to "대중교통 이용하기",
        3 to "손수건 사용하기",
        4 to "전자문서 사용하기"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        val rootView = inflater.inflate(R.layout.fragment_home_mission, container, false)
//        dailyMissionRecyclerView = rootView.findViewById(R.id.dailyMissonRecyclerView)
        binding = FragmentHomeMissionBinding.inflate(layoutInflater)
        val today = LocalDateTime.now()
        todayDate =
            today.format(DateTimeFormatter.ofPattern("yyyyMMdd", Locale("ko", "KR")))
        user = FirebaseAuth.getInstance().currentUser!!
        uid = user?.uid.toString()
        myRef = FirebaseDatabase.getInstance().getReference("User").child(uid)

        initData()
        checkMission()
        clickMission()
        return binding.root
    }

    private fun initData() {
        checkButtonArr = arrayOf<CheckBox>(
            binding.mission1Check,
            binding.mission2Check,
            binding.mission3Check,
            binding.mission4Check,
        binding.bonusMissionCheck
        )
        myRef.addValueEventListener(object : ValueEventListener {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onDataChange(snapshot: DataSnapshot) {
                val todayMission = snapshot.child("MissionInfo")
                    .child(todayDate).value
                if (todayMission == null) {
                    initDB()
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    private fun initDB() {
        for ((key, value) in missionList) {
            myRef.child("MissionInfo").child(todayDate).child("DailyMission").child(key.toString())
                .setValue("false")
        }
        myRef.child("MissionInfo").child(todayDate).child("BonusMission").setValue("false")
    }

    private fun clickMission() {
        binding.mission1Check.setOnClickListener {
            binding.mission1Check.isChecked = false
            val certificationDialog = HomeMissionCertification.newInstance(1, "텀블러 사용하기")
            activity?.supportFragmentManager?.let { fragmentManager ->
                certificationDialog.show(fragmentManager, "Home_missionCertificationDialog")
            }
        }
        binding.mission2Check.setOnClickListener {
            binding.mission2Check.isChecked = false
            val certificationDialog = HomeMissionCertification.newInstance(2, "대중교통 이용하기")
            activity?.supportFragmentManager?.let { fragmentManager ->
                certificationDialog.show(fragmentManager, "Home_missionCertificationDialog")
            }
        }

        binding.mission3Check.setOnClickListener {
            binding.mission3Check.isChecked = false
            val certificationDialog = HomeMissionCertification.newInstance(3, "손수건 사용하기")
            activity?.supportFragmentManager?.let { fragmentManager ->
                certificationDialog.show(fragmentManager, "Home_missionCertificationDialog")
            }
        }
        binding.mission4Check.setOnClickListener {
            binding.mission4Check.isChecked = false
            val certificationDialog = HomeMissionCertification.newInstance(4, "전자문서 사용하기")
            activity?.supportFragmentManager?.let { fragmentManager ->
                certificationDialog.show(fragmentManager, "Home_missionCertificationDialog")
            }
        }
        binding.bonusMissionCheck.setOnClickListener {
            binding.bonusMissionCheck.isChecked = false
            val certificationDialog = HomeMissionCertification.newInstance(5, "플로깅 하기")
            activity?.supportFragmentManager?.let { fragmentManager ->
                certificationDialog.show(fragmentManager, "Home_missionCertificationDialog")
            }
        }
    }

    private fun checkMission() {
        val color = context?.let { ContextCompat.getColor(it, R.color.text_color1) }
        var mission1SuccessOrNot: String
        var mission2SuccessOrNot: String
        var mission3SuccessOrNot: String
        var mission4SuccessOrNot: String
        var bonusMissionSuccessOrNot: String
        myRef.addValueEventListener(object : ValueEventListener {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onDataChange(snapshot: DataSnapshot) {
                mission1SuccessOrNot = snapshot.child("MissionInfo").child(todayDate)
                    .child("DailyMission")
                    .child("1").value.toString()
                mission2SuccessOrNot =
                    snapshot.child("MissionInfo").child(todayDate).child("DailyMission")
                        .child("2").value.toString()
                mission3SuccessOrNot =
                    snapshot.child("MissionInfo").child(todayDate).child("DailyMission")
                        .child("3").value.toString()
                mission4SuccessOrNot =
                    snapshot.child("MissionInfo").child(todayDate).child("DailyMission")
                        .child("4").value.toString()
                bonusMissionSuccessOrNot = snapshot.child("MissionInfo").child(todayDate)
                    .child("BonusMission").value.toString()

                if (mission1SuccessOrNot == "true") {
                    checkButtonArr[0].isEnabled = false
                    checkButtonArr[0].isChecked = true
                    binding.dailyMission1.paintFlags =
                        binding.dailyMission1.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    binding.dailyMission1.setTextColor(Color.rgb(211, 211, 211))
                } else { //false
                    checkButtonArr[0].isEnabled = true
                    checkButtonArr[0].isChecked = false
                    binding.dailyMission1.paintFlags = 0
                    binding.dailyMission1.setTextColor(Color.rgb(65, 121, 51))
                }
                if (mission2SuccessOrNot == "true") {
                    checkButtonArr[1].isEnabled = false
                    checkButtonArr[1].isChecked = true
                    binding.dailyMission2.paintFlags =
                        binding.dailyMission2.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    binding.dailyMission2.setTextColor(Color.rgb(211, 211, 211))
                } else {
                    checkButtonArr[1].isEnabled = true
                    checkButtonArr[1].isChecked = false
                    binding.dailyMission2.paintFlags = 0
                    binding.dailyMission2.setTextColor(Color.rgb(65, 121, 51))
                }
                if (mission3SuccessOrNot == "true") {
                    checkButtonArr[2].isEnabled = false
                    checkButtonArr[2].isChecked = true
                    binding.dailyMission3.paintFlags =
                        binding.dailyMission3.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    binding.dailyMission3.setTextColor(Color.rgb(211, 211, 211))
                } else {
                    checkButtonArr[2].isEnabled = true
                    checkButtonArr[2].isChecked = false
                    binding.dailyMission3.paintFlags = 0
                    binding.dailyMission3.setTextColor(Color.rgb(65, 121, 51))
                }
                if (mission4SuccessOrNot == "true") {
                    checkButtonArr[3].isEnabled = false
                    checkButtonArr[3].isChecked = true
                    binding.dailyMission4.paintFlags =
                        binding.dailyMission4.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    binding.dailyMission4.setTextColor(Color.rgb(211, 211, 211))
                } else {
                    checkButtonArr[3].isEnabled = true
                    checkButtonArr[3].isChecked = false
                    binding.dailyMission4.paintFlags = 0
                    binding.dailyMission4.setTextColor(Color.rgb(65, 121, 51))
                }
                if (bonusMissionSuccessOrNot == "true") {
                    checkButtonArr[4].isEnabled= false
                    checkButtonArr[4].isChecked = true
                    binding.BonusMission.paintFlags =
                        binding.BonusMission.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    binding.BonusMission.setTextColor(Color.rgb(211, 211, 211))
                } else {
                    checkButtonArr[4].isEnabled = true
                    checkButtonArr[4].isChecked = false
                    binding.BonusMission.paintFlags = 0
                    binding.BonusMission.setTextColor(Color.rgb(65, 121, 51))
                }
                if (mission1SuccessOrNot == "true" && mission2SuccessOrNot == "true" && mission3SuccessOrNot == "true" && mission4SuccessOrNot == "true") {
                    binding.bonusMissionCheck.isEnabled = true
                    binding.bonusMissionCheck.isVisible = true
                    binding.BonusMission.text = "플로깅하기"
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
//        binding.dailyMissionCheck1.setText(null)
//        binding.dailyMissionCheck3.setText(null)
//        binding.dailyMission2.paintFlags = binding.dailyMission2.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
//        binding.dailyMission2.setTextColor(Color.rgb(211, 211, 211))
//        binding.dailyMission4.paintFlags = binding.dailyMission4.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
//        binding.dailyMission4.setTextColor(Color.rgb(211, 211, 211))
    }
}