package com.project.credible_course_recommender.ui.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AbsListView
import android.widget.SearchView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.firebase.auth.FirebaseAuth
import com.project.credible_course_recommender.R
import com.project.credible_course_recommender.adapters.CourseAdapter
import com.project.credible_course_recommender.models.Payload
import com.project.credible_course_recommender.ui.CoursesViewModel
import com.project.credible_course_recommender.ui.MainActivity
import com.project.credible_course_recommender.util.Constants.Companion.QUERY_PAGE_SIZE
import com.project.credible_course_recommender.util.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchFragment : Fragment(R.layout.fragment_search),  View.OnClickListener {
    var selectedProviders: ArrayList<String> = ArrayList()
    private var tag = "testing-searchFragment"
    lateinit var viewModel : CoursesViewModel
    lateinit var courseAdapter: CourseAdapter
    lateinit var rvCourses: RecyclerView
    lateinit var cvProgress: ConstraintLayout
    lateinit var svSearch: SearchView
    lateinit var tvDecider: TextView
    lateinit var filterBtn: MaterialCardView
    lateinit var mbCoursera: MaterialButton
    lateinit var mbEdx: MaterialButton
    lateinit var mAuth: FirebaseAuth

    var queryG=""
    lateinit var filterDialog: Dialog
    lateinit var  globalPayload: Payload
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        globalPayload = Payload()
        globalPayload.uid = mAuth.uid.toString()
        viewModel = (activity as MainActivity).viewModel
        rvCourses = view.findViewById(R.id.fl)
        svSearch = view.findViewById(R.id.svSearch)
        cvProgress = view.findViewById(R.id.cvProgress)
        filterBtn = view.findViewById(R.id.filterBtn)
        mbCoursera = view.findViewById(R.id.mbCoursera)
        mbEdx = view.findViewById(R.id.mbEdx)
        mbCoursera.setOnClickListener(this)
        mbEdx.setOnClickListener(this)

        tvDecider = view.findViewById(R.id.tvDecider)
        cvProgress.visibility = View.VISIBLE
        tvDecider.text = "You might be interested in"
        filterDialog = context?.let { Dialog(it) }!!
        filterDialog.setContentView(R.layout.filter_dialog)
        filterDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setupRecyclerView()
        viewModel.payload = globalPayload
        cvProgress.visibility = View.VISIBLE

        viewModel.getCourses(globalPayload)

        filterBtn.setOnClickListener{
            filterDialog.show()
        }

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
                cvProgress.visibility = View.VISIBLE
                job?.cancel()
                job = MainScope().launch {
                    delay(500)
                    globalPayload?.let {
                        if (mbCoursera != null) {
                            if (mbCoursera.currentTextColor == Color.WHITE) {
                                context?.getDrawable(R.drawable.white_button)
                                    ?.let { it1 ->
                                        mbCoursera.setBackgroundDrawable(it1)}
                            }
                        }
                        if (mbEdx != null) {
                            if (mbEdx.currentTextColor == Color.WHITE) {
                                context?.getDrawable(R.drawable.white_button)
                                    ?.let { it1 ->
                                        mbEdx.setBackgroundDrawable(it1)}
                            }
                        }


                        mbEdx.setTextColor(Color.parseColor("#CA3568"))
                        mbCoursera.setTextColor(Color.parseColor("#CA3568"))
                        viewModel.payload = Payload()
                        globalPayload= Payload()
                        globalPayload.uid = mAuth.uid.toString()



                        viewModel.getCourses(globalPayload)
                    }
                }
                tvDecider.text = "You might be interested in"

                return true
            }

        })

        svSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                queryG = query

                callSearch(query)
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
//                queryG = newText
////              if (searchView.isExpanded() && TextUtils.isEmpty(newText)) {
//                callSearch(newText)
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
                        globalPayload.searchText = query
                        viewModel.payload = Payload()

                        viewModel.getCourses(globalPayload)
                    }
                }
            }
        })

        viewModel.searchCourses.observe(viewLifecycleOwner, Observer {

            response ->
            when(response){
                is Resource.Success -> {
                    isLoading = false

                    response.data?.let { res->
                        cvProgress.visibility = View.INVISIBLE

                        courseAdapter.differ.submitList(res.courses.toList())
                        val totalPages = res.coursesCount / QUERY_PAGE_SIZE +2
                        isLastPage =viewModel.pageNumber == totalPages
                    }
                }
                is Resource.Error -> {
                    isLoading = false

                    response.message?.let { msg->
                        Log.e("sd","sdf")
                    }
                }
                is Resource.Loading -> {
                    cvProgress.visibility = View.VISIBLE

                    isLoading = true
                    response.message?.let { msg->
                        Log.e("sd","sdf")
                    }
                }
                else -> {

                }
            }
        })
    }

    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    val scrollListener = object : RecyclerView.OnScrollListener(){
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition>=0
            val isTotalMoreThanVisible = totalItemCount>= QUERY_PAGE_SIZE

            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning
                    && isTotalMoreThanVisible && isScrolling
            if(shouldPaginate){
                viewModel.payload = globalPayload

                viewModel.getCourses(globalPayload)
                isScrolling = false
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if(newState==AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                isScrolling = true
            }
        }
    }

    private fun setupRecyclerView(){

        courseAdapter = CourseAdapter(requireContext())
        rvCourses.apply {
            adapter = courseAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@SearchFragment.scrollListener)
        }

    }

    override fun onClick(p0: View?) {
        cvProgress.visibility = View.VISIBLE

        val materialButton = p0 as? MaterialButton
        if(!selectedProviders.contains(materialButton?.text.toString())){
            selectedProviders.add(materialButton?.text.toString())
        }else{
            selectedProviders.remove(materialButton?.text.toString())

        }
        val drawable1 = context?.getDrawable(R.drawable.white_button)
        val drawable2= context?.getDrawable(R.drawable.gradient_button)

        Log.i(tag,p0.toString())
        materialButton?.let {
            val textColor = if (materialButton.currentTextColor == Color.parseColor("#CA3568")) {
                Color.WHITE // Change to red if the current text color is black
            } else {
                Color.parseColor("#CA3568") // Change to red if the current text color is black
            }
            materialButton.setTextColor(textColor)
        }

        if (materialButton != null) {
            if (materialButton.currentTextColor == Color.parseColor("#CA3568")) {
                p0?.setBackgroundDrawable(drawable1)

            } else {
                p0?.setBackgroundDrawable(drawable2)

            }

        }
        globalPayload.providerFilter = selectedProviders

        viewModel.payload = Payload(providerFilter = selectedProviders)

        viewModel.getCourses(globalPayload)
//        Toast.makeText(context,,Toast.LENGTH_SHORT).show()
    }

}