package com.example.gyg.Home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gyg.R
import com.google.firebase.database.DatabaseReference
import com.example.gyg.databinding.FragmentHomeBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment() {
    private var days:Int = 16

    private lateinit var myRef: DatabaseReference
    private lateinit var binding: FragmentHomeBinding

//    override fun onCreateView(savedInstanceState: Bundle?) {g
//        super.onCreate(savedInstanceState)
//
//        binding = FragmentHomeBinding.inflate(layoutInflater)
//        initData()
//        return binding.root
//
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater , container: ViewGroup? ,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)

        initAdapter()
        initData()
        return binding.root
       // return inflater.inflate(R.layout.fragment_home , container , false)
    }
    private fun initAdapter() {
        val fragmentList = listOf(HomePlantFragment(),HomeMissionFragment(),HomeDailyTipFragment())
        val adapter =  HomePageAdapter(this)
        adapter.fragments.addAll(fragmentList)
        binding.vpSample.adapter = adapter
    }
    private fun initData() {
        binding.daysTextTop.text = "과 함께"
        binding.daysTextBottom.text = "$days 일 째 초록을 키우는 중🍀 "
        binding.gygLogo.setImageResource(R.drawable.gyg_logo2)
    }
//    companion object {
//        @JvmStatic
//        fun newInstance(param1: String , param2: String) =
//            HomeFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1 , param1)
//                    putString(ARG_PARAM2 , param2)
//                }
//            }
//    }
}