package com.example.gyg.Home

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.example.gyg.R
import com.example.gyg.Sign.myRef
import com.example.gyg.databinding.FragmentHomeMissionCertificationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class HomeMissionCertification : DialogFragment() {
    private lateinit var myRef: DatabaseReference
    private lateinit var binding: FragmentHomeMissionCertificationBinding
    private var imageUri: Uri? = null
    private lateinit var user: FirebaseUser
    private lateinit var uid: String
    var userPoint: Int? = null

    val ARG_KEY_1 = "missionNum"
    val ARG_KEY_2 = "missionName"
    private var missionNum: Int? = 0
    private var missionName: String? = null

    companion object {
        const val REQUEST_FIRST = 1000
        const val REQUEST_GET_IMAGE = 2000

        fun newInstance(value1: Int, value2: String): HomeMissionCertification {
            val fragment = HomeMissionCertification()
            val args = Bundle()
            args.putInt("missionNum", value1)
            args.putString("missionName", value2)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        missionNum = arguments?.getInt(ARG_KEY_1)
        missionName = arguments?.getString(ARG_KEY_2)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        binding = FragmentHomeMissionCertificationBinding.inflate(layoutInflater)
        binding.certificationMissionName.text = missionName

        user = FirebaseAuth.getInstance().currentUser!!
        uid = user?.uid.toString()

        myRef = FirebaseDatabase.getInstance().getReference("User").child(uid)
        myRef.addValueEventListener(object : ValueEventListener {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onDataChange(snapshot: DataSnapshot) {
                userPoint = snapshot.child("UserInfo").child("user_point").value.toString().toInt()
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
        initAddImageButton()
        initSubmitItemButton()
        return binding.root
    }

    val storage = FirebaseStorage.getInstance() // 스토리지 인스턴스를 만들고
    var storageRef = storage.getReference() //스토리지 인스턴스를 참조
    val pathRef = storageRef.child("/certify/")

    //파이어베이스 사진 업로드도 성공~!~!~!
    private fun initAddImageButton() {
        binding.certificationAddBtn.setOnClickListener {
            when {
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
                -> {
                    // 권한이 존재하는 경우 이미지를 가져옴
                    getImageFromAlbum()
                }

                shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                    // 권한이 거부 되어 있는 경우
                    // showPermissionContextPopup()
                }

                else -> {
                    // 처음 권한을 시도했을 때 띄움
                    requestPermissions(
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        REQUEST_FIRST
                    )
                }
            }
        }
    }


    // Intent 객체를 생성하고 갤러리 액티비티를 실행하기 위한 정보를 setting
    //startActivityForResult의 두번째 인자로 나중에 확인을 위한 request Code 값을 넣는다.
    private fun getImageFromAlbum() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_GET_IMAGE)

    }

    // 갤러리 앱 실행 결과 처리하기
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            return
        }
        when (requestCode) {
            REQUEST_GET_IMAGE -> {
                data ?: return
                val uri = data.data as Uri
                // 이미지 URI를 가지고 하고 싶은 거 하면 된다.
                binding.certificationImg.setImageURI(uri)
                imageUri = uri
                Toast.makeText(activity, "사진을 가져왔습니다.", Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(activity, "사진을 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initSubmitItemButton() {
        binding.certificationYesBtn.setOnClickListener {
            if (imageUri != null) {
                pathRef.putFile(imageUri!!)
                missionSuccess()
            } else {
                Toast.makeText(activity, "미션을 성공하려면 사진을 업로드해야 해요.", Toast.LENGTH_SHORT).show()
            }
        }
        binding.certificationNoBtn.setOnClickListener {
            dismiss()
        }
    }

    private fun missionSuccess() {
        val today: LocalDateTime = LocalDateTime.now()
        val todayDate = today.format(DateTimeFormatter.ofPattern("yyyyMMdd", Locale("ko", "KR")))

        if (userPoint != null) {
            if (missionNum != 5) {
                if (binding.uploadSwitch.isChecked) {
                    myRef.child("UserInfo").child("user_point")
                        .setValue((userPoint!! + 70).toString())
                    Toast.makeText(activity, " 미션성공으로 70포인트를 획득했어요.", Toast.LENGTH_SHORT).show()
                } else {
                    myRef.child("UserInfo").child("user_point")
                        .setValue((userPoint!! + 50).toString())
                    Toast.makeText(activity, " 미션성공으로 50포인트를 획득했어요.", Toast.LENGTH_SHORT).show()
                }
                myRef.child("MissionInfo").child(todayDate).child("DailyMission")
                    .child(missionNum.toString()).setValue("true")
            } else {
                if (binding.uploadSwitch.isChecked) {
                    myRef.child("UserInfo").child("user_point")
                        .setValue((userPoint!! + 120).toString())
                    Toast.makeText(activity, " 보너스 미션성공으로 120포인트를 획득했어요.", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    myRef.child("UserInfo").child("user_point")
                        .setValue((userPoint!! + 100).toString())
                    Toast.makeText(activity, " 보너스 미션성공으로 100포인트를 획득했어요.", Toast.LENGTH_SHORT)
                        .show()
                }
                myRef.child("MissionInfo").child(todayDate).child("BonusMission").setValue("true")
            }
        }


//        myRef.child("MissionInfo").child(todayDate).child("DailyMission").child(missionNum.toString()).setValue("true")

    }
}