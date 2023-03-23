package com.example.credible_course_recommender.models

data class Name(
    val matchLevel: String,
    val matchedWords: List<Any>,
    val value: String
)