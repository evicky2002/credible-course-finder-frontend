package com.example.credible_course_recommender.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.credible_course_recommender.R
import com.example.credible_course_recommender.adapters.CourseAdapter
import com.example.credible_course_recommender.ui.MainActivity
import com.example.credible_course_recommender.ui.CoursesViewModel
import com.example.credible_course_recommender.util.Resource


class SearchFragment : Fragment(R.layout.fragment_search) {
    lateinit var viewModel : CoursesViewModel
    lateinit var courseAdapter: CourseAdapter
    lateinit var rvCourses: RecyclerView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        rvCourses = view.findViewById(R.id.rvCourses)
        setupRecyclerView()
        viewModel.searchCourses.observe(viewLifecycleOwner, Observer {
            response ->
            when(response){
                is Resource.Success -> {
                    response.data?.let { res->
                        courseAdapter.differ.submitList(res.courses)
                    }
                }
                is Resource.Error -> {
                    response.message?.let { msg->
                        Log.e("sd","sdf")
                    }
                }
                is Resource.Loading -> {
                    response.message?.let { msg->
                        Log.e("sd","sdf")
                    }
                }
                else -> {

                }
            }
        })
    }

    private fun setupRecyclerView(){
        courseAdapter = CourseAdapter()
        rvCourses.apply {
            adapter = courseAdapter
            layoutManager = LinearLayoutManager(activity)
        }

    }
}