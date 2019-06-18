package com.mobiledev.edu.instakek.ui.activity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.auth0.android.jwt.JWT
import com.mobiledev.edu.instakek.R
import com.mobiledev.edu.instakek.data.network.request.LoginRequest
import com.mobiledev.edu.instakek.data.network.requestApi.AuthRequests
import com.mobiledev.edu.instakek.data.network.response.LoginResponse
import com.mobiledev.edu.instakek.data.network.utils.ApiEndpoints
import com.mobiledev.edu.instakek.utils.AuthUtils
import com.mobiledev.edu.instakek.utils.extentions.makeInvisible
import com.mobiledev.edu.instakek.utils.extentions.makeVisible
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {

    companion object {
        private val TAG = LoginActivity::class.qualifiedName
    }

    private var mAuthApi: AuthRequests? = null
    //    private var mUserRepository: UserRepository? = null
    private var mLoginCallback: Call<LoginResponse>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        password.setOnEditorActionListener(TextView.OnEditorActionListener { _, id, _ ->
            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                attemptLogin()
                return@OnEditorActionListener true
            }
            false
        })

        email_sign_in_button.setOnClickListener { attemptLogin() }
        mAuthApi = ApiEndpoints.Auth
//        mUserRepository = UserRepositoryImpl(getContext())

        AuthUtils.clearUserData(this)
        AuthUtils.initUserData(this)

        //TODO enable auth
        if (!AuthUtils.DEFAULT_JWT_TOKEN.equals(AuthUtils.TOKEN)
                && !AuthUtils.DEFAULT_USER_ID.equals(AuthUtils.CURRENT_USER_ID)) {
//            mUserRepository!!.getById(AuthUtils.CURRENT_USER_ID).observe(this, Observer<User> { user ->
//                Log.d(TAG, "User: $user")
//                if (user != null) {
//                    processToHomeActivity()
//                }
//            })
            processToHomeActivity()
        }

        create_acc.setOnClickListener { getToRegister() }
    }

//    override fun onResume() {
//        super.onResume()
//
//        mUserRepository!!.invalidateData()
//    }

    private fun attemptLogin() {
        if (mLoginCallback != null) {
            if (mLoginCallback!!.isExecuted) return
            else mLoginCallback = null
        }

        Log.d(TAG, "Attempting to log in")

        // Reset errors.
        usernameOrEmail.error = null
        password.error = null

        // Store values at the time of the login attempt.
        val usernameOrEmailStr = usernameOrEmail.text.toString()
        val passwordStr = password.text.toString()

        var cancel = false
        var focusView: View? = null

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(passwordStr) && !isPasswordValid(passwordStr)) {
            password.error = getString(R.string.error_invalid_password)
            focusView = password
            cancel = true
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(usernameOrEmailStr)) {
            usernameOrEmail.error = getString(R.string.error_field_required)
            focusView = usernameOrEmail
            cancel = true
        } else if (!isEmailValid(usernameOrEmailStr)) {
            usernameOrEmail.error = getString(R.string.error_invalid_email)
            focusView = usernameOrEmail
            cancel = true
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView?.requestFocus()
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true)

            //mAuthTask = UserLoginTask(usernameOrEmailStr, passwordStr)
            //mAuthTask!!.execute(null as Void?)

            mLoginCallback = mAuthApi!!
                    .login(LoginRequest(usernameOrEmailStr, passwordStr))
            mLoginCallback!!.enqueue(object : Callback<LoginResponse> {

                override fun onResponse(call: Call<LoginResponse>?, response: Response<LoginResponse>?) {

                    if (response!!.isSuccessful) {
                        val token = response.body()
                        AuthUtils.saveJwtToken(getContext(), token.accessToken)
                        val jwt = JWT(token.accessToken)
                        AuthUtils.saveCurrentUserId(getContext(), jwt.subject!!.toLong())
                        processToHomeActivity()

//                        mUserRepository!!.getById(AuthUtils.CURRENT_USER_ID).observe(getContext(), Observer<User> { user ->
//                            Log.d(TAG, "User: $user")
//                            if (user != null) {
//                                processToHomeActivity()
//                            }
//                        })
                    } else {
                        Log.d(TAG, "Error occurred while login. Error code: ${response.code()}")
                        password.error = getString(R.string.error_incorrect_password)
                        password.requestFocus()
                        mLoginCallback = null
                    }

                    showProgress(false)
                }

                override fun onFailure(call: Call<LoginResponse>?, t: Throwable?) {
                    Log.d(TAG, "Error occurred while login", t)
                    showProgress(false)
                    mLoginCallback = null
                }
            })

        }
    }

    fun getContext() = this

    private fun isEmailValid(email: String): Boolean {
        //TODO: Replace this with your own logic
        //return email.contains("@")
        return true
    }

    private fun isPasswordValid(password: String): Boolean {
        //TODO: Replace this with your own logic
        return password.length > 4
    }

    private fun showProgress(show: Boolean) {

        val shortAnimTime = resources.getInteger(android.R.integer.config_shortAnimTime).toLong()

        if (show) login_form.makeInvisible()
        else login_form.makeVisible()

        login_form.animate()
                .setDuration(shortAnimTime)
                .alpha((if (show) 0 else 1).toFloat())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {

                        if (show) login_form.makeInvisible()
                        else login_form.makeVisible()
                    }
                })

        if (show) login_progress.makeVisible()
        else login_progress.makeInvisible()

        login_progress.animate()
                .setDuration(shortAnimTime)
                .alpha((if (show) 1 else 0).toFloat())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        if (show) login_progress.makeVisible()
                        else login_progress.makeInvisible()
                    }
                })

    }

    private fun processToHomeActivity() {
        var intent: Intent = Intent(this, HomeActivity::class.java)

        startActivity(intent)
        finish()
    }

    private fun getToRegister() {
        var intent: Intent = Intent(this, RegisterActivity::class.java)

        startActivity(intent)
        finish()
    }
}
