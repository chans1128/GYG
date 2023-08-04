package com.example.gyg.Community

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gyg.R
import com.google.firebase.database.*


// RecyclerView Adpter 만들기
class Community_BoardAdapter(var item_list: ArrayList<Community_MyBoard>): RecyclerView.Adapter<Community_BoardAdapter.BoardViewHolder>() {

    private lateinit var databaseRef: DatabaseReference
    private lateinit var itemClickListener: onItemClickListener
    private val datas: ArrayList<Community_MyBoard>
    init {
        this.datas = item_list
    }

    /*getItemViewType 오버라이딩 해주면 리사이클러뷰에서 스크롤해도 아이템 섞이지 않음.*/
    override fun getItemViewType(position: Int): Int {
        return position
    }

    // 항목 뷰에 데이터를 연결한다.
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BoardViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.board_item_view, parent, false)
        return BoardViewHolder(view)
    }

    // ViewHolder가 재활용될 때 사용되는 메서드
    override fun onBindViewHolder(holder: BoardViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val data: Community_MyBoard = datas[position]
        holder.title.text = data.getTitle()
        holder.content.text = data.getContent()
        holder.writer.text = data.getWriter()
        holder.date.text = data.getDate()
        holder.goodCount.text = data.getGood().toString()

//        firebase 저장

    }

    override fun getItemCount(): Int {
        if (item_list != null) {
            return item_list.size // 전체 데이터의 개수 조회
        }
        return 1
    }

    // 커스텀 리스너 인터페이스 정의, item 누르면 글 자세히 보일 수 있도록
    interface onItemClickListener {
        fun onItemClick(view: View, position: Int)
    }


    // 아이템 뷰를 저장하는 클래스
    inner class BoardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // ViewHolder에 필요한 데이터들을 적음
        val title: TextView
        val content: TextView
        val writer: TextView
        val date: TextView
        val goodCount: TextView

        init {
            title = itemView.findViewById(R.id.title)
            content = itemView.findViewById(R.id.content)
            writer = itemView.findViewById(R.id.item_board_writer)
            date = itemView.findViewById(R.id.date)
            goodCount = itemView.findViewById(R.id.item_board_goodCount)
        }
    }
}
