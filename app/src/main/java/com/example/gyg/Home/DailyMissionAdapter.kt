package com.example.gyg.Home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gyg.R

class DailyMissionAdapter(private val dailyMissions: List<DailyMission>) :
    RecyclerView.Adapter<DailyMissionAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.missionTitleTextView)
        private val completeCheckBox: CheckBox = itemView.findViewById(R.id.missionCheck)
//    private var view:View = itemView
        fun bind(mission: DailyMission) {
            titleTextView.text = mission.title
            completeCheckBox.isChecked = mission.isCompleted

            // CheckBox 상태 변경 시 이벤트 처리
            completeCheckBox.setOnCheckedChangeListener { _, isChecked ->
                mission.isCompleted = isChecked
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_mission, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: DailyMissionAdapter.ViewHolder, position: Int) {
        val task = dailyMissions[position]
        holder.apply { bind(task) }
    }

    override fun getItemCount(): Int {
        return dailyMissions.size
    }


}