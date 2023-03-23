package com.example.credible_course_recommender.models

import com.google.gson.annotations.SerializedName
import org.json.JSONObject

data class CourseraModel(
    @SerializedName("_highlightResult")
    val _highlightResult: HighlightResult,
    val _id: String,
    @SerializedName("_snippetResult")
    val _snippetResult: SnippetResult,
    val allLanguageCodes: List<String>,
    val audience: Any,
    val avgLearningHours: Int,
    val avgProductRating: Double,
    val cobrandingEnabled: Any,
    val enrollments: Int,
    val entityType: String,
    val imageUrl: String,
    val isCourseFree: Boolean,
    val isCreditEligible: Boolean,
    val isPartOfCourseraPlus: Boolean,
    val language: String,
    val name: String,
    val numProductRatings: Int,
    val objectID: String,
    val objectUrl: String,
    val partnerLogos: List<String>,
    val partners: List<String>,
    val productDifficultyLevel: String,
    val productDurationEnum: String,
    val skills: List<String>,
    val subtitleLanguage: List<String>,
    val tagline: String
)