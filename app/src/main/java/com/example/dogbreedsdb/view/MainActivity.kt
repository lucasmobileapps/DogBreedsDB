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
import com.example.dogbreedsdb.database.BreedDatabaseHelper
import com.example.dogbreedsdb.model.Breeds
import com.example.dogbreedsdb.util.ErrorLogger

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.breed_item_view_layout.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), BreedAdapter.BreedAdapterDelegate {

    var breedList: MutableList <Breeds> = mutableListOf(Breeds("Labrador", "Family Dog", "60 cm", "90 pounds", "7 years"))
    lateinit var breedDBHelper: BreedDatabaseHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        breedDBHelper = BreedDatabaseHelper(this, null)
        getAllBreeds()
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
        if (breed.favorite == "FALSE") {
            //edit_imageview.setImageResource(R.drawable.ic_stargold).toString()
            breed.favorite = "TRUE"
            //breedList.add(Breeds(favoriteChange))
            allBreeds_recyclerview.adapter?.notifyDataSetChanged()
            Toast.makeText(applicationContext, "${breed.breedname} is no more in favorites", Toast.LENGTH_SHORT).show()

        }
        else{
            //edit_imageview.setImageResource(R.drawable.ic_star)
            breed.favorite = "FALSE"
            allBreeds_recyclerview.adapter?.notifyDataSetChanged()
            Toast.makeText(applicationContext, "${breed.breedname} is in favorite", Toast.LENGTH_SHORT).show()
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
                val filteredList: List<Breeds> = breedList.filter { it.favorite == "FALSE" }
                allBreeds_textview.text = "My Favorite Breed"
                setUpView(filteredList.toMutableList())
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getAllBreeds() {

        val dbCursor = breedDBHelper.getAllBreeds()
        dbCursor?.moveToFirst()
        val stringBuilder = StringBuilder()
        try {
            dbCursor?.let { myCursor ->

                while (myCursor.moveToNext()) {
                    var breedIDDB =  myCursor.getInt(myCursor.getColumnIndex(BreedDatabaseHelper.COLUMN_BREED_ID))
                    var nameDB = myCursor.getString(myCursor.getColumnIndex(BreedDatabaseHelper.COLUMN_NAME))
                    var groupDB = dbCursor.getString(myCursor.getColumnIndex(BreedDatabaseHelper.COLUMN_BREEDGROUP))
                    var heightDB = myCursor.getString(myCursor.getColumnIndex(BreedDatabaseHelper.COLUMN_HEIGHT))
                    var weightDB = dbCursor.getString(myCursor.getColumnIndex(BreedDatabaseHelper.COLUMN_WEIGHT))
                    var lifespanDB = dbCursor.getString(myCursor.getColumnIndex(BreedDatabaseHelper.COLUMN_LIFESPAN))
                    var favoriteDB = dbCursor.getString(myCursor.getColumnIndex(BreedDatabaseHelper.COLUMN_FAVORITE))

                    breedList.add(
                        Breeds(
                            nameDB, groupDB,
                            heightDB, weightDB, lifespanDB, favoriteDB
                        )
                    )
                    allBreeds_recyclerview.adapter?.notifyItemInserted(breedList.lastIndex)


                }
                /*
                while (myCursor.moveToNext()) {
                    stringBuilder.append(
                        "${myCursor.getInt(myCursor.getColumnIndex(BreedDatabaseHelper.COLUMN_BREED_ID))} | ${myCursor.getString(
                            myCursor.getColumnIndex(BreedDatabaseHelper.COLUMN_NAME))} " +
                                "${dbCursor.getString(myCursor.getColumnIndex(BreedDatabaseHelper.COLUMN_BREEDGROUP))} " +
                                "${myCursor.getString(myCursor.getColumnIndex(BreedDatabaseHelper.COLUMN_HEIGHT))} " +
                                "${dbCursor.getString(myCursor.getColumnIndex(BreedDatabaseHelper.COLUMN_WEIGHT))} " +
                                "${dbCursor.getString(myCursor.getColumnIndex(BreedDatabaseHelper.COLUMN_LIFESPAN))} " +
                                "${dbCursor.getString(myCursor.getColumnIndex(BreedDatabaseHelper.COLUMN_FAVORITE))}\n"
                    )
                }

                 */
                myCursor.close()
            }
            Log.d("PASSEDDATA", "Database:  ${stringBuilder}")
            Log.d("PASSEDDATA", "Database:  ${stringBuilder.toString()}")
            //info_textview.text = stringBuilder.toString()
        } catch (throwable: Throwable) {
            ErrorLogger.LogError(throwable)
        }

    }




}
