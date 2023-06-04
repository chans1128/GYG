package com.example.gyg.Ranking

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.example.gyg.R
import com.example.gyg.databinding.FragmentHomePlantBinding
import com.example.gyg.databinding.FragmentRankingBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class RankingFragment : Fragment() {
    private lateinit var myRef: DatabaseReference
    private lateinit var binding: FragmentRankingBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRankingBinding.inflate(layoutInflater)
        setMyRank()
        return binding.root
    }

private fun setMyRank(){
    val user = FirebaseAuth.getInstance().currentUser
    val uid = user?.uid
    myRef = FirebaseDatabase.getInstance().getReference("User").child(uid.toString())
    myRef.addValueEventListener(object : ValueEventListener {
        @RequiresApi(Build.VERSION_CODES.O)
        override fun onDataChange(snapshot: DataSnapshot) {
            var nickname = snapshot.child("UserInfo").child("user_nickname").value.toString()
            nickname = nickname.substring(0, nickname.indexOf("#"))
            val plantLevel = snapshot.child("PlantInfo").child("plant_level").value.toString()
            binding.rankingUserName.text=nickname
            binding.rankingPlantLevel.text="Lv. $plantLevel"
            if(plantLevel.toInt()==1){
                binding.rank1Name.text="🥇소복"
                binding.rank10Name.text=nickname
                binding.userRank.text="10"
            }else if(plantLevel.toInt()==2){
                binding.rank10Name.text="가르송"
                binding.rank7Name.text=nickname
                binding.userRank.text="7"
            }else if(plantLevel.toInt()==3){
                binding.rank7Name.text="제로"
                binding.rank3Name.text="🥉$nickname"
                binding.userRank.text="3"
            }else if(plantLevel.toInt()==4){
                binding.rank3Name.text="🥉꼬야"
                binding.rank2Name.text="🥈$nickname"
                binding.userRank.text="2"
            }else if(plantLevel.toInt()==5){
                binding.rank2Name.text="🥈차미"
                binding.rank1Name.text="🥇$nickname"
                binding.userRank.text="1"
            }
        }
        override fun onCancelled(error: DatabaseError) {
        }
    })
}

}