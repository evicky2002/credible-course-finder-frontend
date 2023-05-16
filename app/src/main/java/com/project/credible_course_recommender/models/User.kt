package com.project.credible_course_recommender.models

data class User(
    val _id: String,
    val interests: List<String>,
    val uid: String
)