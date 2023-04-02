package com.example.credible_course_recommender.models

data class Course(
    val _id: String,
    val avgLearningHours: Int,
    val avgProductRating: Double,
    val description: String,
    val enrollments: Int,
    val imageUrl: String,
    val isCourseFree: Boolean,
    val language: String,
    val name: String,
    val objectUrl: String,
    val partnerLogos: String,
    val partners: String,
    val productDifficultyLevel: String,
    val productDurationEnum: String,
    val skills: String
)