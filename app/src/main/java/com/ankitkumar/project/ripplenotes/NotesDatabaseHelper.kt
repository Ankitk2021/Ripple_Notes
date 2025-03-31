package com.ankitkumar.project.ripplenotes

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import androidx.core.content.contentValuesOf

class NotesDatabaseHelper(context: Context) : SQLiteOpenHelper(context, "notes.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE notes(id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, content TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS notes")
        onCreate(db)
    }

    fun insertNote(title: String, content: String) {
        val db = writableDatabase
        val values = ContentValues()
        values.put("title", title)
        values.put("content", content)
        db.insert("notes", null, values)
        db.close()
    }

    fun getAllNotes(): List<NoteModal> {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM notes", null)
        val notes = mutableListOf<NoteModal>()

        while (cursor.moveToNext()) {

            val Notes: NoteModal = NoteModal(cursor.getString(1), cursor.getString(2))


            notes.add(Notes)
        }
        cursor.close()
        db.close()
        return notes
    }

    fun updateNote(note : NoteModal,context: Context,title: String,desc: String){

        val db = writableDatabase;
        val values = contentValuesOf().apply { put("title",title)
        put("content",desc)}

        val whereArgs = "title = ? AND content = ? "
        val whereClause = arrayOf(note.title,note.desc)

        try{
            db.update("notes",values,whereArgs,whereClause)
        }catch (e:Exception){
            Toast.makeText(context, "Not updated.Reason : ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
        }


    }

    fun deleteNote(title: String, desc: String, context: Context) {
        val db = writableDatabase

        try {

            val res = db.delete(
                "notes",
                "title = ? AND content = ? ", arrayOf(title, desc)
            )
            //  Toast.makeText(context,"Success 🚮🗑️",Toast.LENGTH_SHORT).show()

            if (res > 0) {
              //  Toast.makeText(context, "Success. 🗑️🚮", Toast.LENGTH_SHORT).show()
            } else {
              //  Toast.makeText(context, "Not deleted 😕", Toast.LENGTH_SHORT).show()
            }

        } catch (e: Exception) {
            Toast.makeText(context, e.localizedMessage, Toast.LENGTH_LONG).show()

        }

    }
}
