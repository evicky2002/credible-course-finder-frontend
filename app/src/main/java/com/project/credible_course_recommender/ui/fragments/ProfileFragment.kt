package com.project.credible_course_recommender.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.project.credible_course_recommender.R


class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private lateinit var btnSignOut: Button
    private lateinit var mFirebaseAuth: FirebaseAuth
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mFirebaseAuth = FirebaseAuth.getInstance()
        btnSignOut = view.findViewById(R.id.btnSignOut)
        btnSignOut.setOnClickListener{
            mFirebaseAuth.signOut()
            activity?.finish()
        }

    }

}