package com.example.dogbreedsdb.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dogbreedsdb.R
import com.example.dogbreedsdb.adapter.BreedAdapter
import com.example.dogbreedsdb.model.Breeds

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.breed_item_view_layout.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), BreedAdapter.BreedAdapterDelegate {

    var breedList: MutableList <Breeds> = mutableListOf(Breeds("Labrador", "Family Dog", "60 cm", "90 pounds", "7 years"))
    //var breedList: MutableList <Breeds> = mutableListOf()

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
        val intent = Intent(this, DogBreedActivity::class.java)
        intent.putExtra("editbreed", breed)

        //val intent = Intent(MainActivity@this, DogBreedActivity::class.java)
        startActivityForResult(intent, 744)

    }

    override fun favoriteSelect(breed: Breeds) {
        if (breed.favorite == "false") {
            edit_imageview.setImageResource(R.drawable.ic_stargold).toString()
            breed.favorite = "true"
            //breedList.add(Breeds(favoriteChange))
            allBreeds_recyclerview.adapter?.notifyDataSetChanged()
            Toast.makeText(applicationContext, "${breed.breedname} is in favorites", Toast.LENGTH_SHORT).show()

        }
        else{
            edit_imageview.setImageResource(R.drawable.ic_star)
            breed.favorite = "false"
            allBreeds_recyclerview.adapter?.notifyDataSetChanged()
            Toast.makeText(applicationContext, "${breed.breedname} is no more in favorite", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val passedName = data?.getParcelableExtra<Breeds>("breed")
        Log.d("PASSEDDATA", "${passedName?.breedname} " +
                "${passedName?.breedgroup} " +
                "${passedName?.weight} " +
                "${passedName?.height} " +
                "${passedName?.lifespan}")
        if (passedName?.breedname != null) {
            breedList.add(
                Breeds(
                    passedName?.breedname, passedName?.breedgroup,
                    passedName?.height, passedName?.weight, passedName?.lifespan
                )
            )
            Log.d("PASSEDDATA", "List: ${breedList}")

            allBreeds_recyclerview.adapter?.notifyItemInserted(breedList.lastIndex)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings_allBreeds -> {
                allBreeds_textview.text = "All Breeds of Dogs"
                setUpView(breedList)
                return true
            }
            R.id.action_settings_favorites -> {
                val filteredList: List<Breeds> = breedList.filter { it.favorite == "false" }
                allBreeds_textview.text = "My Favorite Breed"
                setUpView(filteredList.toMutableList())
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }



}
