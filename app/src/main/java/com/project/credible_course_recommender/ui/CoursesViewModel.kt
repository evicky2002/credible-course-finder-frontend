package com.project.credible_course_recommender.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.project.credible_course_recommender.models.CourseResponse
import com.project.credible_course_recommender.models.FavoritesPayload
import com.project.credible_course_recommender.models.Payload
import com.project.credible_course_recommender.repository.CourseRepository
import com.project.credible_course_recommender.util.Constants.Companion.QUERY_PAGE_SIZE
import com.project.credible_course_recommender.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class CoursesViewModel(
    app: Application,
    val courseRepository: CourseRepository
):ViewModel() {
    val tag = "testing-CoursesViewModel"
    var payload: Payload = Payload()
    val searchCourses: MutableLiveData<Resource<CourseResponse>> = MutableLiveData()
    var pageNumber =1
    var recommendPageNumber =1
    var searchPageNumber =1
    var courseResponse:CourseResponse?=null
    var searchResponse:CourseResponse?=null
    var favoritesResponse:CourseResponse?=null


    init {
//        getCourses(Payload(""))
    }
    fun getCourses(payload: Payload) = viewModelScope.launch {
        searchCourses.postValue(Resource.Loading())
        payload.pageNumber = recommendPageNumber
        payload.itemsPerPage = QUERY_PAGE_SIZE
        Log.i(tag,"local"+this@CoursesViewModel.payload.toString())
        Log.i(tag,"foreign"+payload.toString())

        if(this@CoursesViewModel.payload != payload){
            searchResponse =null
            searchPageNumber =1

        }

        if(payload.searchText.equals("") && payload.providerFilter.size ==0){
            searchResponse =null

            searchPageNumber =1
            val response = courseRepository.getCourses(payload)
            searchCourses.postValue(handleSearchCoursesResponse(response))

        }else{
            recommendPageNumber =1
            courseResponse =null

            payload.pageNumber = searchPageNumber
            val response = courseRepository.getCourses(payload)
            searchCourses.postValue(handlenewCoursesResponse(response))

        }

    }

    fun getFavorites(payload: FavoritesPayload) = viewModelScope.launch {
        searchCourses.postValue(Resource.Loading())
        val response = courseRepository.getFavorites(payload)
        searchCourses.postValue(handleFavoritesResponse(response))

    }

    private fun handleSearchCoursesResponse(response: Response<CourseResponse>): Resource<CourseResponse> {
        if(response.isSuccessful){
            response.body()?.let { resultResponse ->
                recommendPageNumber++
                if(courseResponse==null){
                    courseResponse = resultResponse
                }else{
                        val oldCourses = courseResponse?.courses
                        val newCourses = resultResponse.courses
                        oldCourses?.addAll(newCourses)
                }
                return Resource.Success(courseResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
    private fun handlenewCoursesResponse(response: Response<CourseResponse>): Resource<CourseResponse> {
        if(response.isSuccessful){
            response.body()?.let { resultResponse ->
                searchPageNumber++
                if(searchResponse==null){
                    searchResponse = resultResponse
                }else{
                    val oldCourses = searchResponse?.courses
                    val newCourses = resultResponse.courses
                    oldCourses?.addAll(newCourses)
                }
                return Resource.Success(searchResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleFavoritesResponse(response: Response<CourseResponse>): Resource<CourseResponse> {
        if(response.isSuccessful){
            response.body()?.let { resultResponse ->
                if(favoritesResponse==null){
                    favoritesResponse = resultResponse
                }else{
                    favoritesResponse = null
                    favoritesResponse = resultResponse

                }
                return Resource.Success(favoritesResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}