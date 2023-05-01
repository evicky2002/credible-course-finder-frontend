package com.project.credible_course_recommender.ui.fragments

import android.os.Build
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.project.credible_course_recommender.R
import com.project.credible_course_recommender.ui.CoursesViewModel
import com.project.credible_course_recommender.ui.MainActivity


class CourseFragment : Fragment(R.layout.fragment_course) {
    lateinit var viewModel : CoursesViewModel
    lateinit var webView : WebView
    lateinit var cvProgress: ProgressBar

    val args: CourseFragmentArgs by navArgs()


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        webView = view.findViewById(R.id.webView)
        cvProgress = view.findViewById(R.id.cvProgress)
        cvProgress.visibility = View.VISIBLE

        val course = args.course
        webView.apply {
//            webViewClient = WebViewClient()
//            loadUrl(course.objectUrl)
//            webViewClient.onPageFinished(this,course.objectUrl)
            webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    cvProgress.visibility = View.INVISIBLE

                }
            }
            loadUrl(course.objectUrl)


        }
//        webView.setWebViewClient(object : WebViewClient() {
//            override fun onPageFinished(view: WebView, url: String) {
//                webView.loadUrl(course.objectUrl)
//                cvProgress.visibility = View.INVISIBLE
//
//            }
//        })



    }
}