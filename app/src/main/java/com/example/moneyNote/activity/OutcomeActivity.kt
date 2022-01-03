package com.example.moneyNote.activity

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moneyNote.R
import com.example.moneyNote.adapater.OutcomeAdapter
import com.example.moneyNote.module.Outcome
import com.example.moneyNote.util.DataBase
import com.example.moneyNote.util.OutcomeListener
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class OutcomeActivity : AppCompatActivity(), View.OnClickListener, OutcomeListener {

    private var userId = -1
    private var userEmail = ""



    private lateinit var mCategoryList: RecyclerView
    private lateinit var mCategoryAdd: FloatingActionButton

    private lateinit var outcomes: List<Outcome>
    private val handler = Handler(Looper.getMainLooper())





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_outcome)

        userId = intent.getIntExtra("user_id", -1)
        userEmail = intent.getStringExtra("user_email").toString()

        mCategoryList = findViewById(com.example.moneyNote.R.id.outcome_recyclerView)
        mCategoryList.layoutManager = LinearLayoutManager(this@OutcomeActivity)

        mCategoryAdd = findViewById(com.example.moneyNote.R.id.outcome_floatButton_add)
        mCategoryAdd.setOnClickListener(this@OutcomeActivity)

    }

    override fun onStart() {
        super.onStart()

        GlobalScope.launch {
            outcomes = DataBase
                .getInstance(applicationContext)
                .getOutcomeDao()
                .findAll()

            handler.post {
               val adapter = OutcomeAdapter(outcomes)
                adapter.setOnCategoryListener(this@OutcomeActivity)
                mCategoryList.adapter = adapter
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            com.example.moneyNote.R.id.outcome_floatButton_add -> {
                val it = Intent(applicationContext, OutcomeFormsActivity::class.java)
                it.putExtra("user_id", userId)
                it.putExtra("user_email", userEmail)
                startActivity(it)
            }
        }
    }

    override fun onCategoryItemClick(view: View, position: Int) {
        val it = Intent(applicationContext, OutcomeActivity::class.java)
        it.putExtra("user_id", userId)
        it.putExtra("user_email", userEmail)
        it.putExtra("category_id", outcomes[position].id)
        startActivity(it)
    }

    override fun onCategoryItemLongClick(view: View, position: Int) {
        Toast.makeText(applicationContext, "Category $position long clicked", Toast.LENGTH_SHORT)
            .show()

        val alertDialog = AlertDialog.Builder(view.context).create()
        alertDialog.setTitle("Delete")
        alertDialog.setMessage("Are you sure you want to delete note $position ?")
        alertDialog.setButton("OK") { dialog, which ->
            Toast.makeText(applicationContext, "Note $position deleted", Toast.LENGTH_SHORT)
                .show()

            GlobalScope.launch {
                DataBase.getInstance(applicationContext).getOutcomeDao()
                    .delete(outcomes[position])

                val adapter = OutcomeAdapter(outcomes)
                adapter.notifyItemRemoved(position)

            }
        }
        alertDialog.setButton2("Cancel") { dialog, which ->
            Toast.makeText(applicationContext, "Canceled", Toast.LENGTH_SHORT)
                .show()
        }

        alertDialog.setIcon(R.drawable.moneynotepic)
        alertDialog.show()


    }



}