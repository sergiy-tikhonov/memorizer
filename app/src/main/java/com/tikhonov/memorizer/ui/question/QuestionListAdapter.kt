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
//class QuestionListAdapter: RecyclerView.Adapter<QuestionListAdapter.QuestionViewHolder>() {



    interface OnClickListener{
        fun onLongClick(question: Question)
        //fun onEdit(dictionary: Dictionary)
        //fun onDelete(dictionary: Dictionary)
        //fun onTrain(dictionary: Dictionary)
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
            //textViewStat.text = "${items[position].mark} / ${items[position].baseMark}"
            textViewStat.text = "$mark %"
            /*textViewItems.text = this.context.getString(R.string.items_count, items[position].questions.size)
            textViewDescription.text = items[position].dictionary.description*/
            setOnLongClickListener {
                listener.onLongClick(items[position].question)
                false
            }
           /* buttonEdit.setOnClickListener {
                listener.onEdit(items[position].dictionary)
            }
            buttonDelete.setOnClickListener {
                listener.onDelete(items[position].dictionary)
            }
            buttonTrain.setOnClickListener {
                listener.onTrain(items[position].dictionary)
            }*/
        }

    }

    class QuestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
