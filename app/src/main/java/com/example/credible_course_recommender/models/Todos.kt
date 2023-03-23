package com.example.credible_course_recommender.models

data class Todos(
    val completed: Boolean,
    val id: Int,
    val title: String,
    val userId: Int
)