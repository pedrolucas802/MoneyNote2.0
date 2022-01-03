package com.example.moneyNote.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.moneyNote.R
import com.example.moneyNote.module.User
import com.example.moneyNote.util.DataBase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mRegisterName: EditText
    private lateinit var mRegisterEmail: EditText
    private lateinit var mRegisterPhone: EditText
    private lateinit var mRegisterPassword: EditText
    private lateinit var mRegisterPasswordConfirmation: EditText
    private lateinit var mRegisterSignUp: Button

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mRegisterName = findViewById(R.id.register_name)
        mRegisterEmail = findViewById(R.id.register_email)
        mRegisterPhone = findViewById(R.id.register_phone)
        mRegisterPassword = findViewById(R.id.register_password)
        mRegisterPasswordConfirmation = findViewById(R.id.register_passwordConfirm)
        mRegisterSignUp = findViewById(R.id.register_confirmButton)
        mRegisterSignUp.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.register_confirmButton -> {

                if (formIsFilled()) {
                    val name = mRegisterName.text.toString()
                    val email = mRegisterEmail.text.toString()
                    val phone = mRegisterPhone.text.toString()
                    val password = mRegisterPassword.text.toString()
                    val passwordConfirmation = mRegisterPasswordConfirmation.text.toString()

                    if (password == passwordConfirmation) {

                        val user = User(name = name, email = email, password = password, phone = phone)

                        GlobalScope.launch {

                            val userAux = DataBase.getInstance(applicationContext).getUserDao().findByEmail(email)

                            if (userAux == null) {
                                DataBase.getInstance(applicationContext).getUserDao().insert(user)

                                handler.post{
                                    Toast.makeText(
                                        applicationContext,
                                        "User registered successfully!",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    finish()
                                }

                            } else {
                                handler.post{   Toast.makeText(
                                    applicationContext,
                                    "The E-mail address provided is already in use.",
                                    Toast.LENGTH_SHORT
                                ).show()}

                            }

                        }

                    } else {
                        formError(mRegisterPasswordConfirmation, R.string.passwordMatch_error)
                    }

                }

            }

        }
    }

    private fun formIsFilled(): Boolean {
        var isFormFilled = true

        if (mRegisterName.text.isEmpty()) {
            isFormFilled = formError(mRegisterName, R.string.register_fill_error)
        }
        if (mRegisterEmail.text.isEmpty()) {
            isFormFilled = formError(mRegisterEmail, R.string.register_fill_error)
        }
        if (mRegisterPhone.text.isEmpty()) {
            isFormFilled = formError(mRegisterPhone, R.string.register_fill_error)
        }
        if (mRegisterPassword.text.isEmpty()) {
            isFormFilled = formError(mRegisterPassword, R.string.register_fill_error)
        }
        if (mRegisterPasswordConfirmation.text.isEmpty()) {
            isFormFilled = formError(mRegisterPasswordConfirmation, R.string.register_fill_error)
        }

        return isFormFilled
    }

    private fun formError(editText: EditText, stringId: Int): Boolean {
        editText.error = resources.getString(stringId)
        return false
    }
}