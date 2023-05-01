package com.project.credible_course_recommender.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.credible_course_recommender.R
import com.project.credible_course_recommender.adapters.CourseAdapter
import com.project.credible_course_recommender.models.Payload
import com.project.credible_course_recommender.ui.CoursesViewModel
import com.project.credible_course_recommender.ui.MainActivity
import com.project.credible_course_recommender.util.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchFragment : Fragment(R.layout.fragment_search) {
    lateinit var viewModel : CoursesViewModel
    lateinit var courseAdapter: CourseAdapter
    lateinit var rvCourses: RecyclerView
    lateinit var cvProgress: ConstraintLayout
    lateinit var svSearch: SearchView
    lateinit var tvDecider: TextView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        rvCourses = view.findViewById(R.id.rvCourses)
        svSearch = view.findViewById(R.id.svSearch)
        cvProgress = view.findViewById(R.id.cvProgress)
        tvDecider = view.findViewById(R.id.tvDecider)
        cvProgress.visibility = View.VISIBLE
        tvDecider.text = "You might be interested in"
        setupRecyclerView()

//        courseAdapter.setOnItemClickListener {
//            val bundle = Bundle().apply {
//                putSerializable("course",it)
//            }
//            findNavController().navigate(
//               R.id.action_searchFragment_to_courseFragment,
//               bundle
//            )
//        }


        var job: Job? =null
        svSearch.setOnCloseListener (object : SearchView.OnCloseListener {
            override fun onClose(): Boolean {
                val payload = Payload("")
                cvProgress.visibility = View.VISIBLE
                job?.cancel()
                job = MainScope().launch {
                    delay(500)
                    payload?.let {
                        viewModel.getCourses(payload)
                    }
                }
                return true
            }

        })

        svSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                callSearch(query)
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
//              if (searchView.isExpanded() && TextUtils.isEmpty(newText)) {
                callSearch(newText)
                //              }
                return true
            }

            fun callSearch(query: String?) {
                if(query?.isEmpty() == true){
                    tvDecider.text = "You might be interested in"

                }else{
                    tvDecider.text = "Your search results"

                }

                cvProgress.visibility = View.VISIBLE

                job?.cancel()
                job = MainScope().launch {
                    delay(500)
                    query?.let {
                        viewModel.getCourses(Payload(query))
                    }
                }
            }
        })

        viewModel.searchCourses.observe(viewLifecycleOwner, Observer {

            response ->
            when(response){
                is Resource.Success -> {
                    response.data?.let { res->
                        cvProgress.visibility = View.INVISIBLE

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