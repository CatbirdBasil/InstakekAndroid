package com.mobiledev.edu.instakek.ui.activity

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatEditText
import android.util.Log
import android.widget.Button
import com.mobiledev.edu.instakek.R
import com.mobiledev.edu.instakek.data.database.entity.Post
import com.mobiledev.edu.instakek.data.database.entity.User
import com.mobiledev.edu.instakek.ui.viewModel.PostViewModel
import com.mobiledev.edu.instakek.ui.viewModel.UserViewModel
import java.util.*

class RegisterActivity : AppCompatActivity(){

    private lateinit var register : Button
    lateinit var  firstName:AppCompatEditText
    lateinit var  secondname: AppCompatEditText
    lateinit var  email:AppCompatEditText
    lateinit var  username:AppCompatEditText
    lateinit var  password:AppCompatEditText
    private var mUserViewModel: UserViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        firstName = findViewById(R.id.name)
        secondname = findViewById(R.id.secondname)
        email = findViewById(R.id.email)
        username = findViewById(R.id.username)
        password = findViewById(R.id.passwod)
        register = findViewById(R.id.register_buttn)

        register.setOnClickListener{getRegistration()}

            mUserViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
//        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)



        }


    private fun getRegistration() {
        var user:User = User(6,username.getText().toString(),email.getText().toString(),firstName.getText().toString(),secondname.getText().toString(), Calendar.getInstance().time,"https://medialeaks.ru/wp-content/uploads/2018/11/12-1.jpg",true)
        mUserViewModel!!.registerNewUser(user)
    }


}