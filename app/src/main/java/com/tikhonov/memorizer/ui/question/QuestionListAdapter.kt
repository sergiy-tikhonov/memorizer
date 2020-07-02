package com.tikhonov.memorizer.ui.question

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.tikhonov.memorizer.R
import com.tikhonov.memorizer.data.Dictionary
import com.tikhonov.memorizer.data.DictionaryWithQuestions
import com.tikhonov.memorizer.data.Question


//import com.tikhonov.memorizer.ui.question.QuestionListFragment.OnListFragmentInteractionListener
import kotlinx.android.synthetic.main.dictionary_item_list.view.*
import kotlinx.android.synthetic.main.question_list_item.view.*

//class QuestionListAdapter(val listener: OnClickListener): RecyclerView.Adapter<QuestionListAdapter.QuestionViewHolder>() {
class QuestionListAdapter: RecyclerView.Adapter<QuestionListAdapter.QuestionViewHolder>() {



    interface OnClickListener{
        fun onClick(dictionary: Dictionary)
        fun onEdit(dictionary: Dictionary)
        fun onDelete(dictionary: Dictionary)
        fun onTrain(dictionary: Dictionary)
    }

    private var items = mutableListOf<Question>()

    fun setQuestionItems(questionList: List<Question>) {
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
            textViewQuestion.text = items[position].title
            /*textViewItems.text = this.context.getString(R.string.items_count, items[position].questions.size)
            textViewDescription.text = items[position].dictionary.description*/
            /*setOnClickListener {
                listener.onClick(items[position].dictionary)
            }
            buttonEdit.setOnClickListener {
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
