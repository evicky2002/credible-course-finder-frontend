package com.project.credible_course_recommender.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.project.credible_course_recommender.R
import com.project.credible_course_recommender.adapters.CourseAdapter
import com.project.credible_course_recommender.adapters.FavoritesAdapter
import com.project.credible_course_recommender.models.FavoritesPayload
import com.project.credible_course_recommender.ui.MainActivity
import com.project.credible_course_recommender.ui.CoursesViewModel
import com.project.credible_course_recommender.util.Constants
import com.project.credible_course_recommender.util.Resource


class FavouritesFragment : Fragment(R.layout.fragment_favourites) {
    lateinit var viewModel: CoursesViewModel
    lateinit var favoritesAdapter: FavoritesAdapter
    lateinit var rvCourses: RecyclerView
    lateinit var cvProgress: ConstraintLayout

    val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        rvCourses = view.findViewById(R.id.rvCourses)
        cvProgress = view.findViewById(R.id.cvProgress)
        cvProgress.visibility = View.VISIBLE
        setupRecyclerView()
        viewModel.getFavorites(FavoritesPayload(mAuth.uid.toString()))


        viewModel.searchCourses.observe(viewLifecycleOwner, Observer {

                response ->
            when (response) {
                is Resource.Success -> {
//                    cvProgress.visibility = View.INVISIBLE


                    response.data?.let { res ->
                        cvProgress.visibility = View.INVISIBLE

                        favoritesAdapter.differ.submitList(res.courses.toList())
                    }
                }
                is Resource.Error -> {

                    response.message?.let { msg ->
                        Log.e("sd", "sdf")
                    }
                }
                is Resource.Loading -> {
                    cvProgress.visibility = View.VISIBLE

                    response.message?.let { msg ->
                        Log.e("sd", "sdf")
                    }
                }
                else -> {

                }
            }
        })


    }

    private fun setupRecyclerView() {

        favoritesAdapter = FavoritesAdapter(requireContext())
        rvCourses.apply {
            adapter = favoritesAdapter
            layoutManager = LinearLayoutManager(activity)


        }

    }
}