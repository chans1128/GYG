package com.example.gyg.Community

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.gyg.R
import com.example.gyg.databinding.FragmentCommunityCertifyBinding
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

class Community_certify : DialogFragment() {
    private lateinit var binding: FragmentCommunityCertifyBinding
    val storage = FirebaseStorage.getInstance() // 스토리지 인스턴스를 만들고
    var storageRef = storage.getReference() //스토리지 인스턴스를 참조
    val pathRef = storageRef.child("/certify/bag.jpg")
    val pathRef2 = storageRef.child("/certify/tumbler.jpg")
    val pathRef3 = storageRef.child("/certify/recycling.png")
    val pathRef4 = storageRef.child("/certify/bus.jpg")
    val pathRef5 = storageRef.child("/certify/subway.jpg")
    val pathRef6 = storageRef.child("/certify/bag.jpg")
    lateinit var imageUri: Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater , container: ViewGroup? ,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCommunityCertifyBinding.inflate(inflater, container, false)
        //loadImage(binding.Fimage)
        binding.cancel.setOnClickListener {
            dismiss()
        }
        //initAddImageButton()
        //initSubmitItemButton()
        loadImage(binding.Fimage)
        loadImage(binding.Fimage2)
        loadImage(binding.Fimage3)
        loadImage(binding.Fimage4)
        loadImage(binding.Fimage5)
        loadImage(binding.Fimage6)



        return binding.root
    }

    // 사진 불러오기 된당~!~!~!
    // 나중에 리사이클러뷰 구현해서 불러오기
    private fun loadImage(imageView: ImageView) {
        pathRef.downloadUrl.addOnSuccessListener { uri ->
            Glide.with(imageView.context)
                .load(uri)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .centerCrop()
                .into(imageView)
        }
        pathRef2.downloadUrl.addOnSuccessListener { uri ->
            Glide.with(imageView.context)
                .load(uri)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .centerCrop()
                .into(imageView)
        }
        pathRef3.downloadUrl.addOnSuccessListener { uri ->
            Glide.with(imageView.context)
                .load(uri)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .centerCrop()
                .into(imageView)
        }
        pathRef4.downloadUrl.addOnSuccessListener { uri ->
            Glide.with(imageView.context)
                .load(uri)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .centerCrop()
                .into(imageView)
        }
        pathRef5.downloadUrl.addOnSuccessListener { uri ->
            Glide.with(imageView.context)
                .load(uri)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .centerCrop()
                .into(imageView)
        }
        pathRef6.downloadUrl.addOnSuccessListener { uri ->
            Glide.with(imageView.context)
                .load(uri)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .centerCrop()
                .into(imageView)
        }
    }

    // 파이어베이스 사진 업로드도 성공~!~!~!
//    private fun initAddImageButton() {
//        binding.addBtn.setOnClickListener {
//            when {
//                ContextCompat.checkSelfPermission(requireContext(),
//                    Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
//                -> {
//                    // 권한이 존재하는 경우 이미지를 가져옴
//                    getImageFromAlbum()
//                }
//                shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE) -> {
//                    // 권한이 거부 되어 있는 경우
//                    // showPermissionContextPopup()
//                }
//                else -> {
//                    // 처음 권한을 시도했을 때 띄움
//                    requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),REQUEST_FIRST)
//                }
//            }
//        }
//    }
//
//
//    // Intent 객체를 생성하고 갤러리 액티비티를 실행하기 위한 정보를 setting
//    //startActivityForResult의 두번째 인자로 나중에 확인을 위한 request Code 값을 넣는다.
//    private fun getImageFromAlbum() {
//        val intent = Intent(Intent.ACTION_GET_CONTENT)
//        intent.type = "image/*"
//        startActivityForResult(intent,REQUEST_GET_IMAGE)
//
//    }
//
//    // 갤러리 앱 실행 결과 처리하기
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if(resultCode != Activity.RESULT_OK) {
//            return
//        }
//        when(requestCode){
//            REQUEST_GET_IMAGE -> {
//                data?:return
//                val uri = data.data as Uri
//                // 이미지 URI를 가지고 하고 싶은 거 하면 된다.
//                binding.Fimage.setImageURI(uri)
//                imageUri = uri
//
//            }
//            else -> {
//                //Toast.makeText(this, "사진을 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//
//    private fun initSubmitItemButton() {
//        binding.submitBtn.setOnClickListener {
//            pathRef.putFile(imageUri)
//        }
//    }
//
//    companion object {
//        const val REQUEST_FIRST = 1000
//        const val REQUEST_GET_IMAGE = 2000
//    }




}