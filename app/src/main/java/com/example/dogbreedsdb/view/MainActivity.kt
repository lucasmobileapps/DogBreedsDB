package com.example.dogbreedsdb.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dogbreedsdb.R
import com.example.dogbreedsdb.adapter.BreedAdapter
import com.example.dogbreedsdb.model.Breeds

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.breed_item_view_layout.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), BreedAdapter.BreedAdapterDelegate {

    var breedList = mutableListOf(Breeds("Labrador", "Family Dog", "60 cm", "90 pounds", "7 years"))
    var filteredList = mutableListOf(Breeds("Labrador", "Family Dog", "60 cm", "90 pounds", "7 years"))

    

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)


        setUpView(breedList)


        fabAdd.setOnClickListener { view ->
            val intent = Intent(MainActivity@this, DogBreedActivity::class.java)
            startActivityForResult(intent, 743)
        }
    }

    fun setUpView(list: MutableList<Breeds>){

        allBreeds_recyclerview.adapter = BreedAdapter(list, this)
        allBreeds_recyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true)


        val itemDecorator = DividerItemDecoration(this, LinearLayout.VERTICAL)
        allBreeds_recyclerview.addItemDecoration(itemDecorator)
    }

    override fun breedSelect(breed: Breeds) {
        val intent = Intent(MainActivity@this, DogBreedActivity::class.java)
        startActivityForResult(intent, 744)

    }

    override fun favoriteSelect(breed: Breeds) {
        if (breed.favorite == "false") {
            edit_imageview.setImageResource(R.drawable.ic_stargold)
            breed.favorite = "true"
        }
        else{
            edit_imageview.setImageResource(R.drawable.ic_star)
            breed.favorite = "false"
        }

        Log.d("PASSEDDATA","star")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val passedName = data?.getParcelableExtra<Breeds>("breed")
        Log.d("PASSEDDATA", "${passedName?.breedname} " +
                "${passedName?.breedgroup} " +
                "${passedName?.weight} " +
                "${passedName?.height} " +
                "${passedName?.lifespan}")

        breedList = mutableListOf(
            Breeds(passedName?.breedname, passedName?.breedgroup,
            passedName?.height, passedName?.weight, passedName?.lifespan))
        Log.d("PASSEDDATA", "List: ${breedList}")

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings_allBreeds -> {
                setUpView(breedList)
                return true
            }
            R.id.action_settings_favorites -> {
                setUpView(filteredList)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }



}
