package com.example.gyg.Home

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import com.example.gyg.Sign.myRef
import com.example.gyg.databinding.FragmentHomeWateringBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.values

class HomeWatering : DialogFragment() {
    private lateinit var myRef: DatabaseReference
    private lateinit var binding: FragmentHomeWateringBinding
    private lateinit var user: FirebaseUser
    private lateinit var uid: String
    var userPoint = 0
    var plantPoint = 0
    var plantGrowthRate = 0
    var plantLevel = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        binding = FragmentHomeWateringBinding.inflate(layoutInflater)
        user = FirebaseAuth.getInstance().currentUser!!
        uid = user?.uid.toString()
        myRef = FirebaseDatabase.getInstance().getReference("User").child(uid)
        initData()
        setButton()
        return binding.root
    }

    private fun initData() {
        myRef.addValueEventListener(object : ValueEventListener {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onDataChange(snapshot: DataSnapshot) {
                plantPoint =
                    snapshot.child("PlantInfo").child("plant_point").value.toString().toInt()
                plantLevel =
                    snapshot.child("PlantInfo").child("plant_level").value.toString().toInt()
                userPoint = snapshot.child("UserInfo").child("user_point").value.toString().toInt()
                binding.wateringUserPoint.text = userPoint.toString()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

    }

    private fun setButton() {
        binding.wateringNoBtn.setOnClickListener() {
            dismiss()
        }

        binding.wateringYesBtn.setOnClickListener() {
            if(plantLevel>5){
                Toast.makeText(activity, "최고 레벨에 도달하여 더 이상 물을 줄 수 없어요.", Toast.LENGTH_SHORT).show()
                dialog?.dismiss()
            }else {
                Toast.makeText(activity, "$plantGrowthRate:r,$plantLevel:l,$plantPoint:p", Toast.LENGTH_SHORT).show()
                if (userPoint >= 100) {
                    myRef.child("PlantInfo").child("plant_point").setValue(plantPoint + 100)
                    myRef.child("UserInfo").child("user_point").setValue(userPoint - 100)
                    plantGrowthRate = ((plantPoint + 100) * 100 / maxPoint(plantLevel))
                    myRef.child("PlantInfo").child("plant_growth_rate").setValue(plantGrowthRate)
                    Toast.makeText(getActivity(), "물주기 성공!👍🏻", Toast.LENGTH_SHORT)
                        .show()
                    checkLevel()
                } else {
                    Toast.makeText(getActivity(), "포인트가 부족해요😢", Toast.LENGTH_SHORT).show()

                }
            }
        }
    }

    private fun maxPoint(level: Int): Int {
        if (plantLevel == 1) {
            return 300
        } else if (plantLevel == 2) {
            return 500
        } else if (plantLevel == 3) {
            return 700
        } else if (plantLevel == 4) {
            return 1000
        } else if (plantLevel == 5) {
            return 1500
        } else {
            return -1
        }
    }

    private fun checkLevel() {
        if((plantPoint+100) >= maxPoint(plantLevel) && plantLevel<=5){
            myRef.child("PlantInfo").child("plant_point").setValue(0)
            myRef.child("PlantInfo").child("plant_level").setValue(plantLevel+1)
            myRef.child("PlantInfo").child("plant_growth_rate").setValue(0)
            Toast.makeText(activity, "레벨업!🎉", Toast.LENGTH_SHORT).show()
            dialog?.dismiss()
        }
//        if(plantLevel>=6){
//            myRef.child("PlantInfo").child("plant_point").setValue(1500)
//            myRef.child("PlantInfo").child("plant_level").setValue(5)
//            myRef.child("PlantInfo").child("plant_growth_rate").setValue(100)
//        }
    }
}