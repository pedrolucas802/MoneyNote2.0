package com.example.moneyNote.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.moneyNote.R
import com.example.moneyNote.util.DataBase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mEmailLogin: EditText
    private lateinit var mPasswordLogin: EditText
    private lateinit var mLoginButton: Button
    private lateinit var mSignUpButton: Button

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mEmailLogin = findViewById(R.id.loginEmail_input)
        mPasswordLogin = findViewById(R.id.loginPassword_input)
        mLoginButton = findViewById(R.id.button_Login)
        mLoginButton.setOnClickListener(this)
        mSignUpButton = findViewById(R.id.login_signupButton)
        mSignUpButton.setOnClickListener(this)


    }

    override fun onClick(v: View?) {

        when (v?.id) {

            R.id.button_Login -> {


                var isFormFilled = true

                if (mEmailLogin.text.isEmpty()) {
                    isFormFilled = formError(mEmailLogin, R.string.loginForm_fill_error)
                }

                if (mPasswordLogin.text.isEmpty()) {
                    isFormFilled = formError(mPasswordLogin, R.string.loginForm_fill_error)
                }

                if (isFormFilled) {
                    val email = mEmailLogin.text.toString()
                    val password = mPasswordLogin.text.toString()

                    GlobalScope.launch {
                        val user =
                            DataBase.getInstance(applicationContext).getUserDao().findByEmail(email)

                        if (user != null && user.password == password) {
                            val it = Intent(applicationContext, MainActivity::class.java)
                            startActivity(it)
                            it.putExtra("user_id", user.id)
                            it.putExtra("user_email",user.email)
                            finish()
                        } else {
                            handler.post {
                                Toast.makeText(
                                    applicationContext,
                                    "Invalid E-mail or Password.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }


                }

            }

            R.id.login_signupButton -> {
                val it = Intent(this, RegisterActivity::class.java)
                startActivity(it)
            }


        }

    }

    private fun formError(editText: EditText, stringId: Int): Boolean {
        editText.error = resources.getString(stringId)
        return false
    }
}