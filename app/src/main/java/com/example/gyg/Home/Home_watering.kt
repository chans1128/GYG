package com.example.gyg.Home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.gyg.R
import com.example.gyg.databinding.FragmentHomeBinding
import com.example.gyg.databinding.FragmentHomePlantBinding
import com.example.gyg.databinding.FragmentHomeWateringBinding

class Home_watering : DialogFragment() {

    private lateinit var binding: FragmentHomeWateringBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeWateringBinding.inflate(layoutInflater)
        return inflater.inflate(R.layout.fragment_home_watering, container, false)
    }


}