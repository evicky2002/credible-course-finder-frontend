package com.project.credible_course_recommender.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.view.marginStart
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.project.credible_course_recommender.R
import com.project.credible_course_recommender.api.RetrofitInstance
import com.project.credible_course_recommender.models.LoginPayload
import com.project.credible_course_recommender.models.UserPayload
import com.project.credible_course_recommender.models.UserResponse
import com.project.credible_course_recommender.ui.CircleTransform
import com.project.credible_course_recommender.ui.MainActivity
import com.project.credible_course_recommender.ui.PreferencesActivity
import com.project.credible_course_recommender.ui.SignInActivity
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import retrofit2.Response


class ProfileFragment : Fragment(com.project.credible_course_recommender.R.layout.fragment_profile) {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var btnSignOut: Button
    private lateinit var tvUser: TextView
    private lateinit var tvEmail: TextView
    private lateinit var ivProfile: ImageView
    private lateinit var llInterests: LinearLayout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnSignOut = view.findViewById(com.project.credible_course_recommender.R.id.btnSignOut)
        tvUser = view.findViewById(com.project.credible_course_recommender.R.id.tvUser)
        tvEmail = view.findViewById(com.project.credible_course_recommender.R.id.tvEmail)
        ivProfile = view.findViewById(com.project.credible_course_recommender.R.id.ivProfile)
        llInterests = view.findViewById(com.project.credible_course_recommender.R.id.llInterests)
        var arr = arrayOf("LeadershipandManagement")

        var res: Response<UserResponse>
        MainScope().launch {
            res = RetrofitInstance.api.getInterests(UserPayload(mAuth.uid!!))
            if(res.body()?.status.equals("ok") ){
                arr = res.body()?.user?.interests?.toTypedArray()!!
                Log.i("testing-ProfileFragment",res.body()?.user.toString()!!)
                Toast.makeText(context,"success",
                    Toast.LENGTH_SHORT).show()
                for (i in 0 until arr.size) {
                    val text = TextView(context)
                    text.setTextColor(getResources().getColor(R.color.white))
                    text.setText(arr.get(i))
                    llInterests.addView(text)
                }
            }else{


                Toast.makeText(context,"error", Toast.LENGTH_SHORT).show()


            }

        }

        mAuth = FirebaseAuth.getInstance()
        val currentUser: FirebaseUser = mAuth.getCurrentUser()!!
        val name = currentUser.displayName
        val email = currentUser.email
        tvUser.text = name
        tvEmail.text =email
        Glide.with(requireContext())
            .load(currentUser.photoUrl) // add your image url
            .transform(CircleTransform(activity)) // applying the image transformer
            .into(ivProfile)

        btnSignOut.setOnClickListener(View.OnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(activity, SignInActivity::class.java)
            startActivity(intent)
        })

    }

}