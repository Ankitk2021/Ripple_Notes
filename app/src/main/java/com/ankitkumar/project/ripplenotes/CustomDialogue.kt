package com.ankitkumar.project.ripplenotes

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import com.ankitkumar.project.ripplenotes.Note_MenuModel.ChangeListener
import com.google.android.material.textfield.TextInputEditText
import java.util.zip.Inflater
import kotlin.math.log

class CustomDialogue(context: Context,note:NoteModal,private val Helper : updateHelper)   {
    private val c: Context = context;
    private val note : NoteModal = note;
    private  val thisHelper : updateHelper = Helper

interface updateHelper{
    fun update(title : String , desc : String )
}
    interface RefreshInterface {
        fun refresh()
    }

    val inflater = LayoutInflater.from(context)
    val v = inflater.inflate(R.layout.edit_dialogue, null)
    var closeBtn  = v.findViewById<ImageButton>(R.id.closeBtn)
    var saveBtn = v.findViewById<Button>(R.id.btn_save_change)

    val dialogue = Dialog(context)
    val title_input = v.findViewById<TextInputEditText>(R.id.note_edit_title)
    val desc_input = v.findViewById<TextInputEditText>(R.id.note_edit_desc)


    fun show() {
        dialogue.setContentView(v)
        dialogue.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogue.show()
    }
    fun saveBtn (){
        saveBtn.setOnClickListener{



            thisHelper.update(title_input.text.toString(),desc_input.text.toString())



            Toast.makeText(c, "Not Changed Successfully 😛", Toast.LENGTH_SHORT).show()
            dialogue.dismiss()
        }
    }
    fun closeBtn(){
        closeBtn.setOnClickListener{
            Toast.makeText(c, "Closed ☺️", Toast.LENGTH_SHORT).show()
            dialogue.dismiss()

        }
    }


}
