package com.example.moneyNote.activity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.moneyNote.R
import com.example.moneyNote.adapater.IncomeAdapter
import com.example.moneyNote.module.Income
import com.example.moneyNote.module.Outcome
import com.example.moneyNote.util.DataBase
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Graph2Activity : AppCompatActivity() {

    private lateinit var lineChart: LineChart
    private var incomeList = ArrayList<Income>()
    private var outcomeList = ArrayList<Outcome>()

    private val handler = Handler(Looper.getMainLooper())

    private lateinit var lineDataSet: LineDataSet
    private lateinit var lineData: LineData


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graph2)

        lineChart = findViewById(R.id.lineChart)

//        setLineChartData()

    }

    fun setLineChartData(){
        val xvalue = ArrayList<String>()
        xvalue.add("11.00 AM")
        xvalue.add("12.00 AM")
        xvalue.add("1.00 AM")
        xvalue.add("3.00 AM")
        xvalue.add("7.00 AM")

        val lineEntry = ArrayList<Entry>()
        lineEntry.add(Entry(20f, 1F))
        lineEntry.add(Entry(50f, 2F))
        lineEntry.add(Entry(60f, 3F))

        val lineDataSet = LineDataSet(lineEntry,"First")
        lineDataSet.color = Color.GREEN


        val data = LineData(lineDataSet)

        lineChart.data = data
        lineChart.setBackgroundColor(Color.WHITE)
        lineChart.animateXY(3000,3000)

    }

    override fun onStart() {
        super.onStart()

        GlobalScope.launch {
            incomeList = DataBase
                .getInstance(applicationContext)
                .getIncomeDao()
                .findAll() as ArrayList<Income>

            outcomeList = DataBase
                .getInstance(applicationContext)
                .getOutcomeDao()
                .findAll() as ArrayList<Outcome>

        handler.post{

            //INCOMES
            val lineEntry = ArrayList<Entry>()
            for (i in 0 until incomeList.size) {
                val number = incomeList[i].value
                val aux = i
                lineEntry.add(Entry(aux.toFloat(),incomeList[i].value.toFloat()))
            }
            val lineDataSet = LineDataSet(lineEntry,"Income")
            lineDataSet.color = Color.GREEN


            //OUTCOMES

            val lineEntry2 = ArrayList<Entry>()
            for (i in 0 until outcomeList.size) {
                val number = outcomeList[i].value
                val aux = i
                lineEntry2.add(Entry(aux.toFloat(),outcomeList[i].value.toFloat()))
            }

            val lineDataSet2 = LineDataSet(lineEntry2,"Outcome")
            lineDataSet2.color = Color.RED

            //Final data set
            val finalDataSet = ArrayList<LineDataSet>()
            finalDataSet.add(lineDataSet)
            finalDataSet.add(lineDataSet2)

            val data = LineData(finalDataSet as List<ILineDataSet>?)

            lineChart.data = data
            lineChart.setBackgroundColor(Color.WHITE)
            lineChart.animateXY(3000,3000)

        }


        }
    }
}