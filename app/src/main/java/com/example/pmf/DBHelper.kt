package com.example.pmf

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "ingredients.db"
        private const val DATABASE_VERSION = 1
        const val TABLE_NAME = "ingre"
        const val COLUMN_NAME = "name"
        const val COLUMN_NUM = "num"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = "CREATE TABLE $TABLE_NAME ($COLUMN_NAME TEXT, $COLUMN_NUM INTEGER)"
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addItem(name: String, num: Int) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, name)
            put(COLUMN_NUM, num)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun updateItem(name: String, num: Int) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NUM, num)
        }
        db.update(TABLE_NAME, values, "$COLUMN_NAME = ?", arrayOf(name))
        db.close()
    }

    fun deleteItem(name: String) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "$COLUMN_NAME = ?", arrayOf(name))
        db.close()
    }

    fun getItem(name: String): Pair<String, Int>? {
        val db = this.readableDatabase
        val cursor = db.query(TABLE_NAME, arrayOf(COLUMN_NAME, COLUMN_NUM), "$COLUMN_NAME = ?", arrayOf(name), null, null, null)
        cursor?.moveToFirst()
        val item = if (cursor.count > 0) {
            Pair(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)), cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NUM)))
        } else {
            null
        }
        cursor.close()
        db.close()
        return item
    }

    fun getAllItems(): List<Pair<String, Int>> {
        val itemList = mutableListOf<Pair<String, Int>>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        if (cursor.moveToFirst()) {
            do {
                val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
                val num = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NUM))
                itemList.add(Pair(name, num))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return itemList
    }

    fun searchItems(query: String): List<Pair<String, Int>> {
        val itemList = mutableListOf<Pair<String, Int>>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $COLUMN_NAME LIKE ?", arrayOf("%$query%"))
        if (cursor.moveToFirst()) {
            do {
                val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
                val num = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NUM))
                itemList.add(Pair(name, num))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return itemList
    }

    fun searchItemsCursor(query: String): Cursor {
        val db = this.readableDatabase
        return db.rawQuery("SELECT _rowid_ AS _id, $COLUMN_NAME, $COLUMN_NUM FROM $TABLE_NAME WHERE $COLUMN_NAME LIKE ?", arrayOf("%$query%"))
    }
    fun getAllIngredientsCursor(): Cursor {
        val db = this.readableDatabase
        return db.rawQuery("SELECT _rowid_ AS _id, $COLUMN_NAME, $COLUMN_NUM FROM $TABLE_NAME", null)
    }
}
