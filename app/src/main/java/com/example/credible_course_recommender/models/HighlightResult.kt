package com.example.credible_course_recommender.models

import com.google.gson.annotations.SerializedName

data class HighlightResult(
    @SerializedName("name")
    val name: Name
)