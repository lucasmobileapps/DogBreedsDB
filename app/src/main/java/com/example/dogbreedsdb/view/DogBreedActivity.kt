package com.example.dogbreedsdb.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.dogbreedsdb.R
import com.example.dogbreedsdb.model.Breeds
import kotlinx.android.synthetic.main.activity_dogbreed.*
import kotlinx.android.synthetic.main.breed_item_view_layout.*

class DogBreedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dogbreed)

        saveEditor_button.setOnClickListener {
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
}
