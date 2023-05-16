package com.project.credible_course_recommender.models

data class CourseResponse(
    val courses: MutableList<Course>,
    val coursesCount: Int,
    val status: String
)