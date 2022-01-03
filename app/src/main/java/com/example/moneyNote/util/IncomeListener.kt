package com.example.moneyNote.util

import android.view.View

interface IncomeListener {
    fun onCategoryItemClick(view: View, position: Int)

    fun onCategoryItemLongClick(view: View, position: Int)
}