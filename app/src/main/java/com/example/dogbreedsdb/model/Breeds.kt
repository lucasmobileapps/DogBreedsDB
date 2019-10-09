package com.example.dogbreedsdb.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Breeds (
    val breedname: String? ,
    val breedgroup: String? ,
    val height: String? ,
    val weight: String? ,
    val lifespan: String? ,
    var favorite : String? = "False") :Parcelable