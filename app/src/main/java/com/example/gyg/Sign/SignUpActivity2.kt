package com.example.gyg.Sign

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.gyg.databinding.ActivitySignUp2Binding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class SignUpActivity2 : AppCompatActivity() {
    private var mAuth: FirebaseAuth? =null
    lateinit var binding: ActivitySignUp2Binding

    @RequiresApi(Build.VERSION_CODES.O)
    var date = LocalDate.now()

    val database : FirebaseDatabase = FirebaseDatabase.getInstance()
    val myRef : DatabaseReference = database.reference

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUp2Binding.inflate(layoutInflater)

        setContentView(binding.root)

        mAuth = Firebase.auth

        binding.BtnSignUp.setOnClickListener({
            signUp()
        })

    }

    private fun generateCertificationNumber(number: Int = 9999) = (0..number).random()
        .toString()
        .padStart(number.toString().length, '0')

    private fun getTime(): String? {
        val now = System.currentTimeMillis()
        val date = Date(now)
        val dateFormat =
            SimpleDateFormat("yyyy-MM-dd")
        return dateFormat.format(date)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun signUp(){

        if(binding.inputId.length()>0&&binding.inputPw.length()>0&&binding.inputPwCheck.length()>0&&binding.inputNickname.length()>0){
            var id = binding.inputId.text.toString()
            var pw = binding.inputPw.text.toString()
            var user_name = binding.inputName.text.toString()
            var user_num = binding.inputNum.text.toString()
            var pw_ck = binding.inputPwCheck.text.toString()
            if(pw == pw_ck) {
                mAuth?.createUserWithEmailAndPassword(id, pw)
                    ?.addOnCompleteListener(
                        this
                    ) { task ->
                        if (task.isSuccessful) {


                            var user_uid = mAuth?.uid
                            var user_id = binding.inputId.text.toString()
                            var user_pw = binding.inputPw.text.toString()
                            var user_nickname = binding.inputNickname.text.toString()+ "#" +generateCertificationNumber()
                            var user_date = getTime().toString()


                            if (user_uid != null) {
                                myRef.child("User").child(user_uid).child("UserInfo")
                                    .child("user_name").setValue(user_name)
                                myRef.child("User").child(user_uid).child("UserInfo")
                                    .child("user_phone").setValue(user_num)

                                myRef.child("User").child(user_uid).child("UserInfo")
                                    .child("user_id").setValue(user_id)
                                myRef.child("User").child(user_uid).child("UserInfo")
                                    .child("user_pw").setValue(user_pw)
                                myRef.child("User").child(user_uid).child("UserInfo")
                                    .child("user_nickname").setValue(user_nickname)
//                                myRef.child("User").child(user_uid).child("UserInfo")
//                                    .child("user_date").setValue(user_date)
                                val currentDate: LocalDateTime = LocalDateTime.now()
                                myRef.child("User").child(user_uid).child("PlantInfo").child("plant_level").setValue(1) // 식물레벨
                                myRef.child("User").child(user_uid).child("PlantInfo").child("plant_point").setValue(0) // 식물포인트
                                myRef.child("User").child(user_uid).child("PlantInfo").child("plant_growth_point").setValue(0) //식물성장치(%단위)
                                myRef.child("User").child(user_uid).child("UserInfo").child("user_point").setValue(0) //사용자현재포인트
                                myRef.child("User").child(user_uid).child("UserInfo").child("user_signup_date").setValue(currentDate.format(
                                    DateTimeFormatter.ofPattern("yyyyMMdd", Locale("ko", "KR"))
                                )) //가입날짜
                            }

                            val user = mAuth!!.currentUser
                            Toast.makeText(this, "회원가입에 성공하였습니다.", Toast.LENGTH_SHORT).show()
                            changeActivity(SignInActivity::class.java)
                        } else {
                            if (task.exception != null) {
                                if (task.exception.toString() == "com.google.firebase.auth.FirebaseAuthUserCollisionException: The email address is already in use by another account.") {
                                    Toast.makeText(
                                        this,
                                        "이미 있는 아이디입니다. 다시 입력하세요.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else if (task.exception.toString() == "com.google.firebase.auth.FirebaseAuthWeakPasswordException: The given password is invalid. [ Password should be at least 6 characters ]") {
                                    Toast.makeText(
                                        this,
                                        "비밀번호는 최소 6자리 이상이어야 합니다. 다시 입력하세요.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    }
            }else{
                Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(this, "모든 항목을 입력하세요.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun changeActivity(c: Class<*>) {
        val intent = Intent(this, c)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}