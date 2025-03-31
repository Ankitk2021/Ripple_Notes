package com.ankitkumar.project.ripplenotes

import android.annotation.SuppressLint
import android.renderscript.ScriptGroup.Binding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.core.view.marginBottom
import androidx.recyclerview.widget.RecyclerView
import java.util.Locale

class ItemAdapter(
    private val itemList: MutableList<NoteModal>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<ItemAdapter.VHolder>() {

    private var filteredList = itemList

    //step 1
    interface OnItemClickListener {
        fun onItemClick(note: NoteModal)   // 🔥 Click Event
        fun onItemLongPress(note: NoteModal,view: View) // 🔥 Long Press Event
    }

    fun filterList(mutableList: MutableList<NoteModal>) {
        this.filteredList = mutableList
        notifyDataSetChanged()
    }


    fun updateList(list : MutableList<NoteModal>){
        this.filteredList = list
        notifyDataSetChanged()

    }

    class VHolder(v: View) : RecyclerView.ViewHolder(v) {
        val textViewTitle: TextView = v.findViewById(R.id.note_title)
        val textViewDesc: TextView = v.findViewById(R.id.note_desc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_layout, parent, false)
        return VHolder(view)
    }

    override fun getItemCount(): Int {
        return filteredList.size
    }

    override fun onBindViewHolder(holder: VHolder, position: Int) {

        var background = holder.itemView.findViewById<View>(R.id.note_layout)

        when(position){


            0->{
                background.setBackgroundResource(R.drawable.note_item_state_first)
            }
            filteredList.size-1->{
                background.setBackgroundResource(R.drawable.note_item_state_last)

            }
        }


        val item = filteredList[position]
        holder.textViewTitle.text = item.title
        holder.textViewDesc.text = item.desc

//step 2
        holder.itemView.setOnClickListener {
            listener.onItemClick(item)
        }

        holder.itemView.setOnLongClickListener {
            listener.onItemLongPress(item,it)
            true

        }
    }
}