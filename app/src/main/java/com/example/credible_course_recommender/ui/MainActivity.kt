package com.example.credible_course_recommender.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.credible_course_recommender.R
import com.example.credible_course_recommender.api.RetrofitInstance
import com.example.credible_course_recommender.repository.CourseRepository
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.HttpException
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private val TAG = "test-mainActivity"

    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var mainNavHostFragment: Fragment
    lateinit var viewModel: CoursesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        hideSystemBars()
        setContentView(R.layout.activity_main)
        val courseRepository = CourseRepository()
        val viewModelProviderFactory =CourseViewModelProviderFactory(application,courseRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(CoursesViewModel::class.java)

        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        mainNavHostFragment = supportFragmentManager.findFragmentById(R.id.mainNavHostFragment) as NavHostFragment
        mainNavHostFragment.findNavController()
        bottomNavigationView.setupWithNavController(mainNavHostFragment.findNavController())
//        lifecycleScope.launchWhenCreated {
//            val response = try{
//                RetrofitInstance.api.getCourses()
//            } catch (e: IOException){
//                return@launchWhenCreated
//            }catch (e: HttpException){
//                return@launchWhenCreated
//            }
//            if(response.isSuccessful && response.body()!=null){
//                Log.i(TAG,response.body().toString())
//            }else{
//                Log.i(TAG,"response is not successful")
//            }
//        }
    }

    private fun hideSystemBars() {
        val windowInsetsController =
            ViewCompat.getWindowInsetsController(window.decorView) ?: return
        // Configure the behavior of the hidden system bars
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        // Hide both the status bar and the navigation bar
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
    }

}