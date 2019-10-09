package com.example.dogbreedsdb.util

import android.util.Log

object ErrorLogger{
    private const val errorTag = "TAG_ERROR"

    fun LogError(throwable: Throwable){
        Log.e(errorTag, throwable.toString())
    }
}