package com.example.gyg.Home

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.gyg.R
import com.example.gyg.databinding.FragmentHomePlantBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomePlantFragment : Fragment() {
    private lateinit var myRef: DatabaseReference
    private lateinit var binding: FragmentHomePlantBinding
    var nickname = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomePlantBinding.inflate(layoutInflater)
        initData()
        setButton()
        return binding.root
    }

    private fun initData() {
        val user = FirebaseAuth.getInstance().currentUser
        val uid = user?.uid

        myRef = FirebaseDatabase.getInstance().getReference("User").child(uid.toString())
        myRef.addValueEventListener(object : ValueEventListener {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onDataChange(snapshot: DataSnapshot) {
                nickname = snapshot.child("UserInfo").child("user_nickname").value.toString()
                var plantLevel = snapshot.child("PlantInfo").child("plant_level").value.toString()
                var plantGrowthRate =
                    snapshot.child("PlantInfo").child("plant_growth_rate").value.toString()
                binding.homeName.text = nickname.substring(0, nickname.indexOf("#"))
                binding.homeLevel.text = "Lv.$plantLevel"
                setImage(plantLevel.toInt())
                setProgressBar(plantGrowthRate.toInt())
//                Toast.makeText(getActivity(),snapshot.child("PlantInfo").child("plant_level").value.toString(),Toast.LENGTH_SHORT).show()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    private fun setProgressBar(percent: Int) {
        binding.homeProgressBar.progress = percent
        binding.homePercent.text = "$percent%"
    }

    private fun setButton() {
        binding.homeWaterButton.setOnClickListener {
            val wateringDialog = HomeWatering()
            activity?.supportFragmentManager?.let { fragmentManager ->
                wateringDialog.show(fragmentManager, "Home_wateringDialog")
            }
        }
        binding.homePhotosaveButton.setOnClickListener {
            Toast.makeText(activity, "사진을 갤러리에 저장했어요📷", Toast.LENGTH_LONG).show()
        }
    }

    private fun setImage(level: Int) {
        if (level == 1) binding.homePlantImg.setImageResource(R.drawable.plant_seed)
        else if (level == 2) binding.homePlantImg.setImageResource(R.drawable.plant_sprout)
        else if (level == 3) binding.homePlantImg.setImageResource(R.drawable.plant_stem)
        else if (level == 4) binding.homePlantImg.setImageResource(R.drawable.plant_flower)
        else if (level == 5) binding.homePlantImg.setImageResource(R.drawable.plant_fruit)
    }
}