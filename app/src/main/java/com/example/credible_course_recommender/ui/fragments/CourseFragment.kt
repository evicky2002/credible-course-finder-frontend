package com.example.credible_course_recommender.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.credible_course_recommender.R
import com.example.credible_course_recommender.ui.CoursesViewModel
import com.example.credible_course_recommender.ui.MainActivity


class CourseFragment : Fragment(R.layout.fragment_course) {
    lateinit var viewModel : CoursesViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
    }
}