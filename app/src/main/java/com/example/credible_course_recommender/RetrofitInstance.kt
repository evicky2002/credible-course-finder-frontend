package com.example.credible_course_recommender

import com.example.credible_course_recommender.service.CourseAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val url_base = "http://192.168.1.11:3000"
//    val url_base = "https://jsonplaceholder.typicode.com"

    val api: CourseAPI by lazy {
        Retrofit.Builder()
            .baseUrl(url_base+"/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CourseAPI::class.java)
    }
}