package com.example.gyg.Home

import android.app.ProgressDialog.show
import android.media.DrmInitData
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.example.gyg.R
import com.example.gyg.databinding.FragmentHomeBinding
import com.example.gyg.databinding.FragmentHomePlantBinding
import com.google.firebase.database.DatabaseReference

class HomePlantFragment : Fragment() {
    private lateinit var myRef: DatabaseReference
    private lateinit var binding: FragmentHomePlantBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomePlantBinding.inflate(layoutInflater)
        initData()
        setProgressBar(35)
        binding.homeWaterButton.setOnClickListener{
            val wateringDialog = Home_watering()
            activity?.supportFragmentManager?.let { fragmentManager ->
                wateringDialog.show(fragmentManager, "Home_wateringDialog")
            }
        }
        return binding.root
    }

    private fun initData() {
        binding.homeName.text = "새싹이"
        binding.homeProgressBar.max = 100
    }

    private fun setProgressBar( percent:Int){
        binding.homeProgressBar.progress = percent
        binding.homePercent.text="$percent%"
    }
}