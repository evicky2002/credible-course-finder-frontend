package com.example.credible_course_recommender.models

import com.google.gson.annotations.SerializedName

data class SnippetResult(
    @SerializedName("description")
    val description: Description
)