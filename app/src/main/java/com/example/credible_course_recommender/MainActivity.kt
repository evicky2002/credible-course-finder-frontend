package com.example.credible_course_recommender

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.lifecycleScope
import retrofit2.HttpException
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private val TAG = "test-mainActivity"

    private lateinit var tvHelloWorld: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        hideSystemBars()
        setContentView(R.layout.activity_main)
        tvHelloWorld = findViewById(com.example.credible_course_recommender.R.id.tvHelloWorld) as TextView

        lifecycleScope.launchWhenCreated {
            val response = try{
                RetrofitInstance.api.getCourses()
            } catch (e: IOException){
                return@launchWhenCreated
            }catch (e: HttpException){
                return@launchWhenCreated
            }
            if(response.isSuccessful && response.body()!=null){
                Log.i(TAG,response.body().toString())
                tvHelloWorld.setText(response.body().toString())
            }else{
                Log.i(TAG,"response is not successful")
            }
        }
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