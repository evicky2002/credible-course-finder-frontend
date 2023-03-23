package com.example.credible_course_recommender.service

import com.example.credible_course_recommender.models.CourseraModel
import com.example.credible_course_recommender.models.Test
import com.example.credible_course_recommender.models.Todos
import retrofit2.Response
import retrofit2.http.GET

interface CourseAPI {
    @GET("/course/courses")
    suspend fun getCourses(): Response<List<CourseraModel>>

}