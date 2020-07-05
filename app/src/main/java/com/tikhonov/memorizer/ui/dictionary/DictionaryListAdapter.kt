package com.tikhonov.memorizer.ui.dictionary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tikhonov.memorizer.R
import com.tikhonov.memorizer.data.model.Dictionary
import com.tikhonov.memorizer.data.model.DictionaryWithQuestions
import kotlinx.android.synthetic.main.dictionary_item_list.view.*

class DictionaryListAdapter(val listener: OnClickListener): RecyclerView.Adapter<DictionaryListAdapter.DictionaryViewHolder>() {

    interface OnClickListener{
        fun onEdit(dictionary: Dictionary)
        fun onDelete(dictionary: Dictionary)
        fun onTrain(dictionary: Dictionary)
        fun onList(dictionary: Dictionary)
    }

    private var items = mutableListOf<DictionaryWithQuestions>()

    fun setDictionaryItems(dictionaryList: List<DictionaryWithQuestions>) {
        items = dictionaryList.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DictionaryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.dictionary_item_list, parent, false)

        return DictionaryViewHolder(
            view
        )
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: DictionaryViewHolder, position: Int) {
        holder.itemView.apply {
            textViewDictionaryName.text = items[position].dictionary.name
            textViewItems.text = this.context.getString(R.string.items_count, items[position].questions.size)
            textViewDescription.text = items[position].dictionary.description
            buttonEdit.setOnClickListener {
                listener.onEdit(items[position].dictionary)
            }
            buttonDelete.setOnClickListener {
                listener.onDelete(items[position].dictionary)
            }
            buttonList.setOnClickListener {
                listener.onList(items[position].dictionary)
            }
            buttonTrain.setOnClickListener {
                listener.onTrain(items[position].dictionary)
            }
        }

    }

    class DictionaryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}