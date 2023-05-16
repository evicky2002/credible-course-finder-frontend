package com.project.credible_course_recommender.repository

import com.project.credible_course_recommender.api.RetrofitInstance
import com.project.credible_course_recommender.models.FavoritesPayload
import com.project.credible_course_recommender.models.Payload

class CourseRepository() {

    suspend fun getCourses(payload: Payload) = RetrofitInstance.api.getCourses(payload)
    suspend fun getFavorites(payload: FavoritesPayload) = RetrofitInstance.api.getFavorites(payload)
}