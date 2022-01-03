package com.example.moneyNote.adapater

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import com.example.moneyNote.R
import com.example.moneyNote.activity.IncomeActivity
import com.example.moneyNote.activity.OutcomeActivity
import com.example.moneyNote.module.Income
import com.example.moneyNote.util.IncomeListener

class IncomeAdapter(private val incomes: List<Income>) :
    RecyclerView.Adapter<IncomeAdapter.CategoryViewHolder>() {

    private var listener:IncomeListener? = null


    class CategoryViewHolder(item: View, listener: IncomeListener?) : ViewHolder(item) {
        val mName: TextView = item.findViewById(R.id.categoryView_itemName)
        val mDescription: TextView = item.findViewById(R.id.categoryView_itemDescription)

        init{
            item.setOnClickListener{
                listener?.onCategoryItemClick(it, adapterPosition)
            }

            item.setOnLongClickListener{
                listener?.onCategoryItemLongClick(it, adapterPosition)
                true
            }
        }
    }

    fun setOnCategoryListener(listener: IncomeActivity){
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(
            R.layout.category_view_list, parent, false
        )

        return CategoryViewHolder(item, listener)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.mName.text = incomes[position].value
        holder.mDescription.text = incomes[position].description
    }

    override fun getItemCount(): Int {
        return incomes.size
    }
}