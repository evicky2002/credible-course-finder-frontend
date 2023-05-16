package com.project.credible_course_recommender.models

import com.project.credible_course_recommender.util.Constants.Companion.QUERY_PAGE_SIZE

data class Payload(var searchText:String= "",
var pageNumber:Int =1, var itemsPerPage:Int = QUERY_PAGE_SIZE, var providerFilter : ArrayList<String> = ArrayList(), var uid: String = "")