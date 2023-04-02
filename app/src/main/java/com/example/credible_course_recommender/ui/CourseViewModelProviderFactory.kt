package com.example.credible_course_recommender.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.credible_course_recommender.repository.CourseRepository

class CourseViewModelProviderFactory(
    val app: Application,
    val courseRepository: CourseRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CoursesViewModel(app,courseRepository) as T
    }
}