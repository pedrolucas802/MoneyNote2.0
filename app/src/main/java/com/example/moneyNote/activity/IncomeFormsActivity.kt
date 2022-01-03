package com.example.moneyNote.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.SpannableStringBuilder
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.moneyNote.R
import com.example.moneyNote.module.Income
import com.example.moneyNote.util.DataBase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class IncomeFormsActivity : AppCompatActivity(), View.OnClickListener {

    private var userId = -1
    private var userEmail = ""
    private var categoryId = -1


    private lateinit var mCategoryFormTitle: TextView
    private lateinit var mCategoryFormValue: TextView
    private lateinit var mCategoryFormDescription: EditText
    private lateinit var mCategoryFormButton: Button

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_income_forms)

        userId = intent.getIntExtra("user_id", -1)
        categoryId = intent.getIntExtra("category_id", -1)
        userEmail = intent.getStringExtra("user_email").toString()

        mCategoryFormTitle = findViewById(R.id.category_form_title)
        if (categoryId == -1) {
            mCategoryFormTitle.text = "New Income Note"
        } else {

            GlobalScope.launch {
                val income = DataBase
                    .getInstance(applicationContext)
                    .getIncomeDao()
                    .find(categoryId)

                handler.post {
                    mCategoryFormTitle.text = "Edit Income Note"
                    mCategoryFormValue.text = SpannableStringBuilder(income.value)
                    mCategoryFormDescription.text = SpannableStringBuilder(income.description)
                    mCategoryFormButton.text = "Update"
                }
            }

        }

        mCategoryFormValue = findViewById(R.id.incomeForm_valueInput)
        mCategoryFormDescription = findViewById(R.id.incomeForm_DescriptionInput)
        mCategoryFormButton = findViewById(R.id.incomeForm_button)
        mCategoryFormButton.setOnClickListener(this)



    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.incomeForm_button -> {

                var isFormFilled = true

                if (mCategoryFormValue.text.isEmpty()) {
                    mCategoryFormValue.error = resources.getString(R.string.fill_error)
                    isFormFilled = false

                }

                if (mCategoryFormDescription.text.isEmpty()) {
                    mCategoryFormDescription.error = resources.getString(R.string.fill_error)
                    isFormFilled = false
                }

                if (isFormFilled) {

                    val value = mCategoryFormValue.text.toString()
                    val description = mCategoryFormDescription.text.toString()


                    GlobalScope.launch {

                        if (categoryId == -1) {
                            val newIncome =
                                Income(value = value, description = description, userId = userId)

                            DataBase.getInstance(applicationContext).getIncomeDao()
                                .insert(newIncome)

                            handler.post {
                                Toast.makeText(
                                    applicationContext,
                                    "Income $value registered successfully!",
                                    Toast.LENGTH_SHORT
                                ).show()
                                finish()
                            }
                        } else {
                            val newIncome =
                                Income(id = categoryId, value = value, description = description, userId = userId)

                            DataBase.getInstance(applicationContext).getIncomeDao()
                                .update(newIncome)

                            handler.post {
                                Toast.makeText(
                                    applicationContext,
                                    "Income $value updated successfully!",
                                    Toast.LENGTH_SHORT
                                ).show()
                                finish()
                            }


                        }

                    }

                }


            }
        }
    }
}