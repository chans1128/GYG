package com.example.gyg.Community

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gyg.R
import com.example.gyg.databinding.FragmentCommunityBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class CommunityFragment : Fragment() {
    private lateinit var binding: FragmentCommunityBinding
    val database = Firebase.database
    val myRef = database.getReference("test1")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater , container: ViewGroup? ,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCommunityBinding.inflate(inflater, container, false)

        try{
                    myRef.addValueEventListener(object: ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            myRef.child("test").setValue("파이어베이스 write 성공").toString()
                        }
                        override fun onCancelled(error: DatabaseError) {
                }
            })
        } catch(E:Exception) {

        }

        return binding.root
    }

    companion object {

    }
}
