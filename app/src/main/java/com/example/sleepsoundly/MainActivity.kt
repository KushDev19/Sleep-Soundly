package com.example.sleepsoundly

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    lateinit var myRecyclerView : RecyclerView
    lateinit var myAdapter: MusicAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myRecyclerView= findViewById(R.id.rvMusic)

        val retrofitBuilder= Retrofit.Builder()
            .baseUrl("https://deezerdevs-deezer.p.rapidapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)

        val retrofitData=retrofitBuilder.getData("eminem")

        retrofitData.enqueue(object : Callback<MusicData?> {
            override fun onResponse(
                call: Call<MusicData?>, response: Response<MusicData?>
            ) {
                val dataList = response.body()?.data!!

                myAdapter= MusicAdapter(this@MainActivity, dataList)
                myRecyclerView.adapter= myAdapter
                myRecyclerView.layoutManager= LinearLayoutManager(this@MainActivity)


                Log.d("TAG: OnResponse", "onResponse" + response.body())

            }

            override fun onFailure(call: Call<MusicData?>, t: Throwable) {
                Log.d("TAG: onFailure", "onFailure: " + t.message)
            }
        })
    }



}