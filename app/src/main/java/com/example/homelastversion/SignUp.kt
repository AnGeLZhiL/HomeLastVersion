package com.example.homelastversion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.homelastversion.databinding.ActivitySignUpBinding
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okio.IOException
import org.json.JSONObject

class SignUp : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signUp.setOnClickListener {
            val requestBody = RequestBody.create(
                "application/json".toMediaTypeOrNull(),
                JSONObject()
                    .put("email", binding.email.text.toString())
                    .put("password", binding.password.text.toString())
                    .put("nickname", binding.nickname.text.toString())
                    .put("phone", "90182837282")
                    .toString()
            )
            val request = Request.Builder()
                .url("http://wsk2019.mad.hakta.pro/api/users")
                .post(requestBody)
                .build()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    TODO("Not yet implemented")
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.code == 200){
                        startActivity(Intent(this@SignUp, MenuActivity::class.java))
                        finish()
                    }
                }
            })
        }

        binding.signIn.setOnClickListener {
            startActivity(Intent(this, SignIn::class.java))
        }
    }
}