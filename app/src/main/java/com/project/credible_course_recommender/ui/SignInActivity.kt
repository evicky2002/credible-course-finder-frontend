package com.project.credible_course_recommender.ui

import LoginResponse
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.HapticFeedbackConstants
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.card.MaterialCardView
import androidx.core.view.WindowInsetsControllerCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.project.credible_course_recommender.R
import com.project.credible_course_recommender.api.RetrofitInstance
import com.project.credible_course_recommender.models.LoginPayload
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import retrofit2.Response

class SignInActivity : AppCompatActivity() {
    private lateinit var btnSignIn: MaterialCardView
    private lateinit var cvProgress: ConstraintLayout
    private val TAG = "testing-SignInActivity"
    private val RC_SIGN_IN = 1
    private lateinit var mFirebaseAuth: FirebaseAuth
    private lateinit var mSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        hideSystemBars()
        setContentView(R.layout.activity_sign_in)
        btnSignIn = findViewById(R.id.btnSignIn)
        cvProgress = findViewById(com.project.credible_course_recommender.R.id.cvProgress)
        mFirebaseAuth = FirebaseAuth.getInstance()
        cvProgress.visibility = View.INVISIBLE

        val gso: GoogleSignInOptions =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        mSignInClient = GoogleSignIn.getClient(this, gso)
        btnSignIn.setOnClickListener {
            cvProgress.visibility = View.VISIBLE

            btnSignIn.isClickable = false
            it.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
            signIn()
        }
    }

    private fun signIn() {
        val signInIntent = mSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> =
                GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account: GoogleSignInAccount = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account)
            } catch (e: ApiException) {
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }
    @SuppressLint("LongLogTag")
    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.getIdToken(), null)
        mFirebaseAuth.signInWithCredential(credential)
            .addOnSuccessListener(this) { authResult: AuthResult? ->
                Log.i(TAG,"Logged in")
                var res: Response<LoginResponse>
                MainScope().launch {
                    res = RetrofitInstance.api.loginUser(LoginPayload(mFirebaseAuth.uid!!))
                    if(res.body()?.exisitingUser == true){
                        cvProgress.visibility = View.INVISIBLE

                        Toast.makeText(this@SignInActivity,"Welcome back ${mFirebaseAuth.currentUser?.displayName}",Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@SignInActivity, MainActivity::class.java))
                        finish()
                    }else{
                        cvProgress.visibility = View.INVISIBLE

                        Toast.makeText(this@SignInActivity,"User registered",Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@SignInActivity, PreferencesActivity::class.java))
                        finish()

                    }

                }


            }
            .addOnFailureListener(
                this
            ) { e: Exception? ->
                Toast.makeText(
                    this, "Authentication failed.",
                    Toast.LENGTH_SHORT
                ).show()
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

