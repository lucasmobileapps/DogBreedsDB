package com.example.dogbreedsdb.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.dogbreedsdb.model.Breeds

class BreedDatabaseHelper(private val context: Context, private val cursorFactory: SQLiteDatabase.CursorFactory?): SQLiteOpenHelper(context, DATABASE_NAME, cursorFactory, DATABASE_VERSION) {
    override fun onCreate(database: SQLiteDatabase) {
        val createQuery = "CREATE TABLE $TABLE_NAME ($COLUMN_BREED_ID INTEGER PRIMARY KEY, " +
                "$COLUMN_NAME TEXT, $COLUMN_BREEDGROUP TEXT, $COLUMN_WEIGHT TEXT, " +
                "$COLUMN_HEIGHT TEXT, $COLUMN_LIFESPAN TEXT, $COLUMN_FAVORITE TEXT)"
        database.execSQL(createQuery)
    }

    override fun onUpgrade(database: SQLiteDatabase, olderVersion: Int, newerVersion: Int) {
        database.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(database)
    }

    fun insertNewBreed(newBreed: Breeds){
        val values = ContentValues()
        values.apply {
            put(COLUMN_NAME, newBreed.breedname)
            put(COLUMN_BREEDGROUP, newBreed.breedgroup)
            put(COLUMN_WEIGHT, newBreed.weight)
            put(COLUMN_HEIGHT, newBreed.height)
            put(COLUMN_LIFESPAN, newBreed.lifespan)
            put(COLUMN_FAVORITE, newBreed.favorite)
        }
        val database = this.writableDatabase
        database.insert(TABLE_NAME, null, values)
        database.close()
    }

    fun getAllBreeds(): Cursor? {
        val database = this.readableDatabase

        return database.rawQuery("SELECT * FROM $TABLE_NAME", null)
    }

    companion object {
        val DATABASE_NAME = "customers.db"
        val DATABASE_VERSION = 1
        val TABLE_NAME = "customers"

        val COLUMN_NAME = "breed_name"
        val COLUMN_BREEDGROUP = "breed_group"
        val COLUMN_WEIGHT = "weight"
        val COLUMN_HEIGHT = "height"
        val COLUMN_LIFESPAN = "life_span"
        val COLUMN_FAVORITE = "favorite"
        val COLUMN_BREED_ID = "breed_id"
    }
}