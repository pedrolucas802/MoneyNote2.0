package com.example.moneyNote.activity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.SpannableStringBuilder
import android.widget.TextView
import com.example.moneyNote.R
import com.example.moneyNote.util.DataBase
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class GraphActivity : AppCompatActivity() {



     var incomeSUM = 50
     var outcomeSUM = 50

    private lateinit var mBarChart: BarChart

    private val handler = Handler(Looper.getMainLooper())

    private lateinit var barList:ArrayList<BarEntry>
    private lateinit var barList2:ArrayList<BarEntry>

    private lateinit var barDataSet: BarDataSet
    private lateinit var barData: BarData
    private lateinit var barDataSet2: BarDataSet
    private lateinit var barData2: BarData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graph_actvity)

        mBarChart = findViewById(R.id.BarChart)

//        barList.add(BarEntry(1f,500f))
//        barList.add(BarEntry(2f,600f))


        GlobalScope.launch {
            barList = ArrayList()
            barList2 = ArrayList()


            val incomeSUM = DataBase.getInstance(this@GraphActivity).getIncomeDao().getSum()
            val outcomeSUM = DataBase.getInstance(this@GraphActivity).getOutcomeDao().getSum()

            handler.post{
                barList.add(BarEntry(1f,incomeSUM.toFloat()))
                barList2.add(BarEntry(2f,outcomeSUM.toFloat()))

                barDataSet = BarDataSet(barList,"Income")
                barData = BarData(barDataSet)
//                barData.addDataSet(barDataSet)
                barDataSet.setColors(Color.GREEN,250)
//                mBarChart.data = barData
                barDataSet.valueTextColor = Color.BLACK
                barDataSet.valueTextSize = 15f


                barDataSet2 = BarDataSet(barList2,"Outcome")
//                barData = BarData(barDataSet2)
                barData.addDataSet(barDataSet2)
                barDataSet2.setColors(Color.RED,250)
                barDataSet2.valueTextColor = Color.BLACK
                barDataSet2.valueTextSize = 15f

                mBarChart.data = barData

            }


        }







    }
}

