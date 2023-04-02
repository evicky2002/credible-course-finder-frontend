package com.example.credible_course_recommender.repository

import com.example.credible_course_recommender.api.RetrofitInstance

class CourseRepository() {

    suspend fun getCourses() = RetrofitInstance.api.getCourses()
}