package com.example.moneyNote.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.moneyNote.R

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var userId = -1
    private var userEmail = ""
    private lateinit var mIncomeButton: Button
    private lateinit var mOutcomeButton: Button
    private lateinit var mGraphButton: Button
    private lateinit var mGraph2Button: Button




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userId = intent.getIntExtra("user_id",-1)
        userEmail = intent.getStringExtra("user_email").toString()

        mIncomeButton = findViewById(R.id.main_income_button)
        mIncomeButton.setOnClickListener(this)

        mOutcomeButton = findViewById(R.id.main_outcome_button)
        mOutcomeButton.setOnClickListener(this)

        mGraphButton = findViewById(R.id.main_graphs_button)
        mGraphButton.setOnClickListener(this)

        mGraph2Button = findViewById(R.id.main_graphs2_button)
        mGraph2Button.setOnClickListener(this)




    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.main_income_button -> {
                val it = Intent(applicationContext, IncomeActivity::class.java)
                it.putExtra("user_id", userId)
                it.putExtra("user_email",userEmail)
                startActivity(it)
            }

            R.id.main_outcome_button -> {
                val it = Intent(applicationContext, OutcomeActivity::class.java)
                it.putExtra("user_id", userId)
                it.putExtra("user_email",userEmail)
                startActivity(it)
            }

            R.id.main_graphs_button -> {
                val it = Intent(applicationContext, GraphActivity::class.java)
                it.putExtra("user_id", userId)
                it.putExtra("user_email",userEmail)
                startActivity(it)
            }

            R.id.main_graphs2_button -> {
                val it = Intent(applicationContext, Graph2Activity::class.java)
                it.putExtra("user_id", userId)
                it.putExtra("user_email",userEmail)
                startActivity(it)
            }
        }
    }
}