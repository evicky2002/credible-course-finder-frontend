package com.project.credible_course_recommender.api

import LoginResponse
import com.project.credible_course_recommender.models.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface CourseAPI {

    @POST("api/v1/courses")
    @Headers("Content-Type: application/json")
    suspend fun getCourses(@Body payload: Payload
    ): Response<CourseResponse>
    @POST("api/v1/login")
    @Headers("Content-Type: application/json")
    suspend fun loginUser(@Body payload: LoginPayload
    ): Response<LoginResponse>

    @GET("api/v1/courses/getTopSkills")
    suspend fun getTopSkills(
    ): Response<SkillsResponse>

    @POST("api/v1/login/addInterests")
    @Headers("Content-Type: application/json")
    suspend fun addInterests(@Body payload: InterestsPayload
    ): Response<InterestsResponse>

    @POST("api/v1/login/getFavorites")
    @Headers("Content-Type: application/json")
    suspend fun getFavorites(@Body payload: FavoritesPayload
    ): Response<CourseResponse>

    @POST("api/v1/login/addToFavorites")
    @Headers("Content-Type: application/json")
    suspend fun addToFavorites(@Body payload: FavoritesPayload
    ): Response<AddToFavoritesResponse>

    @POST("api/v1/login/removeFromFavorites")
    @Headers("Content-Type: application/json")
    suspend fun removeFromFavorites(@Body payload: FavoritesPayload
    ): Response<AddToFavoritesResponse>
    @POST("api/v1/login/getInterests")
    @Headers("Content-Type: application/json")
    suspend fun getInterests(@Body payload: UserPayload
    ): Response<UserResponse>
}