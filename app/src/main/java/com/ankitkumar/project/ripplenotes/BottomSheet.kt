package com.ankitkumar.project.ripplenotes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheet : BottomSheetDialogFragment() {


    private lateinit var dbHelper: NotesDatabaseHelper

    //set 1
    interface BottomSheetListener {

        fun onNoteAdded(note: NoteModal)
    }


    private var listener: BottomSheetListener? = null

    fun setListener(listener: BottomSheetListener) {
        this.listener = listener
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.bottom_sheet_layout, container, false)


        val btnSvae: Button = v.findViewById(R.id.btn_save)
        val editTitle: EditText = v.findViewById(R.id.editTitle)
        val editDesc: EditText = v.findViewById(R.id.editDesc)

        dbHelper = NotesDatabaseHelper(v.context)
        btnSvae.setOnClickListener({
            val title = editTitle.text.toString()
            val desc = editDesc.text.toString()


            if (title.isNotEmpty() && desc.isNotBlank()) {


                dbHelper.insertNote(title, desc)

//step 2
                val newNote = NoteModal(title, desc)
                listener?.onNoteAdded(newNote)  // 🔥 Notify MainActivity
                dismiss()
            }


        })


        return v
    }


}