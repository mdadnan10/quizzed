package com.demo.quizzed.activities.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.demo.quizzed.R
import com.demo.quizzed.activities.models.Questions

class OptionAdapter(val context: Context, val question: Questions) :
    RecyclerView.Adapter<OptionAdapter.OptionViewHolder>() {

    private var options: List<String> =
        listOf(question.option1, question.option2, question.option3, question.option4)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.option_item, parent, false)
        return OptionViewHolder(view)
    }

    override fun onBindViewHolder(holder: OptionViewHolder, position: Int) {
        val data = options[position]
        holder.optionView.text = data
        holder.itemView.setOnClickListener {
            question.userAnswer = data
            notifyDataSetChanged()
        }
        if (question.userAnswer == data) {
            holder.itemView.setBackgroundResource(R.drawable.option_item_selected_bg)
        } else {
            holder.itemView.setBackgroundResource(R.drawable.option_item_bg)
        }
    }

    override fun getItemCount(): Int {
        return options.size
    }

    inner class OptionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var optionView = view.findViewById<TextView>(R.id.quiz_option)
    }
}