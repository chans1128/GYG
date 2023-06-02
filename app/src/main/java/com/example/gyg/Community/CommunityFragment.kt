package com.example.gyg.Community

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import android.Manifest
import android.app.AlertDialog
import com.example.gyg.R
import com.example.gyg.databinding.FragmentCommunityBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask

class CommunityFragment : Fragment() {
    private lateinit var binding: FragmentCommunityBinding
    val database = Firebase.database
    val myRef = database.getReference("test1")
    val storage = FirebaseStorage.getInstance() // 스토리지 인스턴스를 만들고
    var storageRef = storage.getReference() //스토리지 인스턴스를 참조
    val pathRef = storageRef.child("/certify/1.png")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater , container: ViewGroup? ,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCommunityBinding.inflate(inflater , container , false)

//        try{
//                    myRef.addValueEventListener(object: ValueEventListener {
//                        override fun onDataChange(snapshot: DataSnapshot) {
//                            myRef.child("test").setValue("파이어베이스 write 성공").toString()
//                        }
//                        override fun onCancelled(error: DatabaseError) {
//                }
//            })
//        } catch(E:Exception) {
//
//        }
        setButtonClickEvent()

        return binding.root
    }

    private fun setButtonClickEvent() {
//        binding.BtnMeal.setOnClickListener{
//            val dialog = Community_write()
//            //dialog.setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_NoTitleBar_Fullscreen)
//            activity?.supportFragmentManager?.let { fragmentManager ->
//                dialog.show(fragmentManager, "Community_write_dialog")
//            }
//        }
        binding.write.setOnClickListener() {
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
//        binding.addImageBtn.setOnClickListener() {
//            val dialog = Community_certify()
//            dialog.setStyle(
//                DialogFragment.STYLE_NO_TITLE ,
//                android.R.style.Theme_NoTitleBar_Fullscreen
//            )
//            activity?.supportFragmentManager?.let { fragmentManager ->
//                dialog.show(fragmentManager , "Community_certify_dialog")
//            }
//
//        }

        // 파이어베이스에 사진 올리기 이벤트
        binding.submitBtn.setOnClickListener() {
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