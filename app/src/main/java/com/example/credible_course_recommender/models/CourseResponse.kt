package com.example.credible_course_recommender.models

data class CourseResponse(
    val courses: List<Course>,
    val coursesCount: Int,
    val status: String
)