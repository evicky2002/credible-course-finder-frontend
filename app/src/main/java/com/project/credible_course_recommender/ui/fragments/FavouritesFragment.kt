package com.project.credible_course_recommender.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.project.credible_course_recommender.R
import com.project.credible_course_recommender.ui.MainActivity
import com.project.credible_course_recommender.ui.CoursesViewModel



class FavouritesFragment : Fragment(R.layout.fragment_favourites) {
    lateinit var viewModel : CoursesViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
    }
}