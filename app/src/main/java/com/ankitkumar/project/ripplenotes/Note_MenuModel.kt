package com.ankitkumar.project.ripplenotes

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu

class Note_MenuModel(private val Listener: ChangeListener) : CustomDialogue.updateHelper{

private lateinit var Note : NoteModal;
private lateinit var c : Context;
    interface ChangeListener {

        fun refreshList(note: NoteModal)

    }

    fun SetMenu(context: Context, view: View, note: NoteModal) {
Note = note
        c = context
        val pop = PopupMenu(context, view)
        pop.inflate(R.menu.note_item_menu)

        try {
            val menu = PopupMenu::class.java.getDeclaredField("mPopup")
            menu.isAccessible = true
            val mPopup = menu.get(pop)
            val method = mPopup.javaClass.getDeclaredMethod("setIcon", Boolean::class.java)
            method.isAccessible = true
            method.invoke(mPopup, true)
        } catch (e: Exception) {
            print(e.localizedMessage)
        } finally {
            pop.show()
        }

        pop.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.note_edit -> {
                    val cd = CustomDialogue(context,note,this)
                    cd.show()
                    cd.saveBtn()
                    cd.closeBtn()

                  //  Listener.refreshList(note)
                    true
                }

                R.id.note_delete -> {
                    val dbHelper = NotesDatabaseHelper(context)

                    dbHelper.deleteNote(note.title, note.desc, context)

                    Listener.refreshList(note)




                    true
                }



                else -> {
                    false
                }

            }
        }

    }

    override fun update(title: String, desc: String) {
        val dbHelper = NotesDatabaseHelper(c)
        dbHelper.updateNote(Note, c,title,desc )

    }



}