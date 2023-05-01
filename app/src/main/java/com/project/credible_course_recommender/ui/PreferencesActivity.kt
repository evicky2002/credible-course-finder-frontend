package com.project.credible_course_recommender.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CalendarContract.Colors
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.project.credible_course_recommender.R
import com.project.credible_course_recommender.api.RetrofitInstance
import com.project.credible_course_recommender.models.InterestsPayload
import com.project.credible_course_recommender.models.InterestsResponse
import com.project.credible_course_recommender.models.LoginPayload
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class PreferencesActivity : AppCompatActivity(),  View.OnClickListener {
    val TAG = "testing-PreferencesActivity"
    var selectedSkills: ArrayList<String> = ArrayList()
    lateinit var btnNext: MaterialButton
    private lateinit var mFirebaseAuth: FirebaseAuth
    private var btnSemOne: MaterialButton? = null
    private var btnSemTwo: MaterialButton? = null
    private var btnSemThree: MaterialButton? = null
    private var btnSemFour: MaterialButton? = null
    private var btnSemFive: MaterialButton? = null
    private var btnSemSix: MaterialButton? = null
    private var btnSemSeven: MaterialButton? = null
    private var btnSemEight: MaterialButton? = null
    lateinit var cvProgress: ConstraintLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        hideSystemBars()
        setContentView(R.layout.activity_preferences)
        mFirebaseAuth = FirebaseAuth.getInstance()
        btnNext= findViewById(R.id.btnNext)
        btnSemOne = findViewById(R.id.btnSemOne)
        btnSemTwo = findViewById(R.id.btnSemTwo)
        btnSemThree = findViewById(R.id.btnSemThree)
        btnSemFour = findViewById(R.id.btnSemFour)
        btnSemFive = findViewById(R.id.btnSemFive)
        btnSemSix = findViewById(R.id.btnSemSix)
        btnSemSeven = findViewById(R.id.btnSemSeven)
        btnSemEight = findViewById(R.id.btnSemEight)
        btnSemOne?.setOnClickListener(this)
        btnSemTwo?.setOnClickListener(this)
        btnSemThree?.setOnClickListener(this)
        btnSemFour?.setOnClickListener(this)
        btnSemFive?.setOnClickListener(this)
        btnSemSix?.setOnClickListener(this)
        btnSemSeven?.setOnClickListener(this)
        btnSemEight?.setOnClickListener(this)
        btnNext?.setOnClickListener(this)
        var ex : Array<MaterialButton?> = arrayOf(btnSemOne,btnSemTwo,
        btnSemThree,btnSemFour,btnSemFive,btnSemSix,btnSemSeven,btnSemEight)
        cvProgress= findViewById(com.project.credible_course_recommender.R.id.cvProgress)
        cvProgress.visibility = View.VISIBLE
        cvProgress?.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.white))


        lifecycleScope.launchWhenCreated {
            val response = try{
                RetrofitInstance.api.getTopSkills()
            } catch (e: IOException){
                return@launchWhenCreated
            }catch (e: HttpException){
                return@launchWhenCreated
            }
            if(response.isSuccessful && response.body()!=null){
                Log.i(TAG,response.body().toString())
                response.body()!!.skills.forEachIndexed { index,it->
                    Log.i(TAG,it.toString())
                    ex[index]?.text = it._id

                }
                cvProgress.visibility = View.INVISIBLE

            }else{
                Log.i(TAG,"response is not successful")
            }
        }
    }

    @SuppressLint("ResourceType")
    override fun onClick(view: View?) {
        when(view?.id){
            R.id.btnSemOne -> {
                if(btnSemOne?.textColors == ColorStateList.valueOf(resources.getColor(R.color.white))){
                    btnSemOne?.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.white))
                    btnSemOne?.setTextColor(getResources().getColor(R.color.red_400))
                    selectedSkills.add(btnSemOne!!.text as String)

                }else{
                    btnSemOne?.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.no_color))
                    btnSemOne?.setTextColor(getResources().getColor(R.color.white))
                    selectedSkills.remove(btnSemOne!!.text as String)

                }
            }
            R.id.btnSemTwo -> {
                if(btnSemTwo?.textColors == ColorStateList.valueOf(resources.getColor(R.color.white))){
                    btnSemTwo?.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.white))
                    btnSemTwo?.setTextColor(getResources().getColor(R.color.red_400))
                    selectedSkills.add(btnSemTwo!!.text as String)

                }else{
                    btnSemTwo?.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.no_color))
                    btnSemTwo?.setTextColor(getResources().getColor(R.color.white))
                    selectedSkills.remove(btnSemTwo!!.text as String)

                }
            }           R.id.btnSemThree -> {
                if(btnSemThree?.textColors == ColorStateList.valueOf(resources.getColor(R.color.white))){
                    btnSemThree?.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.white))
                    btnSemThree?.setTextColor(getResources().getColor(R.color.red_400))
                    selectedSkills.add(btnSemThree!!.text as String)

                }else{
                    btnSemThree?.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.no_color))
                    btnSemThree?.setTextColor(getResources().getColor(R.color.white))
                    selectedSkills.remove(btnSemThree!!.text as String)

                }
            } R.id.btnSemFour -> {
                if(btnSemFour?.textColors == ColorStateList.valueOf(resources.getColor(R.color.white))){
                    btnSemFour?.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.white))
                    btnSemFour?.setTextColor(getResources().getColor(R.color.red_400))
                    selectedSkills.add(btnSemFour!!.text as String)

                }else{
                    btnSemFour?.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.no_color))
                    btnSemFour?.setTextColor(getResources().getColor(R.color.white))
                    selectedSkills.remove(btnSemFour!!.text as String)

                }
            }R.id.btnSemFive -> {
                if(btnSemFive?.textColors == ColorStateList.valueOf(resources.getColor(R.color.white))){
                    btnSemFive?.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.white))
                    btnSemFive?.setTextColor(getResources().getColor(R.color.red_400))
                    selectedSkills.add(btnSemFive!!.text as String)

                }else{
                    btnSemFive?.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.no_color))
                    btnSemFive?.setTextColor(getResources().getColor(R.color.white))
                    selectedSkills.remove(btnSemFive!!.text as String)

                }
            }R.id.btnSemSix -> {
                if(btnSemSix?.textColors == ColorStateList.valueOf(resources.getColor(R.color.white))){
                    btnSemSix?.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.white))
                    btnSemSix?.setTextColor(getResources().getColor(R.color.red_400))
                    selectedSkills.add(btnSemSix!!.text as String)

                }else{
                    btnSemSix?.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.no_color))
                    btnSemSix?.setTextColor(getResources().getColor(R.color.white))
                    selectedSkills.remove(btnSemSix!!.text as String)

                }
            }R.id.btnSemSeven -> {
                if(btnSemSeven?.textColors == ColorStateList.valueOf(resources.getColor(R.color.white))){
                    btnSemSeven?.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.white))
                    btnSemSeven?.setTextColor(getResources().getColor(R.color.red_400))
                    selectedSkills.add(btnSemSeven!!.text as String)

                }else{
                    btnSemSeven?.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.no_color))
                    btnSemSeven?.setTextColor(getResources().getColor(R.color.white))
                    selectedSkills.remove(btnSemSeven!!.text as String)

                }
            }R.id.btnSemEight -> {
                if(btnSemEight?.textColors == ColorStateList.valueOf(resources.getColor(R.color.white))){
                    btnSemEight?.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.white))
                    btnSemEight?.setTextColor(getResources().getColor(R.color.red_400))
                    selectedSkills.add(btnSemEight!!.text as String)

                }else{
                    btnSemEight?.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.no_color))
                    btnSemEight?.setTextColor(getResources().getColor(R.color.white))
                    selectedSkills.remove(btnSemEight!!.text as String)

                }
            }
            R.id.btnNext ->{
                cvProgress.visibility = View.VISIBLE
                cvProgress?.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.no_color))

                var res: Response<InterestsResponse>
                MainScope().launch {
                    res = RetrofitInstance.api.addInterests(InterestsPayload(mFirebaseAuth.uid!!,selectedSkills))
                    if(res.body()?.status.equals("ok")){
                        cvProgress.visibility = View.INVISIBLE

//                        Toast.makeText(this@PreferencesActivity,"Welcome back ${mFirebaseAuth.currentUser?.displayName}",
//                            Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@PreferencesActivity, MainActivity::class.java))
                        finish()
                    }else{
//                        Toast.makeText(this@SignInActivity,"User registered", Toast.LENGTH_SHORT).show()
//                        startActivity(Intent(this@SignInActivity, MainActivity::class.java))
//                        finish()

                    }

                }

            }
        }
        Log.i(TAG,selectedSkills.toString())


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