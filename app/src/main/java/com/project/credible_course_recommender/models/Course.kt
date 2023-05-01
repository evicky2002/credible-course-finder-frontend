package com.project.credible_course_recommender.models

data class Course(
    val _id: String,
    val avgLearningHours: Int,
    val avgProductRating: Double,
    val course_provider: String,
    val description: String,
    val enrollments: Int,
    val imageUrl: String,
    val isCourseFree: Int,
    val language: String,
    val name: String,
    val objectUrl: String,
    val partnerLogos: List<String>,
    val partners: List<String>,
    val productDifficultyLevel: String,
    val productDurationEnum: String,
    val skills: List<String>
): java.io.Serializable