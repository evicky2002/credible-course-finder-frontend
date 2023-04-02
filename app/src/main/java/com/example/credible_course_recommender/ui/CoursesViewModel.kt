package com.example.credible_course_recommender.ui

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.credible_course_recommender.models.CourseResponse
import com.example.credible_course_recommender.repository.CourseRepository
import com.example.credible_course_recommender.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class CoursesViewModel(
    app: Application,
    val courseRepository: CourseRepository
):ViewModel() {

    val searchCourses: MutableLiveData<Resource<CourseResponse>> = MutableLiveData()

    init {
        getCourses()
    }
    fun getCourses() = viewModelScope.launch {
        searchCourses.postValue(Resource.Loading())
        val response = courseRepository.getCourses()
        searchCourses.postValue(handleSearchCoursesResponse(response))

    }

    private fun handleSearchCoursesResponse(response: Response<CourseResponse>): Resource<CourseResponse>{
        if(response.isSuccessful){
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}