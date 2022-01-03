package com.example.moneyNote.adapater

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import com.example.moneyNote.R
import com.example.moneyNote.module.Income
import com.example.moneyNote.module.Outcome
import com.example.moneyNote.util.OutcomeListener

class OutcomeAdapter(private val outcomes: List<Outcome>) :
    RecyclerView.Adapter<OutcomeAdapter.CategoryViewHolder>() {

    private var listener:OutcomeListener? = null


    class CategoryViewHolder(item: View, listener: OutcomeListener?) : ViewHolder(item) {
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

    fun setOnCategoryListener(listener: OutcomeListener){
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(
            R.layout.category_view_list, parent, false
        )

        return CategoryViewHolder(item, listener)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.mName.text = outcomes[position].value.toString()
        holder.mDescription.text = outcomes[position].description
    }

    override fun getItemCount(): Int {
        return outcomes.size
    }
}