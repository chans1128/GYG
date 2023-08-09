package com.example.gyg.Community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gyg.databinding.FragmentCommunityBinding
import com.example.gyg.databinding.FragmentCommunityWriteBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.TimeZone


class Community_write : DialogFragment() {
    private lateinit var binding: FragmentCommunityWriteBinding

    val database = Firebase.database
    val boardRef = database.getReference(FBRef.Board.toString())
    val replyRef = database.getReference(FBRef.Reply.toString())

    lateinit var board: Community_MyBoard
    lateinit var comment: Community_MyComment

    // 현재 user 가져오기
    val user = FirebaseAuth.getInstance().currentUser
    val userID = user?.uid // 현재 로그인한 사용자의 파이어베이스 uid

    val currentTime = System.currentTimeMillis() // 현재 시간 받아오기

    override fun onCreateView(
        inflater: LayoutInflater , container: ViewGroup? ,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCommunityWriteBinding.inflate(inflater , container , false)

        // 취소 버튼 이벤트
        binding.cancel.setOnClickListener {
            dismiss() // 사라져 버리기~~
            Toast.makeText(getActivity(),"글 작성이 취소되었습니다.",Toast.LENGTH_SHORT).show();
        }

        // 확인 버튼 이벤트 -> 파이어베이스에 데이터 저장, 리사이클러뷰
        binding.finish.setOnClickListener {
            val boardRef2 = database.getReference(FBRef.User.toString()).child(userID.toString()).child(FBRef.UserInfo.toString()).child(FBRef.UserNickname.toString())
            var postKey = ""
            val title = binding.title.text.toString() // 제목 받아오기
            val content = binding.content.text.toString() // 내용 받아오기
            var writer = ""
            var date = convertTimestampToDate(currentTime)

            boardRef2.addListenerForSingleValueEvent(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    writer = snapshot.getValue().toString() // 닉네임 받아오기
                    postKey = boardRef.push().key.toString() // 키 생성
                    board = Community_MyBoard(postKey, title, content, writer, date, 0)
                    boardRef.child(postKey).setValue(board) // Board에 게시글 저장
                    replyRef.child(postKey).setValue(null) // Reply에 게시글 key 값 저장
                    // 여기서 리사이클러뷰 처리를 하든, adapter class에서 처리를 하든
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
