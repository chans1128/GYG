package com.example.gyg.Community

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

object FBUtil {
    private val firebaseDatabase: FirebaseDatabase by lazy { FirebaseDatabase.getInstance() }

    private val boardDatabaseRef = firebaseDatabase.getReference("Board")

    fun getPosts(onComplete: (List<Community_MyBoard>) -> Unit) {
        boardDatabaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val boardList = dataSnapshot.children.map { it.getValue(Community_MyBoard::class.java)!! }
                onComplete(boardList)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("Firebase", "Failed to read value.", databaseError.toException())
            }
        })
    }
}