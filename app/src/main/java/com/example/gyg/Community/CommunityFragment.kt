package com.example.gyg.Community

import android.os.Bundle
import com.example.gyg.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gyg.databinding.FragmentCommunityBinding
import com.example.gyg.databinding.FragmentCommunityWriteBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlin.properties.Delegates


class CommunityFragment : Fragment() {
    private lateinit var binding: FragmentCommunityBinding
    val database = Firebase.database
    val myRef = database.getReference("Board")
    val myRef2 = database.getReference("InfoBoard")
    val storage = FirebaseStorage.getInstance() // 스토리지 인스턴스를 만들고
    var storageRef = storage.getReference() //스토리지 인스턴스를 참조

    // 현재 user 가져오기
    val user = FirebaseAuth.getInstance().currentUser
    val userID = user?.uid // 현재 로그인한 사용자의 파이어베이스 uid

    lateinit var title: String
    lateinit var content: String
    lateinit var writer: String
    lateinit var date: String
    var good by Delegates.notNull<Int>()


    override fun onCreateView(
        inflater: LayoutInflater , container: ViewGroup? ,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCommunityBinding.inflate(inflater , container , false)

        myRef.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                title = snapshot.child(userID.toString()).child("title").getValue().toString()
                content = snapshot.child(userID.toString()).child("content").getValue().toString()
                writer = snapshot.child(userID.toString()).child("writer").getValue().toString()
                date = snapshot.child(userID.toString()).child("date").getValue().toString()
                good = snapshot.child(userID.toString()).child("good").getValue().toString().toInt()
                binding.h1.text = title
                binding.h2.text = content
                binding.h3.text = ""
                binding.h4.text = date + "     💚 0"
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
        myRef2.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                title = snapshot.child(userID.toString()).child("title").getValue().toString()
                content = snapshot.child(userID.toString()).child("content").getValue().toString()
                binding.i1.text = title
                binding.i2.text = content
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })


        setButtonClickEvent()

        return binding.root
    }

    private fun setButtonClickEvent() {
        // 글쓰기 버튼
        binding.writeinfo.setOnClickListener() {
            val dialog = Community_write_info()
            dialog.setStyle(
                DialogFragment.STYLE_NO_TITLE ,
                android.R.style.Theme_NoTitleBar_Fullscreen
            )
            activity?.supportFragmentManager?.let { fragmentManager ->
                dialog.show(fragmentManager , "Community_write_info_dialog")
            }

        }
        binding.write2.setOnClickListener() {
            val dialog = Community_write()
            dialog.setStyle(
                DialogFragment.STYLE_NO_TITLE ,
                android.R.style.Theme_NoTitleBar_Fullscreen
            )
            activity?.supportFragmentManager?.let { fragmentManager ->
                dialog.show(fragmentManager , "Community_write_dialog")
            }

        }
        // 사진 인증 누르면 인증 사진 보여줌
        binding.certifyBtn.setOnClickListener() {
            val dialog = Community_certify()
            dialog.setStyle(
                DialogFragment.STYLE_NO_TITLE ,
                android.R.style.Theme_NoTitleBar_Fullscreen
            )
            activity?.supportFragmentManager?.let { fragmentManager ->
                dialog.show(fragmentManager , "Community_certify_dialog")
            }

        }
    }

}





