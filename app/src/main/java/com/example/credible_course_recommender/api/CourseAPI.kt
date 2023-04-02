package com.example.credible_course_recommender.api

import com.example.credible_course_recommender.models.CourseResponse
import retrofit2.Response
import retrofit2.http.GET

interface CourseAPI {

    @GET("api/v1/courses")
    suspend fun getCourses(
    ): Response<CourseResponse>
}