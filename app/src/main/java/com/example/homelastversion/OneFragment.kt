package com.example.homelastversion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.homelastversion.databinding.FragmentOneBinding
import okhttp3.*
import okio.IOException
import org.json.JSONObject

class OneFragment : Fragment() {
    private lateinit var binding: FragmentOneBinding
    private val client = OkHttpClient()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val request = Request.Builder()
            .url("http://wsk2019.mad.hakta.pro/api/user/profile")
            .addHeader("Token", Global.token)
            .build()
        client.newCall(request).enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {
                TODO("Not yet implemented")
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.code == 200){
                    val jsonObject = JSONObject(response.body.string()).getJSONObject("content")
                    binding.email.text = jsonObject.getString("email")
                    binding.nickname.text = jsonObject.getString("nickName")
                }
            }

        })
    }
}