package com.example.dogbreedsdb.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.dogbreedsdb.R
import com.example.dogbreedsdb.database.BreedDatabaseHelper
import com.example.dogbreedsdb.model.Breeds
import com.example.dogbreedsdb.util.ErrorLogger
import kotlinx.android.synthetic.main.activity_dogbreed.*
import kotlinx.android.synthetic.main.breed_item_view_layout.*

class DogBreedActivity : AppCompatActivity() {

    companion object {
        val fileName = "MyBreedList.txt"
        val filePath = "MyFilePath"
    }

    lateinit var guestDBHelper: BreedDatabaseHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dogbreed)

        guestDBHelper = BreedDatabaseHelper(this, null)


        /*
        val breedSelected = intent.getStringExtra("breed")?:""

        Log.d("PASSEDDATA", "List: ${breedSelected}")

        nameeditor_edittext.setText(breedSelected)

         */
        saveEditor_button.setOnClickListener {

            addNewBreed()

            val nameBreed = nameeditor_edittext.text.toString()
            val breedGroup = breedgroupeditor_edittext.text.toString()
            val height = height_edittext.text.toString()
            val weight = weight_edittext.text.toString()
            val lifespan = lifespanEditor_edittext.text.toString()
            val breed = Breeds(nameBreed, breedGroup, height, weight, lifespan)

            val intent = Intent()
            intent.putExtra("breed", breed)
            setResult(643, intent)
            finish()

        }
    }

    private fun addNewBreed() {

        val breedName = nameeditor_edittext.text.toString().toUpperCase()
        val breedGroup = breedgroupeditor_edittext.text.toString().toUpperCase()
        val breedHeight = height_edittext.text.toString().toUpperCase()
        val breedWeight = weight_edittext.text.toString().toUpperCase()
        val breedLifespan = lifespanEditor_edittext.text.toString().toUpperCase()
        val breedFavorite = "FALSE"

        /*
        nameeditor_edittext.text.clear()
        breedgroupeditor_edittext.text.clear()
        height_edittext.text.clear()
        weight_edittext.text.clear()
        lifespanEditor_edittext.text.clear()
         */

        try {
            guestDBHelper.insertNewBreed(Breeds(breedName, breedGroup, breedHeight, breedWeight, breedLifespan, breedFavorite))
        } catch (throwable: Throwable) {
            ErrorLogger.LogError(throwable)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val passedName = data?.getParcelableExtra<Breeds>("editbreed")
        nameeditor_edittext.setText("${passedName?.breedname}")
        breedgroupeditor_edittext.setText("${passedName?.breedgroup}")
        weight_edittext.setText("${passedName?.weight}")
        height_edittext.setText("${passedName?.height}")
        lifespanEditor_edittext.setText("${passedName?.lifespan}")



    }
}
