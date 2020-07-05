package com.tikhonov.memorizer.ui.question

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tikhonov.memorizer.R
import com.tikhonov.memorizer.data.model.Question
import com.tikhonov.memorizer.data.model.QuestionWithMarks


//import com.tikhonov.memorizer.ui.question.QuestionListFragment.OnListFragmentInteractionListener
import kotlinx.android.synthetic.main.question_list_item.view.*
import kotlin.math.min

class QuestionListAdapter(val listener: OnClickListener): RecyclerView.Adapter<QuestionListAdapter.QuestionViewHolder>() {

    interface OnClickListener{
        fun onLongClick(question: Question)
    }

    private var items = mutableListOf<QuestionWithMarks>()

    fun setQuestionItems(questionList: List<QuestionWithMarks>) {
        items = questionList.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.question_list_item, parent, false)

        return QuestionViewHolder(
            view
        )
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        holder.itemView.apply {
            textViewQuestion.text = items[position].question.title
            val mark = min(items[position].mark, 100)
            textViewStat.text = "$mark %"
            setOnLongClickListener {
                listener.onLongClick(items[position].question)
                false
            }
        }

    }

    class QuestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
