package com.example.moneyNote.util

import android.view.View

interface OutcomeListener {
    fun onCategoryItemClick(view: View, position: Int)

    fun onCategoryItemLongClick(view: View, position: Int)
}