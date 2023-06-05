package com.example.gyg.Community

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.gyg.R
import com.example.gyg.databinding.FragmentCommunityWriteBinding
import com.example.gyg.databinding.FragmentCommunityWriteInfoBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.TimeZone

class Community_write_info : DialogFragment() {
    private lateinit var binding: FragmentCommunityWriteInfoBinding

    val database = Firebase.database
    val myRef = database.getReference("InfoBoard")

    lateinit var board: Community_MyBoard

    // 현재 user 가져오기
    val user = FirebaseAuth.getInstance().currentUser
    val userID = user?.uid // 현재 로그인한 사용자의 파이어베이스 uid

    val currentTime = System.currentTimeMillis() // 현재 시간 받아오기

    override fun onCreateView(
        inflater: LayoutInflater , container: ViewGroup? ,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCommunityWriteInfoBinding.inflate(inflater , container , false)

        // 취소 버튼 이벤트
        binding.cancel.setOnClickListener {
            dismiss() // 사라져 버리기~~
            // toast msg 띄우기 or 알림창
        }
        // 확인 버튼 이벤트 -> 파이어베이스에 데이터 저장, 어댑터 -> 게시판에 나타내기
        binding.finish.setOnClickListener {
            var myRef3 = database.getReference("User").child(userID.toString()).child("UserInfo").child("user_nickname")
            val title = binding.title.text.toString()
            val content = binding.content.text.toString()
            var writer = ""
            var date = convertTimestampToDate(currentTime).toString()

            myRef3.addListenerForSingleValueEvent(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    writer = snapshot.getValue().toString() // 닉네임 받아오기
                    board = Community_MyBoard(title, content, writer, date, 0)
                    myRef.child(userID.toString()).setValue(board)
                }
                override fun onCancelled(error: DatabaseError) {

                }
            })
            dismiss()
        }
        return binding.root
    }

    private fun convertTimestampToDate(timestamp: Long): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd k:mm")
        sdf.timeZone = TimeZone.getTimeZone("GMT+09:00")
        val date = sdf.format(timestamp)
        return date.toString()
    }
}