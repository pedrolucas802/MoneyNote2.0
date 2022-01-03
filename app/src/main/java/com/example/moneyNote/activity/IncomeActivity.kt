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
import com.example.moneyNote.adapater.IncomeAdapter
import com.example.moneyNote.module.Income
import com.example.moneyNote.util.IncomeListener
import com.example.moneyNote.util.DataBase
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class IncomeActivity : AppCompatActivity(), View.OnClickListener, IncomeListener {

    private var userId = -1
    private var userEmail = ""



    private lateinit var mCategoryList: RecyclerView
    private lateinit var mCategoryAdd: FloatingActionButton

    private lateinit var incomes: List<Income>
    private val handler = Handler(Looper.getMainLooper())




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.moneyNote.R.layout.activity_income)

        userId = intent.getIntExtra("user_id", -1)
        userEmail = intent.getStringExtra("user_email").toString()

        mCategoryList = findViewById(com.example.moneyNote.R.id.income_recyclerView)
        mCategoryList.layoutManager = LinearLayoutManager(applicationContext)

        mCategoryAdd = findViewById(com.example.moneyNote.R.id.income_floatButton_add)
        mCategoryAdd.setOnClickListener(this)

    }

    override fun onStart() {
        super.onStart()

        GlobalScope.launch {
            incomes = DataBase
                .getInstance(this@IncomeActivity)
                .getIncomeDao()
                .findAll()

            handler.post {
               val adapter = IncomeAdapter(incomes)
                adapter.setOnCategoryListener(this@IncomeActivity)
                mCategoryList.adapter = adapter
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            com.example.moneyNote.R.id.income_floatButton_add -> {
                val it = Intent(applicationContext, IncomeFormsActivity::class.java)
                it.putExtra("user_id", userId)
                it.putExtra("user_email", userEmail)
                startActivity(it)
            }
        }
    }

    override fun onCategoryItemClick(view: View, position: Int) {
        val it = Intent(applicationContext, IncomeFormsActivity::class.java)
        it.putExtra("user_id", userId)
        it.putExtra("user_email", userEmail)
        it.putExtra("category_id", incomes[position].id)
        startActivity(it)
    }

    override fun onCategoryItemLongClick(view: View, position: Int) {
        Toast.makeText(applicationContext, "Category $position long clicked", Toast.LENGTH_SHORT)
            .show()

        val alertDialog = AlertDialog.Builder(view.context).create()
        alertDialog.setTitle("Delete")
        alertDialog.setMessage("Are you sure you want to delete note $position ?")
        alertDialog.setButton("OK") { dialog, which ->
            Toast.makeText(applicationContext, "Note $position deleted, refresh to see!", Toast.LENGTH_SHORT)
                .show()

            GlobalScope.launch {
                DataBase.getInstance(applicationContext).getIncomeDao()
                    .delete(incomes[position])
                val adapter = IncomeAdapter(incomes)
                adapter.notifyItemRemoved(position)
                adapter.notifyItemRangeChanged(position,adapter.itemCount)

            }

            handler.post {  val adapter = IncomeAdapter(incomes)
                adapter.notifyItemRemoved(position)
                adapter.notifyItemRangeChanged(position,adapter.itemCount) }

        }
        alertDialog.setButton2("Cancel") { dialog, which ->
            Toast.makeText(applicationContext, "Canceled", Toast.LENGTH_SHORT)
                .show()
        }

        alertDialog.setIcon(R.drawable.moneynotepic)
        alertDialog.show()


    }



}