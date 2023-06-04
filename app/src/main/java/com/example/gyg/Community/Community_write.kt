package com.example.gyg.Community

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.gyg.R
import com.example.gyg.databinding.FragmentCommunityWriteBinding
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class Community_write : DialogFragment() {
    private  lateinit var binding: FragmentCommunityWriteBinding
    val database = Firebase.database
    val myRef = database.getReference("Board")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater , container: ViewGroup? ,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCommunityWriteBinding.inflate(inflater, container, false)

        // 취소 버튼 이벤트
        binding.cancel.setOnClickListener{
            dismiss() // 사라져 버리기~~
        }

        // 확인 버튼 이벤트
        binding.finish.setOnClickListener{
            // DB에 제목, 글, 날짜, 아이디 저장
            // count 값 불러와서 게시글 번호 이어서 붙이기

            dismiss()
        }

        return binding.root
    }


}