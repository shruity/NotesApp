package com.notesapp.Database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBOpenHelper (context: Context, factory: SQLiteDatabase.CursorFactory?) : SQLiteOpenHelper(context, DATABASE_NAME,
        factory, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_NOTES_TABLE = ("CREATE TABLE " +
                TABLE_NOTES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_USERID + " INTEGER,"
                + COLUMN_NOTES_ID+ " INTEGER,"
                + COLUMN_TITLE + " TEXT,"
                + COLUMN_BODY + " TEXT,"
                + COLUMN_FAVORITE + " INTEGER,"
                + COLUMN_COMPLETED + " INTEGER"
                +")")
        db.execSQL(CREATE_NOTES_TABLE)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES)
        onCreate(db)
    }
    fun addNotes(notes: NotesModel) {
        val values = ContentValues()
        values.put(COLUMN_USERID, notes.userId)
        values.put(COLUMN_NOTES_ID, notes.notesId)
        values.put(COLUMN_TITLE, notes.title)
        values.put(COLUMN_BODY, notes.body)
        if (notes.favorite)
            values.put(COLUMN_FAVORITE, 1)
        else
            values.put(COLUMN_FAVORITE, 0)
        if (notes.completed)
            values.put(COLUMN_COMPLETED, 1)
        else
            values.put(COLUMN_COMPLETED,0)
        val db = this.writableDatabase
        db.insert(TABLE_NOTES, null, values)
        db.close()
    }
    fun getAllNotes(): List<NotesModel>? {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NOTES", null)
        var completedBool:Boolean
        var favoriteBool:Boolean
        var notesList: List<NotesModel> = ArrayList<NotesModel>()
        if (cursor.moveToFirst()) {
            do {
                val id = Integer.parseInt(cursor.getString(0))
                val userId = Integer.parseInt(cursor.getString(1))
                val notesId = Integer.parseInt(cursor.getString(2))
                val title = cursor.getString(3)
                val body = cursor.getString(4)
                val favorite = cursor.getInt(5)
                if (favorite == 1)
                    favoriteBool = true
                else
                    favoriteBool = false
                val completedNotes = cursor.getInt(6)
                if (completedNotes == 1)
                    completedBool = true
                else
                    completedBool = false

                val notes = NotesModel(userId,notesId,title,body,favoriteBool,completedBool)
                notesList += notes
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return notesList
    }

    fun checkAlreadyExist(notesId: Int): Boolean {
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT $COLUMN_NOTES_ID FROM $TABLE_NOTES WHERE $COLUMN_NOTES_ID = $notesId",null)
        if (cursor.moveToFirst()) {
            //Record exist
            cursor.close();
            return true;
        }
        //Record available
        cursor.close();
        return false;
    }

    fun getAllFavoriteNotes(): List<NotesModel>? {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NOTES WHERE $COLUMN_FAVORITE = '1'", null)
        var completedBool:Boolean
        var favoriteBool:Boolean
        var notesList: List<NotesModel> = ArrayList<NotesModel>()
        if (cursor.moveToFirst()) {
            do {
                val id = Integer.parseInt(cursor.getString(0))
                val userId = Integer.parseInt(cursor.getString(1))
                val notesId = Integer.parseInt(cursor.getString(2))
                val title = cursor.getString(3)
                val body = cursor.getString(4)
                val favorite = cursor.getInt(5)
                if (favorite == 1)
                    favoriteBool = true
                else
                    favoriteBool = false
                val completedNotes = cursor.getInt(6)
                if (completedNotes == 1)
                    completedBool = true
                else
                    completedBool = false

                val notes = NotesModel(userId,notesId,title,body,favoriteBool,completedBool)
                notesList += notes
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return notesList
    }

    fun updateFavoritesData(notesId: Int?, favorite:Boolean) {
        val db = this.writableDatabase
        val values = ContentValues()
        if (favorite)
            values.put(COLUMN_FAVORITE, 1)
        else
            values.put(COLUMN_FAVORITE, 0)
        db.update(TABLE_NOTES, values, "$COLUMN_NOTES_ID = "+notesId, null)
        db.close()
    }

    fun checkIfFavorite(notesId: Int?): Boolean {
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_NOTES " +
                "WHERE $COLUMN_NOTES_ID = $notesId AND $COLUMN_FAVORITE = 1",null)
        if (cursor.moveToFirst()) {
            //Record exist
            cursor.close();
            return true;
        }
        //Record available
        cursor.close();
        return false;
    }

    fun checkIfBody(notesId: Int?): String? {
        val db = this.readableDatabase
        var body:String?= null
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_NOTES WHERE $COLUMN_NOTES_ID = $notesId",null)
        if (cursor.moveToFirst()) {
            //Record exist
            body = cursor.getString(4)
        }
        //Record available
        cursor.close();
        return body
    }

    fun updateBodyData(notesId: Int?, body:String) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_BODY, body)
        db.update(TABLE_NOTES, values, "$COLUMN_NOTES_ID = "+notesId, null)
        db.close()
    }

    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "notes.db"
        val TABLE_NOTES = "notes"
        val COLUMN_ID = "_id"
        val COLUMN_USERID = "userID"
        val COLUMN_NOTES_ID = "notesID"
        val COLUMN_TITLE = "title"
        val COLUMN_BODY = "body"
        val COLUMN_FAVORITE = "favorite"
        val COLUMN_COMPLETED = "completed"
    }
}
