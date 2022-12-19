package com.example.homelastversion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.homelastversion.databinding.ActivitySignInBinding
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okio.IOException
import org.json.JSONObject

class SignIn : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signIn.setOnClickListener {
            val requestBody = RequestBody.create(
                "application/json".toMediaTypeOrNull(),
                JSONObject()
                    .put("email", binding.email.text.toString())
                    .put("password", binding.password.text.toString())
                    .toString()
            )
            val request = Request.Builder()
                .url("http://wsk2019.mad.hakta.pro/api/user/login")
                .post(requestBody)
                .build()
            client.newCall(request).enqueue(object : Callback{
                override fun onFailure(call: Call, e: IOException) {
                    TODO("Not yet implemented")
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.code == 200){
                        Global.token = JSONObject(response.body.string()).getString("token")
                        startActivity(Intent(this@SignIn, MenuActivity::class.java))
                        finish()
                    }
                }

            })

        }

        binding.signUp.setOnClickListener {
            startActivity(Intent(this, SignUp::class.java))
        }
    }
}