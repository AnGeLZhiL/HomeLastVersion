package com.example.homelastversion

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.homelastversion.databinding.FragmentTwoBinding
import okhttp3.*
import okio.IOException
import org.json.JSONObject


class TwoFragment : Fragment(), WantedAdapter.Listner {
    private lateinit var binding: FragmentTwoBinding
    private val adapter = WantedAdapter(this)
    private val client = OkHttpClient()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTwoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val request = Request.Builder()
            .url("http://mad2019.hakta.pro/api/wanted")
            .build()
        client.newCall(request).enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {
                TODO("Not yet implemented")
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.code == 200){
                    val jsonArray = JSONObject(response.body.string()).getJSONArray("data")
                    for (i in 0 until jsonArray.length()) {
                        Global.wanted.add(
                            WontedModel(
                                id = jsonArray.getJSONObject(i).getString("id"),
                                nicknames = jsonArray.getJSONObject(i).getString("nicknames"),
                                description = jsonArray.getJSONObject(i).getString("description"),
                                photo = jsonArray.getJSONObject(i).getString("photo")
                            )
                        )
                        println("--------------"+jsonArray.getJSONObject(i).getString("nicknames"))
                    }
                    Handler(Looper.getMainLooper()).post {
                        binding.recycler.adapter = adapter
                    }
                }
            }

        })
    }

    override fun onClickItem(wontedModel: WontedModel) {
        findNavController().navigate(R.id.action_twoFragment_to_blankFragment)
        Global.id = wontedModel.id
        Global.img = wontedModel.photo
        Global.d = wontedModel.description
    }
}